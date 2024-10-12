package ekstra.jest.JEE.interfaces;
import ekstra.jest.JEE.Requests.PutCategoryOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.Responses.GetCategoriesOfClothingResponse;
import ekstra.jest.JEE.Responses.GetCategoryOfClothingResponse;

import java.util.UUID;

public interface ICategoryOfClothingController {
    GetCategoryOfClothingResponse getCategoryOfClothing(UUID id);
    GetCategoriesOfClothingResponse getAllCategoriesOfClothing();
    void addCategoryOfClothing(UUID id, PutCategoryOfClothingRequest request);
    void updateCategoryOfClothing(UUID id, UpdateCategoryOfClothingRequest request);
    void removeCategoryOfClothing(UUID id);
}