package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.Requests.PutCategoryOfClothingRequest;
import ekstra.jest.JEE.Requests.UpdateCategoryOfClothingRequest;
import ekstra.jest.JEE.Responses.GetCategoriesOfClothingResponse;
import ekstra.jest.JEE.Responses.GetCategoryOfClothingResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface ICategoryOfClothingController {
    @GET
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoryOfClothingResponse getCategoryOfClothing(@PathParam("id") UUID id);

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoriesOfClothingResponse getAllCategoriesOfClothing();

    @PUT
    @Path("/categories/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void addCategoryOfClothing(@PathParam("id") UUID id, PutCategoryOfClothingRequest request);

    @PATCH
    @Path("/categories/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateCategoryOfClothing(@PathParam("id") UUID id, UpdateCategoryOfClothingRequest request);

    @DELETE
    @Path("/categories/{id}")
    void removeCategoryOfClothing(@PathParam("id") UUID id);
}