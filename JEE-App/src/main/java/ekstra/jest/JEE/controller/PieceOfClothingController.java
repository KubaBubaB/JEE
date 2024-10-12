package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.PieceOfClothingMapper;
import ekstra.jest.JEE.Requests.PutPieceOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.Responses.GetPieceOfClothingResponse;
import ekstra.jest.JEE.Responses.GetPiecesOfClothingResponse;
import ekstra.jest.JEE.exceptions.BadRequestException;
import ekstra.jest.JEE.exceptions.NotFoundException;
import ekstra.jest.JEE.service.PieceOfClothingService;

import java.util.UUID;

public class PieceOfClothingController {
    private final PieceOfClothingService pieceOfClothingService;

    public PieceOfClothingController(PieceOfClothingService pieceOfClothingService) {
        this.pieceOfClothingService = pieceOfClothingService;
    }

    public GetPieceOfClothingResponse getPieceOfClothing(UUID pieceOfClothingId) {
        if (pieceOfClothingService.getPieceOfClothing(pieceOfClothingId).isEmpty()) {
            throw new NotFoundException("No piece of clothing with this id");
        }
        return PieceOfClothingMapper.mapPieceOfClothingToGetPieceOfClothingResponse(pieceOfClothingService.getPieceOfClothing(pieceOfClothingId).orElseThrow(NotFoundException::new));
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
}
