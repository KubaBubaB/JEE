package ekstra.jest.JEE.config.servlet;

import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.controller.PersonController;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.json.bind.Jsonb;
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
    private PersonController personController;

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
        public static final Pattern PERSON_PHOTO = Pattern.compile("/persons/%s/photo".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        personController = (PersonController) getServletContext().getAttribute("personController");
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
                //response.addHeader("Location", createUrl(request, Paths.API, "characters", uuid.toString()));
                return;
            } else if (path.matches(Patterns.PERSON_PHOTO.pattern())) {
                UUID uuid = extractUuid(Patterns.PERSON_PHOTO, path);
                personController.addPersonPhoto(uuid, request.getPart("portrait").getInputStream());
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
}
