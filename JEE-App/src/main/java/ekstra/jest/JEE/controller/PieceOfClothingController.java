package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.PieceOfClothingMapper;
import ekstra.jest.JEE.Requests.PutPieceOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.Responses.GetPieceOfClothingResponse;
import ekstra.jest.JEE.Responses.GetPiecesOfClothingResponse;
import ekstra.jest.JEE.exceptions.BadRequestException;
import ekstra.jest.JEE.exceptions.NotFoundException;
import ekstra.jest.JEE.interfaces.IPieceOfClothingController;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import ekstra.jest.JEE.service.PersonService;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class PieceOfClothingController implements IPieceOfClothingController {
    private final PieceOfClothingService pieceOfClothingService;
    private final CategoryOfClothingService categoryOfClothingService;
    private final PersonService personService;

    @Inject
    public PieceOfClothingController(PieceOfClothingService pieceOfClothingService, CategoryOfClothingService categoryOfClothingService, PersonService personService) {
        this.pieceOfClothingService = pieceOfClothingService;
        this.categoryOfClothingService = categoryOfClothingService;
        this.personService = personService;
    }

    public GetPieceOfClothingResponse getPieceOfClothing(UUID pieceOfClothingId) {
        var pieceOfClothing = pieceOfClothingService.getPieceOfClothing(pieceOfClothingId).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        return PieceOfClothingMapper.mapPieceOfClothingToGetPieceOfClothingResponse(pieceOfClothing);
    }

    public GetPiecesOfClothingResponse getAllPiecesOfClothing() {
        return PieceOfClothingMapper.mapPiecesOfClothingToGetPiecesOfClothingResponse(pieceOfClothingService.getAllPieceOfClothing());
    }

    public void addPieceOfClothing(UUID id, PutPieceOfClothingRequest putPieceOfClothingRequest) {
        pieceOfClothingService.getPieceOfClothing(id).ifPresentOrElse(pieceOfClothing -> {
            throw new BadRequestException("Piece of clothing with this id already exists");
        }, () -> pieceOfClothingService.savePieceOfClothing(id, PieceOfClothingMapper.mapPutPieceOfClothingRequestToPieceOfClothing(putPieceOfClothingRequest, id)));
    }

    public void updatePieceOfClothing(UUID id, UpdatePieceOfClothingRequest updatePieceOfClothingRequest) {
        var pieceOfClothing = pieceOfClothingService.getPieceOfClothing(id).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        pieceOfClothingService.updatePieceOfClothing(pieceOfClothing, updatePieceOfClothingRequest);
    }

    public void removePieceOfClothing(UUID id){
        var piece = pieceOfClothingService.getPieceOfClothing(id).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        pieceOfClothingService.removePieceOfClothing(piece.getId());
    }

    public void assignCategory(UUID pieceId, UUID categoryId){
        var category = categoryOfClothingService.getCategoryOfClothing(categoryId).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        var piece = pieceOfClothingService.getPieceOfClothing(pieceId).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        pieceOfClothingService.assignPieceOfClothingToCategory(piece, category);
    }

    public void assignOwner(UUID pieceId, UUID ownerId){
        var owner = personService.getPerson(ownerId).orElseThrow(() -> new NotFoundException("No person with this id"));
        var piece = pieceOfClothingService.getPieceOfClothing(pieceId).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        pieceOfClothingService.assignPieceOfClothingToPerson(piece, owner);
    }
}
