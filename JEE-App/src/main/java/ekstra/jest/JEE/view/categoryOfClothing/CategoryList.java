package ekstra.jest.JEE.view.categoryOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.model.categoryOfClothing.CategoriesOfClothingModel;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class CategoryList {
    private final CategoryOfClothingService service;
    private final EntitiesToModelsMapper mapper;
    private CategoriesOfClothingModel categories;

    @Inject
    public CategoryList(CategoryOfClothingService service, EntitiesToModelsMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public CategoriesOfClothingModel getCategories() {
        if (categories == null) {
            categories = mapper.categoriesToModel(service.getAllCategoryOfClothing());
        }
        return categories;
    }

    public String deleteAction(CategoriesOfClothingModel.Category category) {
        service.removeCategoryOfClothing(category.getId());
        return "categories_list?faces-redirect=true";
    }
}
