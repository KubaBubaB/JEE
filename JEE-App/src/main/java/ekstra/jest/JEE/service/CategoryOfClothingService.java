package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class CategoryOfClothingService {
    private final CategoryOfClothingRepository categoryOfClothingRepository;
    private final PieceOfClothingRepository pieceOfClothingRepository;
    private final SecurityContext securityContext;

    @Inject
    public CategoryOfClothingService(CategoryOfClothingRepository categoryOfClothingRepository,
                                     PieceOfClothingRepository pieceOfClothingRepository,
                                     @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.categoryOfClothingRepository = categoryOfClothingRepository;
        this.pieceOfClothingRepository = pieceOfClothingRepository;
        this.securityContext = securityContext;
    }

    //@RolesAllowed(PersonRoles.ADMIN)
    public void saveCategoryOfClothing(UUID key, CategoryOfClothing value){
        checkUserRole();
        categoryOfClothingRepository.save(key, value);
    }

    //@RolesAllowed(PersonRoles.USER)
    public Optional<CategoryOfClothing> getCategoryOfClothing(UUID key){
        checkUserRole();
        return categoryOfClothingRepository.get(key);
    }

    //@RolesAllowed(PersonRoles.USER)
    public Optional<CategoryOfClothing> getCategoryOfClothingByName(String name){
        checkUserRole();
        return categoryOfClothingRepository.getAll().values().stream().filter(category -> category.getName().equals(name)).findFirst();
    }

    //@RolesAllowed(PersonRoles.USER)
    public HashMap<UUID, CategoryOfClothing> getAllCategoryOfClothing() {
        checkUserRole();
        return categoryOfClothingRepository.getAll();
    }

    //@RolesAllowed(PersonRoles.ADMIN)
    public void updateCategoryOfClothing(CategoryOfClothing category, UpdateCategoryOfClothingRequest request){
        checkUserRole();
        category.setIsTrendy(request.getIsTrendy());
        categoryOfClothingRepository.update(category.getId(), category);
    }

    //@RolesAllowed(PersonRoles.ADMIN)
    public void removeCategoryOfClothing(UUID key) {
        // Not needed with JPA
        //categoryOfClothingRepository.get(key).ifPresent(category -> {
        //    category.getClothingBelongingToType().forEach(pieceOfClothing -> {
        //        pieceOfClothingRepository.get(pieceOfClothing.getId()).ifPresent(piece -> {
        //            piece.setCategoryOfClothing(null);
        //            pieceOfClothingRepository.update(piece.getId(), piece);
        //        });
        //    });
        //});
        checkUserRole();
        categoryOfClothingRepository.remove(key);
    }

    public void assignPieceOfClothingToCategory(UUID categoryId, PieceOfClothing piece) {
        categoryOfClothingRepository.get(categoryId).ifPresent(category -> {
            piece.setCategoryOfClothing(category);
            pieceOfClothingRepository.update(piece.getId(), piece);
        });
    }

    private void checkUserRole() throws SecurityException {
        if (!securityContext.isCallerInRole(PersonRoles.USER)) {
            throw new SecurityException("Caller not authorized.");
        }
    }
}
