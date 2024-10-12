package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.Requests.PutPieceOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.Responses.GetPieceOfClothingResponse;
import ekstra.jest.JEE.Responses.GetPiecesOfClothingResponse;

import java.util.UUID;

public interface IPieceOfClothingController {
    GetPieceOfClothingResponse getPieceOfClothing(UUID pieceOfClothingId);
    GetPiecesOfClothingResponse getAllPiecesOfClothing();
    void addPieceOfClothing(UUID id, PutPieceOfClothingRequest putPieceOfClothingRequest);
    void updatePieceOfClothing(UUID id, UpdatePieceOfClothingRequest updatePieceOfClothingRequest);
    void removePieceOfClothing(UUID id);
    void assignCategory(UUID pieceId, UUID categoryId);
    void assignOwner(UUID pieceId, UUID ownerId);
}