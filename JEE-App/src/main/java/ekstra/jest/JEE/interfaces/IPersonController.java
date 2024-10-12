package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.Responses.GetPersonResponse;
import ekstra.jest.JEE.Responses.GetPersonsResponse;

import java.io.InputStream;
import java.util.UUID;

public interface IPersonController {
    GetPersonResponse getPerson(UUID personId);
    GetPersonsResponse getAllPersons();
    void addPerson(UUID id, PutPersonRequest putPersonRequest);
    void updatePerson(UUID id, UpdatePersonRequest updatePersonRequest);
    void removePerson(UUID id);
    void addPersonPhoto(UUID id, InputStream photo);
    byte[] getPersonPhoto(UUID id);
    void patchPersonPhoto(UUID id, InputStream is);
}