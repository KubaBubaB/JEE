package ekstra.jest.JEE.Mappers;

import ekstra.jest.JEE.Requests.PutPieceOfClothingRequest;
import ekstra.jest.JEE.Responses.GetPieceOfClothingResponse;
import ekstra.jest.JEE.Responses.GetPiecesOfClothingResponse;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PieceOfClothingMapper {
    public static PieceOfClothing mapPutPieceOfClothingRequestToPieceOfClothing(PutPieceOfClothingRequest putPieceOfClothingRequest, UUID id) {
        return PieceOfClothing.builder()
                .id(id)
                .name(putPieceOfClothingRequest.getName())
                .resellPrice(putPieceOfClothingRequest.getResellPrice())
                .size(putPieceOfClothingRequest.getSize().transform(size -> switch (size) {
                    case "S" -> PieceOfClothing.ClothingSize.S;
                    case "L" -> PieceOfClothing.ClothingSize.L;
                    case "XL" -> PieceOfClothing.ClothingSize.XL;
                    default -> PieceOfClothing.ClothingSize.M;
                }))
                .build();
    }

    public static GetPieceOfClothingResponse mapPieceOfClothingToGetPieceOfClothingResponse(PieceOfClothing pieceOfClothing) {
        return GetPieceOfClothingResponse.builder()
                .name(pieceOfClothing.getName())
                .owner(pieceOfClothing.getOwner().toString())
                .categoryOfClothing(pieceOfClothing.getCategoryOfClothing().toString())
                .resellPrice(pieceOfClothing.getResellPrice().toString())
                .size(pieceOfClothing.getSize().toString())
                .build();
    }

    public static GetPiecesOfClothingResponse mapPiecesOfClothingToGetPiecesOfClothingResponse(HashMap<UUID, PieceOfClothing> piecesOfClothing){
        GetPiecesOfClothingResponse response = new GetPiecesOfClothingResponse();
        response.piecesOfClothing = new ArrayList<>();
        for (Map.Entry<UUID, PieceOfClothing> entry : piecesOfClothing.entrySet()) {
            PieceOfClothing pieceOfClothing = entry.getValue();
            GetPiecesOfClothingResponse.PieceOfClothingDTO pieceOfClothingDTO = new GetPiecesOfClothingResponse.PieceOfClothingDTO();
            pieceOfClothingDTO.id = pieceOfClothing.getId().toString();
            pieceOfClothingDTO.name = pieceOfClothing.getName();
            pieceOfClothingDTO.owner = pieceOfClothing.getOwner().toString();
            pieceOfClothingDTO.categoryOfClothing = pieceOfClothing.getCategoryOfClothing().toString();
            pieceOfClothingDTO.resellPrice = pieceOfClothing.getResellPrice();
            pieceOfClothingDTO.size = pieceOfClothing.getSize().toString();
            response.piecesOfClothing.add(pieceOfClothingDTO);
        }
        return response;
    }
}
