package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.Requests.PutPieceOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.Responses.GetPieceOfClothingResponse;
import ekstra.jest.JEE.Responses.GetPiecesOfClothingResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface IPieceOfClothingController {

    @GET
    @Path("/pieces/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPieceOfClothingResponse getPieceOfClothing(@PathParam("id") UUID pieceOfClothingId);

    @GET
    @Path("/pieces")
    @Produces(MediaType.APPLICATION_JSON)
    GetPiecesOfClothingResponse getAllPiecesOfClothing();

    @GET
    @Path("/categories/{categoryId}/pieces")
    @Produces(MediaType.APPLICATION_JSON)
    GetPiecesOfClothingResponse getAllPiecesOfClothingInCategory(@PathParam("categoryId") UUID categoryId);

    @PUT
    @Path("/categories/{categoryId}/pieces/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void addPieceOfClothing(@PathParam("id") UUID id, @PathParam("categoryId") UUID categoryId, PutPieceOfClothingRequest putPieceOfClothingRequest);

    @PATCH
    @Path("/categories/{categoryId}/pieces/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updatePieceOfClothing(@PathParam("id") UUID id,@PathParam("categoryId") UUID categoryId, UpdatePieceOfClothingRequest updatePieceOfClothingRequest);

    @DELETE
    @Path("/categories/{categoryId}/pieces/{id}")
    void removePieceOfClothing(@PathParam("id") UUID id, @PathParam("categoryId") UUID categoryId);
    void assignCategory(UUID pieceId, UUID categoryId);
    void assignOwner(UUID pieceId, UUID ownerId);
}