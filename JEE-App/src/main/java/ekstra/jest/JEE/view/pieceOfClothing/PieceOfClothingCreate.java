package ekstra.jest.JEE.view.pieceOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingCreateModel;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import ekstra.jest.JEE.service.PersonService;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class PieceOfClothingCreate {
    private final PieceOfClothingService pieceOfClothingService;

    private final PersonService personService;

    private final CategoryOfClothingService categoryOfClothingService;

    private final EntitiesToModelsMapper mapper;

    @Inject
    public PieceOfClothingCreate(PieceOfClothingService pieceOfClothingService, PersonService personService, CategoryOfClothingService categoryOfClothingService, EntitiesToModelsMapper mapper) {
        this.pieceOfClothingService = pieceOfClothingService;
        this.personService = personService;
        this.categoryOfClothingService = categoryOfClothingService;
        this.mapper = mapper;
        categories = categoryOfClothingService.getAllCategoryOfClothing().values().stream()
                .map(CategoryOfClothing::getName)
                .toList();
        owners = personService.getAllPersons().values().stream()
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .toList();
    }

    @Getter
    @Setter
    private PieceOfClothingCreateModel piece = PieceOfClothingCreateModel.builder().build();

    @Getter
    private final String[] sizes = {"XS", "S", "M", "L", "XL"};

    @Getter
    @Setter
    private List<String> categories = new ArrayList<>();

    @Getter
    @Setter
    private List<String> owners = new ArrayList<>();;

    public String saveAction() {
        UUID key = UUID.randomUUID();
        Person owner = personService.getPersonByFirstNameAndLastName(piece.getOwnersName()).orElse(null);
        CategoryOfClothing category = categoryOfClothingService.getCategoryOfClothingByName(piece.getCategoryName()).orElse(null);
        pieceOfClothingService.saveWithExtraSteps(key, mapper.modelToPiece(key, piece, owner, category));
        return  (category == null ? "/categories/categories_list.xhtml?faces-redirect=true?" : "/categories/categories_view.xhtml?faces-redirect=true&id=" + category.getId());
    }

    public String cancelAction() {
        return "/categories/categories_list.xhtml?faces-redirect=true?";
    }

    public void init() {
        categories = categoryOfClothingService.getAllCategoryOfClothing().values().stream()
                .map(CategoryOfClothing::getName)
                .toList();
        owners = personService.getAllPersons().values().stream()
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .toList();
    }
}
