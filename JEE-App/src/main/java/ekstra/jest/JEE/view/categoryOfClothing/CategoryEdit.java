package ekstra.jest.JEE.view.categoryOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.model.categoryOfClothing.CategoryEditModel;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class CategoryEdit {
    private final CategoryOfClothingService categoryOfClothingService;
    private final EntitiesToModelsMapper mapper;

    @Setter
    @Getter
    private UUID categoryId;

    @Getter
    private CategoryEditModel category = CategoryEditModel.builder().build();

    @Getter
    private final List<String> isTrendyValues = List.of("It is trendy!", "meh, not really");

    @Inject
    public CategoryEdit(CategoryOfClothingService categoryOfClothingService, EntitiesToModelsMapper mapper) {
        this.categoryOfClothingService = categoryOfClothingService;
        this.mapper = mapper;
    }

    public void init() throws IOException {
        Optional<CategoryOfClothing> category = categoryOfClothingService.getCategoryOfClothing(categoryId);
        if (category.isPresent()) {
            this.category = mapper.categoryToEditModel(category.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public String saveAction() {
        categoryOfClothingService.updateCategoryOfClothing(categoryOfClothingService.getCategoryOfClothing(categoryId).get(), UpdateCategoryOfClothingRequest.builder().isTrendy(Objects.equals(category.getIsTrendy(), "It is trendy!")).build());
        return "/categories/categories_list.xhtml?faces-redirect=true?";
    }
}
