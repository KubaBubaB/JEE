package ekstra.jest.JEE.view.pieceOfClothing;

import ekstra.jest.JEE.Mappers.EntitiesToModelsMapper;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.model.pieceOfClothing.PieceOfClothingEditModel;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
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
    private Long version;

    @Getter
    @Setter
    private PieceOfClothingEditModel pieceOfClothing = PieceOfClothingEditModel.builder().build();

    private final PieceOfClothingService pieceOfClothingService;

    private final EntitiesToModelsMapper mapper;

    private final FacesContext facesContext;

    @Inject
    public PieceOfClothingEdit(PieceOfClothingService pieceOfClothingService, EntitiesToModelsMapper mapper, FacesContext facesContext) {
        this.pieceOfClothingService = pieceOfClothingService;
        this.mapper = mapper;
        this.facesContext = facesContext;
    }

    public void init() throws IOException {
        Optional<PieceOfClothing> piece = pieceOfClothingService.getPieceOfClothing(id);
        if (piece.isPresent()) {
            this.pieceOfClothing = mapper.pieceToEditModel(piece.get());
            this.version = piece.get().getVersion();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public String saveAction() throws IOException {
        pieceOfClothing.setVersion(version);
        PieceOfClothingEditModel pieceModel = pieceOfClothing;
        try{
            pieceOfClothingService.updatePieceOfClothing(mapper.editModelToPiece(pieceOfClothing, pieceOfClothingService.getPieceOfClothing(id).get()));
            return "/categories/categories_view.xhtml?faces-redirect=true&id=" + pieceOfClothingService.getPieceOfClothing(id).get().getCategoryOfClothing().getId();
        }
        catch (Exception ex){
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                String message = "UWAGA: Obiekt jest nieaktualny i nie można go zaktualizować.";
                message += "Stan obiektu aktualnie w bazie:";
                message += pieceOfClothingService.getPieceOfClothing(id).toString();
                message += "\nJeżeli jesteś pewny ze chcesz edytować ten element, wybierz \'Zapisz\' ponownie";
                facesContext.addMessage(null, new FacesMessage(message));
                pieceOfClothing = pieceModel;
            }
            return null ;
        }

    }
}
