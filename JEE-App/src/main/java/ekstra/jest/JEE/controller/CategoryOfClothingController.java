package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Responses.GetCategoryOfClothingResponse;
import ekstra.jest.JEE.service.CategoryOfClothingService;

import java.util.UUID;

public class CategoryOfClothingController {
    private final CategoryOfClothingService categoryOfClothingService;

    public CategoryOfClothingController(CategoryOfClothingService categoryOfClothingService) {
        this.categoryOfClothingService = categoryOfClothingService;
    }

    public GetCategoryOfClothingResponse getCategoryOfClothing(UUID id){

    }
}
