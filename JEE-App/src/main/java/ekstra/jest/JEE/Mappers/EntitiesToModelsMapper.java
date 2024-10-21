package ekstra.jest.JEE.Mappers;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.model.categoryOfClothing.CategoriesOfClothingModel;
import ekstra.jest.JEE.model.categoryOfClothing.CategoryEditModel;
import ekstra.jest.JEE.model.categoryOfClothing.CreateCategoryModel;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingCreateModel;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingEditModel;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingModel;
import ekstra.jest.JEE.model.pieceOfClothing.PiecesOfClothingModel;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EntitiesToModelsMapper {
    public CategoriesOfClothingModel categoriesToModel(HashMap<UUID, CategoryOfClothing> categories) {
        return CategoriesOfClothingModel.builder()
                .categories(categories.values().stream()
                        .map(category -> CategoriesOfClothingModel.Category.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .isTrendy(category.getIsTrendy())
                                .whereWearClothing(String.valueOf(category.getWhereWearClothing()))
                                .build())
                        .toList())
                .build();
    }

    public PiecesOfClothingModel piecesToModel(List<PieceOfClothing> clothingBelongingToType) {
        if(clothingBelongingToType == null || clothingBelongingToType.isEmpty()) {
            return PiecesOfClothingModel.builder().build();
        }
        return PiecesOfClothingModel.builder()
                .pieces(clothingBelongingToType.stream()
                        .map(piece -> PiecesOfClothingModel.PieceOfClothing.builder()
                                .id(piece.getId())
                                .name(piece.getName())
                                .size(String.valueOf(piece.getSize()))
                                .resellPrice(piece.getResellPrice())
                                .nameOfOwner(piece.getOwner().getFirstName() + " " + piece.getOwner().getLastName())
                                .build())
                        .toList())
                .build();
    }

    public CategoryOfClothing modelToCategory(UUID key, CreateCategoryModel category) {
        return CategoryOfClothing.builder()
                .id(key)
                .name(category.getName())
                .isTrendy(category.getIsTrendy().equals("It is trendy!"))
                .whereWearClothing(CategoryOfClothing.WhereWearClothing.valueOf(category.getWhereWearClothing()))
                .clothingBelongingToType(new ArrayList<>())
                .build();
    }

    public CategoryEditModel categoryToEditModel(CategoryOfClothing categoryOfClothing) {
        return CategoryEditModel.builder()
                .name(categoryOfClothing.getName())
                .isTrendy(categoryOfClothing.getIsTrendy() ? "It is trendy!" : "meh, not really")
                .build();
    }

    public PieceOfClothingModel pieceToModel(PieceOfClothing pieceOfClothing) {
        return PieceOfClothingModel.builder()
                .id(pieceOfClothing.getId())
                .name(pieceOfClothing.getName())
                .size(String.valueOf(pieceOfClothing.getSize()))
                .resellPrice(pieceOfClothing.getResellPrice())
                .ownersName(pieceOfClothing.getOwner().getFirstName() + " " + pieceOfClothing.getOwner().getLastName())
                .categoryName(pieceOfClothing.getCategoryOfClothing().getName())
                .build();
    }

    public PieceOfClothingEditModel pieceToEditModel(PieceOfClothing pieceOfClothing) {
        return PieceOfClothingEditModel.builder()
                .name(pieceOfClothing.getName())
                .resellPrice(pieceOfClothing.getResellPrice())
                .build();
    }

    public PieceOfClothing modelToPiece(UUID key, PieceOfClothingCreateModel piece, Person owner, CategoryOfClothing category) {
        return PieceOfClothing.builder()
                .id(key)
                .name(piece.getName())
                .size(PieceOfClothing.ClothingSize.valueOf(piece.getSize()))
                .resellPrice(piece.getResellPrice())
                .owner(owner)
                .categoryOfClothing(category)
                .build();
    }
}
