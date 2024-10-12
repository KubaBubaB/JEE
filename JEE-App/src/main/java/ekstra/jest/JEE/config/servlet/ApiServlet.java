package ekstra.jest.JEE.config.servlet;

import ekstra.jest.JEE.Requests.*;
import ekstra.jest.JEE.interfaces.ICategoryOfClothingController;
import ekstra.jest.JEE.interfaces.IPersonController;
import ekstra.jest.JEE.interfaces.IPieceOfClothingController;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class ApiServlet extends HttpServlet {
    private final IPersonController personController;
    private final IPieceOfClothingController pieceOfClothingController;
    private final ICategoryOfClothingController categoryOfClothingController;

    public static final class Paths {

        /**
         * All API operations. Version v1 will be used to distinguish from other implementations.
         */
        public static final String API = "/api";

    }

    public static final class Patterns{
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern PERSONS = Pattern.compile("/persons/?");
        public static final Pattern PERSON = Pattern.compile("/persons/(%s)".formatted(UUID.pattern()));
        public static final Pattern PERSON_PHOTO = Pattern.compile("/persons/(%s)/photo".formatted(UUID.pattern()));
        public static final Pattern CATEGORIES = Pattern.compile("/categories/?");
        public static final Pattern CATEGORY = Pattern.compile("/categories/(%s)".formatted(UUID.pattern()));
        public static final Pattern PIECES = Pattern.compile("/pieces/?");
        public static final Pattern PIECE = Pattern.compile("/pieces/(%s)".formatted(UUID.pattern()));
        public static final Pattern ASSIGN_CATEGORY = Pattern.compile("/pieces/(%s)/categories/(%s)".formatted(UUID.pattern(), UUID.pattern()));
        public static final Pattern ASSIGN_PERSON = Pattern.compile("/pieces/(%s)/persons/(%s)".formatted(UUID.pattern(), UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(IPersonController personController, IPieceOfClothingController pieceOfClothingController, ICategoryOfClothingController categoryOfClothingController) {
        this.personController = personController;
        this.pieceOfClothingController = pieceOfClothingController;
        this.categoryOfClothingController = categoryOfClothingController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PERSONS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(personController.getAllPersons()));
                return;
            } else if (path.matches(Patterns.PERSON.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PERSON, path);
                response.getWriter().write(jsonb.toJson(personController.getPerson(uuid)));
                return;
            } else if (path.matches(Patterns.PERSON_PHOTO.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.PERSON_PHOTO, path);
                byte[] portrait = personController.getPersonPhoto(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            }
            else if (path.matches(Patterns.CATEGORIES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(categoryOfClothingController.getAllCategoriesOfClothing()));
                return;
            } else if (path.matches(Patterns.CATEGORY.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                response.getWriter().write(jsonb.toJson(categoryOfClothingController.getCategoryOfClothing(uuid)));
                return;
            } else if (path.matches(Patterns.PIECES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(pieceOfClothingController.getAllPiecesOfClothing()));
                return;
            } else if (path.matches(Patterns.PIECE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PIECE, path);
                response.getWriter().write(jsonb.toJson(pieceOfClothingController.getPieceOfClothing(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PERSON.pattern())) {
                UUID uuid = extractUuid(Patterns.PERSON, path);
                personController.addPerson(uuid, jsonb.fromJson(request.getReader(), PutPersonRequest.class));
                return;
            } else if (path.matches(Patterns.PERSON_PHOTO.pattern())) {
                UUID uuid = extractUuid(Patterns.PERSON_PHOTO, path);
                personController.addPersonPhoto(uuid, request.getPart("photo").getInputStream());
                return;
            }
            else if (path.matches(Patterns.CATEGORY.pattern())) {
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                categoryOfClothingController.addCategoryOfClothing(uuid, jsonb.fromJson(request.getReader(), PutCategoryOfClothingRequest.class));
                return;
            }
            else if (path.matches(Patterns.PIECE.pattern())) {
                UUID uuid = extractUuid(Patterns.PIECE, path);
                pieceOfClothingController.addPieceOfClothing(uuid, jsonb.fromJson(request.getReader(), PutPieceOfClothingRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PERSON.pattern())) {
                UUID uuid = extractUuid(Patterns.PERSON, path);
                personController.removePerson(uuid);
                return;
            }
            else if (path.matches(Patterns.CATEGORY.pattern())) {
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                categoryOfClothingController.removeCategoryOfClothing(uuid);
                return;
            }
            else if (path.matches(Patterns.PIECE.pattern())) {
                UUID uuid = extractUuid(Patterns.PIECE, path);
                pieceOfClothingController.removePieceOfClothing(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PERSON.pattern())) {
                UUID uuid = extractUuid(Patterns.PERSON, path);
                personController.updatePerson(uuid, jsonb.fromJson(request.getReader(), UpdatePersonRequest.class));
                return;
            }
            else if (path.matches(Patterns.PERSON_PHOTO.pattern())) {
                UUID uuid = extractUuid(Patterns.PERSON_PHOTO, path);
                personController.patchPersonPhoto(uuid, request.getPart("photo").getInputStream());
                return;
            }
            else if (path.matches(Patterns.CATEGORY.pattern())) {
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                categoryOfClothingController.updateCategoryOfClothing(uuid, jsonb.fromJson(request.getReader(), UpdateCategoryOfClothingRequest.class));
                return;
            }
            else if (path.matches(Patterns.PIECE.pattern())) {
                UUID uuid = extractUuid(Patterns.PIECE, path);
                pieceOfClothingController.updatePieceOfClothing(uuid, jsonb.fromJson(request.getReader(), UpdatePieceOfClothingRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.ASSIGN_CATEGORY.pattern())) {
                UUID[] uuids = extractTwoUuids(Patterns.ASSIGN_CATEGORY, path);
                pieceOfClothingController.assignCategory(uuids[0], uuids[1]);
                return;
            }
            else if (path.matches(Patterns.ASSIGN_PERSON.pattern())) {
                UUID[] uuids = extractTwoUuids(Patterns.ASSIGN_PERSON, path);
                pieceOfClothingController.assignOwner(uuids[0], uuids[1]);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private static UUID[] extractTwoUuids(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return new UUID[] {
                    UUID.fromString(matcher.group(1)),
                    UUID.fromString(matcher.group(2))
            };
        }
        throw new IllegalArgumentException("No UUIDs in path.");
    }
}
