package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.Responses.GetPersonResponse;
import ekstra.jest.JEE.Responses.GetPersonsResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface IPersonController {

    @GET
    @Path("/persons/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPersonResponse getPerson(@PathParam("personId")UUID personId);

    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    GetPersonsResponse getAllPersons();

    @PUT
    @Path("/persons/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void addPerson(@PathParam("id") UUID id, PutPersonRequest putPersonRequest);

    @PATCH
    @Path("/persons/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updatePerson(@PathParam("id") UUID id, UpdatePersonRequest updatePersonRequest);

    @DELETE
    @Path("/persons/{id}")
    void removePerson(@PathParam("id") UUID id);

    @POST
    @Path("/login/{login}/{password}")
    String login(@PathParam("login") String login, @PathParam("password") String password);
    void addPersonPhoto(UUID id, InputStream photo);
    byte[] getPersonPhoto(UUID id);
    void patchPersonPhoto(UUID id, InputStream is);
    void removePersonPhoto(UUID id);
}