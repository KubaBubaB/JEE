package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.CategoryOfClothingMapper;
import ekstra.jest.JEE.Requests.PutCategoryOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.Responses.GetCategoriesOfClothingResponse;
import ekstra.jest.JEE.Responses.GetCategoryOfClothingResponse;
import ekstra.jest.JEE.exceptions.BadRequestException;
import ekstra.jest.JEE.exceptions.NotFoundException;
import ekstra.jest.JEE.interfaces.ICategoryOfClothingController;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class CategoryOfClothingController implements ICategoryOfClothingController {
    private final CategoryOfClothingService categoryOfClothingService;

    @Inject
    public CategoryOfClothingController(CategoryOfClothingService categoryOfClothingService) {
        this.categoryOfClothingService = categoryOfClothingService;
    }

    public GetCategoryOfClothingResponse getCategoryOfClothing(UUID id){
        var categoryOfClothing = categoryOfClothingService.getCategoryOfClothing(id).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        return CategoryOfClothingMapper.mapCategoryOfClothingToGetCategoryOfClothingResponse(categoryOfClothing);
    }

    public GetCategoriesOfClothingResponse getAllCategoriesOfClothing(){
        return CategoryOfClothingMapper.mapCategoriesOfClothingToGetCategoriesOfClothingResponse(categoryOfClothingService.getAllCategoryOfClothing());
    }

    public void addCategoryOfClothing(UUID id, PutCategoryOfClothingRequest request){
        categoryOfClothingService.getCategoryOfClothing(id).ifPresentOrElse(categoryOfClothing -> {
            throw new BadRequestException("Category of clothing with this id already exists");
        }, () -> categoryOfClothingService.saveCategoryOfClothing(id, CategoryOfClothingMapper.mapPutCategoryOfClothingRequestToCategoryOfClothing(request, id)));
    }

    public void updateCategoryOfClothing(UUID id, UpdateCategoryOfClothingRequest request){
        var categoryOfClothing = categoryOfClothingService.getCategoryOfClothing(id).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        categoryOfClothingService.updateCategoryOfClothing(categoryOfClothing, request);
    }

    public void removeCategoryOfClothing(UUID id){
        categoryOfClothingService.removeCategoryOfClothing(id);
    }
}
