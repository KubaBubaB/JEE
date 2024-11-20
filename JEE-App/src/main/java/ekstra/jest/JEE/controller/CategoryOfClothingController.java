package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.CategoryOfClothingMapper;
import ekstra.jest.JEE.Requests.PutCategoryOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.Responses.GetCategoriesOfClothingResponse;
import ekstra.jest.JEE.Responses.GetCategoryOfClothingResponse;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.interfaces.ICategoryOfClothingController;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;

import java.util.UUID;

@Path("")
@RolesAllowed(PersonRoles.USER)
public class CategoryOfClothingController implements ICategoryOfClothingController {
    private CategoryOfClothingService categoryOfClothingService;

    @EJB
    public void setCategoryOfClothingService(CategoryOfClothingService categoryOfClothingService) {
        this.categoryOfClothingService = categoryOfClothingService;
    }

    public GetCategoryOfClothingResponse getCategoryOfClothing(UUID id){
        try{
            var categoryOfClothing = categoryOfClothingService.getCategoryOfClothing(id).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
            return CategoryOfClothingMapper.mapCategoryOfClothingToGetCategoryOfClothingResponse(categoryOfClothing);
        }
        catch (EJBException e){
            throw new BadRequestException("Smthng went wwong %s".formatted(e.getMessage()));
        }
    }

    public GetCategoriesOfClothingResponse getAllCategoriesOfClothing(){
        try{
            return CategoryOfClothingMapper.mapCategoriesOfClothingToGetCategoriesOfClothingResponse(categoryOfClothingService.getAllCategoryOfClothing());
        }
        catch (EJBException e){
            throw new BadRequestException("Smthng went wwong %s".formatted(e.getMessage()));
        }
    }

    public void addCategoryOfClothing(UUID id, PutCategoryOfClothingRequest request){
        try{
            categoryOfClothingService.getCategoryOfClothing(id).ifPresentOrElse(categoryOfClothing -> {
                throw new BadRequestException("Category of clothing with this id already exists");
            }, () -> categoryOfClothingService.saveCategoryOfClothing(id, CategoryOfClothingMapper.mapPutCategoryOfClothingRequestToCategoryOfClothing(request, id)));
        }
        catch (EJBException e){
            throw new BadRequestException("Smthng went wwong %s".formatted(e.getMessage()));
        }
    }

    public void updateCategoryOfClothing(UUID id, UpdateCategoryOfClothingRequest request){
        try{
            var categoryOfClothing = categoryOfClothingService.getCategoryOfClothing(id).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
            categoryOfClothingService.updateCategoryOfClothing(categoryOfClothing, request);
        }
        catch (EJBException e){
            throw new BadRequestException("Smthng went wwong %s".formatted(e.getMessage()));
        }
    }

    public void removeCategoryOfClothing(UUID id){
        try {
            categoryOfClothingService.removeCategoryOfClothing(id);
        }
        catch (EJBException e){
            throw new BadRequestException("Smthng went wwong %s".formatted(e.getMessage()));
        }
    }
}
