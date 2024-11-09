package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.PieceOfClothingMapper;
import ekstra.jest.JEE.Requests.PutPieceOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.Responses.GetPieceOfClothingResponse;
import ekstra.jest.JEE.Responses.GetPiecesOfClothingResponse;
import ekstra.jest.JEE.interfaces.IPieceOfClothingController;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import ekstra.jest.JEE.service.PersonService;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;

import java.util.UUID;

@Path("")
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

    @Override
    public GetPieceOfClothingResponse getPieceOfClothing(UUID pieceOfClothingId) {
        var pieceOfClothing = pieceOfClothingService.getPieceOfClothing(pieceOfClothingId).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        return PieceOfClothingMapper.mapPieceOfClothingToGetPieceOfClothingResponse(pieceOfClothing);
    }

    @Override
    public GetPiecesOfClothingResponse getAllPiecesOfClothing() {
        return PieceOfClothingMapper.mapPiecesOfClothingToGetPiecesOfClothingResponse(pieceOfClothingService.getAllPieceOfClothing());
    }

    @Override
    public GetPiecesOfClothingResponse getAllPiecesOfClothingInCategory(UUID categoryId) {
        var category = categoryOfClothingService.getCategoryOfClothing(categoryId).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        return PieceOfClothingMapper.mapPiecesOfClothingToGetPiecesOfClothingResponse(pieceOfClothingService.getAllPieceOfClothingInCategory(category));
    }


    @Override
    public void addPieceOfClothing(UUID id, UUID categoryId, PutPieceOfClothingRequest putPieceOfClothingRequest) {
        var category = categoryOfClothingService.getCategoryOfClothing(categoryId).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        pieceOfClothingService.getPieceOfClothing(id).ifPresentOrElse(pieceOfClothing -> {
            throw new BadRequestException("Piece of clothing with this id already exists");
        }, () -> {
            var piece = PieceOfClothingMapper.mapPutPieceOfClothingRequestToPieceOfClothing(putPieceOfClothingRequest, id, category);
            //categoryOfClothingService.assignPieceOfClothingToCategory(categoryId, piece); // Maybe not neede with JPA?
            pieceOfClothingService.savePieceOfClothing(id, piece);
        });
    }

    @Override
    public void updatePieceOfClothing(UUID id, UUID categoryId, UpdatePieceOfClothingRequest updatePieceOfClothingRequest) {
        var pieceOfClothing = pieceOfClothingService.getPieceOfClothing(id).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        var category = categoryOfClothingService.getCategoryOfClothing(categoryId).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        if(!pieceOfClothing.getCategoryOfClothing().getId().equals(category.getId())){
            throw new BadRequestException("No piece of clothing with this id within this category");
        }
        pieceOfClothingService.updatePieceOfClothing(pieceOfClothing, updatePieceOfClothingRequest);
    }

    @Override
    public void removePieceOfClothing(UUID id, UUID categoryId) {
        var category = categoryOfClothingService.getCategoryOfClothing(categoryId).orElseThrow(() -> new NotFoundException("No category of clothing with this id"));
        var piece = pieceOfClothingService.getPieceOfClothing(id).orElseThrow(() -> new NotFoundException("No piece of clothing with this id"));
        if(!category.getId().equals(piece.getCategoryOfClothing().getId())){
            throw new BadRequestException("No piece of clothing with this id within this category");
        }
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
