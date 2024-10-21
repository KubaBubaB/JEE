package ekstra.jest.JEE.view.pieceOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingEditModel;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class PieceOfClothingEdit {

    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private PieceOfClothingEditModel pieceOfClothing = PieceOfClothingEditModel.builder().build();

    private final PieceOfClothingService pieceOfClothingService;

    private final EntitiesToModelsMapper mapper;

    @Inject
    public PieceOfClothingEdit(PieceOfClothingService pieceOfClothingService, EntitiesToModelsMapper mapper) {
        this.pieceOfClothingService = pieceOfClothingService;
        this.mapper = mapper;
    }

    public void init() throws IOException {
        Optional<PieceOfClothing> piece = pieceOfClothingService.getPieceOfClothing(id);
        if (piece.isPresent()) {
            this.pieceOfClothing = mapper.pieceToEditModel(piece.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public String saveAction() {
        pieceOfClothingService.updatePieceOfClothing(pieceOfClothingService.getPieceOfClothing(id).get(), UpdatePieceOfClothingRequest.builder()
                .resellPrice(pieceOfClothing.getResellPrice())
                .build());
        return "/categories/categories_view.xhtml?faces-redirect=true&id=" + pieceOfClothingService.getPieceOfClothing(id).get().getCategoryOfClothing().getId();
    }
}
