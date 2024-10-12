package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class CategoryOfClothingService {
    private final CategoryOfClothingRepository categoryOfClothingRepository;
    private final PieceOfClothingRepository pieceOfClothingRepository;

    public CategoryOfClothingService(CategoryOfClothingRepository categoryOfClothingRepository, PieceOfClothingRepository pieceOfClothingRepository) {
        this.categoryOfClothingRepository = categoryOfClothingRepository;
        this.pieceOfClothingRepository = pieceOfClothingRepository;
    }

    public void saveCategoryOfClothing(UUID key, CategoryOfClothing value){
        categoryOfClothingRepository.save(key, value);
    }

    public Optional<CategoryOfClothing> getCategoryOfClothing(UUID key){
        return categoryOfClothingRepository.get(key);
    }

    public HashMap<UUID, CategoryOfClothing> getAllCategoryOfClothing() {
        return categoryOfClothingRepository.getAll();
    }

    public void updateCategoryOfClothing(UUID key, UpdateCategoryOfClothingRequest request){
        categoryOfClothingRepository.get(key).ifPresent(category -> {
            category.setIsTrendy(request.getIsTrendy());
            categoryOfClothingRepository.update(key, category);
        });
    }

    public void removeCategoryOfClothing(UUID key) {
        categoryOfClothingRepository.get(key).ifPresent(category -> {
            category.getClothingBelongingToType().forEach(pieceOfClothing -> {
                pieceOfClothingRepository.get(pieceOfClothing.getId()).ifPresent(piece -> {
                    piece.setCategoryOfClothing(null);
                    pieceOfClothingRepository.update(piece.getId(), piece);
                });
            });
        });
        categoryOfClothingRepository.remove(key);
    }

}
