package ekstra.jest.JEE.view.pieceOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingModel;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
public class PieceOfClothingView {
    private final EntitiesToModelsMapper mapper;

    private final PieceOfClothingService service;

    @Inject
    public PieceOfClothingView(EntitiesToModelsMapper mapper, PieceOfClothingService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @Getter
    private PieceOfClothingModel piece = PieceOfClothingModel.builder().build();

    @Setter
    @Getter
    private UUID pieceId;

    public void init() throws IOException {
        Optional<PieceOfClothing> piece = service.getPieceOfClothing(pieceId);
        if (piece.isPresent()) {
            this.piece = mapper.pieceToModel(piece.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }
}
