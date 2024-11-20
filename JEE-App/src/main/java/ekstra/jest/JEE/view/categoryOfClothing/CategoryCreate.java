package ekstra.jest.JEE.view.categoryOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.model.categoryOfClothing.CreateCategoryModel;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@NoArgsConstructor(force = true)
@RequestScoped
public class CategoryCreate implements Serializable {

    private CategoryOfClothingService categoryOfClothingService;
    
    private final EntitiesToModelsMapper mapper;
    
    @Inject
    public CategoryCreate(CategoryOfClothingService categoryOfClothingService, EntitiesToModelsMapper mapper) {
        //this.categoryOfClothingService = categoryOfClothingService;
        this.mapper = mapper;
    }

    @EJB
    public void setCategoryOfClothingService(CategoryOfClothingService categoryOfClothingService) {
        this.categoryOfClothingService = categoryOfClothingService;
    }

    @Getter
    @Setter
    private CreateCategoryModel category = CreateCategoryModel.builder().build();

    @Getter
    private final List<String> whereWearClothings = List.of("HEAD", "NECK", "TORSO", "HANDS", "LEGS", "FEET");

    @Getter
    private final List<String> isTrendyValues = List.of("It is trendy!", "meh, not really");
    
    public String saveAction() {
        UUID key = UUID.randomUUID();
        categoryOfClothingService.saveCategoryOfClothing(key, mapper.modelToCategory(key, category));
        return "/categories/categories_list.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        return "/categories/categories_list.xhtml?faces-redirect=true";
    }

    public void init() {
        category = CreateCategoryModel.builder().build();
    }
}
