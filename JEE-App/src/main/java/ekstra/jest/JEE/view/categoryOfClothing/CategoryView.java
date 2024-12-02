package ekstra.jest.JEE.view.categoryOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.model.pieceOfClothing.PiecesOfClothingModel;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class CategoryView implements Serializable {

    private final EntitiesToModelsMapper mapper;

    private CategoryOfClothingService service;

    private PieceOfClothingService pieceService;

    @Setter
    @Getter
    private UUID categoryId;

    private String name;


    private PiecesOfClothingModel piecesOfClothing;

    @Inject
    public CategoryView(EntitiesToModelsMapper mapper, CategoryOfClothingService service, PieceOfClothingService pieceService) {
        this.mapper = mapper;
        //this.service = service;
        //this.pieceService = pieceService;
    }

    @EJB
    public void setService(CategoryOfClothingService service) {
        this.service = service;
    }

    @EJB
    public void setPieceService(PieceOfClothingService pieceService) {
        this.pieceService = pieceService;
    }

    public String getName() {
        if (name == null) {
            name = service.getCategoryOfClothing(categoryId).get().getName();
            return name;
        }
        return name;
    }

    public PiecesOfClothingModel getPiecesOfClothing() {
        if(piecesOfClothing == null) {
            piecesOfClothing = mapper.piecesToModel(pieceService.getAllPieceOfClothing().values().stream()
                    .filter(piece -> {
                        if(piece.getCategoryOfClothing() == null) {
                            return false;
                        }
                        return piece.getCategoryOfClothing().getId().equals(categoryId);
                    }).toList());
        }
        return piecesOfClothing;
    }

    public void deleteAction(PiecesOfClothingModel.PieceOfClothing pieceOfClothing) {
        pieceService.removePieceOfClothing(pieceOfClothing.getId());
        piecesOfClothing = null;
        //return "categories_view?faces-redirect=true&id=" + categoryId;
    }
}
