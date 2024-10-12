package ekstra.jest.JEE.Mappers;

import ekstra.jest.JEE.Requests.PutCategoryOfClothingRequest;
import ekstra.jest.JEE.Responses.GetCategoriesOfClothingResponse;
import ekstra.jest.JEE.Responses.GetCategoryOfClothingResponse;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class CategoryOfClothingMapper {
    public static CategoryOfClothing mapPutCategoryOfClothingRequestToCategoryOfClothing(PutCategoryOfClothingRequest putCategoryOfClothingRequest, UUID id) {
        return CategoryOfClothing.builder()
                .id(id)
                .name(putCategoryOfClothingRequest.getName())
                .isTrendy(putCategoryOfClothingRequest.getIsTrendy())
                .whereWearClothing(putCategoryOfClothingRequest.getWhereWearClothing().transform(whereWearClothing -> switch (whereWearClothing) {
                    case "HEAD" -> CategoryOfClothing.WhereWearClothing.HEAD;
                    case "NECK" -> CategoryOfClothing.WhereWearClothing.NECK;
                    case "HANDS" -> CategoryOfClothing.WhereWearClothing.HANDS;
                    case "LEGS" -> CategoryOfClothing.WhereWearClothing.LEGS;
                    case "FEET" -> CategoryOfClothing.WhereWearClothing.FEET;
                    default -> CategoryOfClothing.WhereWearClothing.TORSO;
                }))
                .clothingBelongingToType(new ArrayList<>())
                .build();
    }

    public static GetCategoryOfClothingResponse mapCategoryOfClothingToGetCategoryOfClothingResponse(CategoryOfClothing categoryOfClothing) {
        return GetCategoryOfClothingResponse.builder()
                .name(categoryOfClothing.getName())
                .isTrendy(categoryOfClothing.getIsTrendy())
                .whereWearClothing(categoryOfClothing.getWhereWearClothing().toString())
                .clothingBelongingToType(categoryOfClothing.getClothingBelongingToType() != null ? categoryOfClothing.getClothingBelongingToType().toString() : "No clothing belonging to type")
                .build();
    }

    public static GetCategoriesOfClothingResponse mapCategoriesOfClothingToGetCategoriesOfClothingResponse(HashMap<UUID, CategoryOfClothing> categoriesOfClothing){
        GetCategoriesOfClothingResponse response = new GetCategoriesOfClothingResponse();
        response.categories = new ArrayList<>();
        for (Map.Entry<UUID, CategoryOfClothing> entry : categoriesOfClothing.entrySet()) {
            CategoryOfClothing categoryOfClothing = entry.getValue();
            GetCategoriesOfClothingResponse.CategoryOfClothingDTO categoryOfClothingDTO = new GetCategoriesOfClothingResponse.CategoryOfClothingDTO();
            categoryOfClothingDTO.id = categoryOfClothing.getId().toString();
            categoryOfClothingDTO.name = categoryOfClothing.getName();
            categoryOfClothingDTO.isTrendy = categoryOfClothing.getIsTrendy();
            categoryOfClothingDTO.whereWearClothing = categoryOfClothing.getWhereWearClothing().toString();
            response.categories.add(categoryOfClothingDTO);
        }
        return response;
    }
}
