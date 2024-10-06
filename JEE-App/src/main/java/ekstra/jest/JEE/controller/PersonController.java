package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.PersonMapper;
import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.Responses.GetPersonResponse;
import ekstra.jest.JEE.Responses.GetPersonsResponse;
import ekstra.jest.JEE.service.PersonService;

import java.io.InputStream;
import java.util.UUID;

public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public GetPersonResponse getPerson(UUID personId) {
        return PersonMapper.mapPersonToGetPersonResponse(personService.getPerson(personId));
    }

    public GetPersonsResponse getAllPersons() {
        return PersonMapper.mapPersonsToGetPersonsResponse(personService.getAllPersons());
    }

    public void addPerson(UUID id, PutPersonRequest putPersonRequest) {
        personService.savePerson(id, PersonMapper.mapPutPersonRequestToPerson(putPersonRequest));
    }

    public void updatePerson(UUID id, UpdatePersonRequest updatePersonRequest) {
        personService.updatePerson(id, updatePersonRequest);
    }

    public void removePerson(UUID id) {
        personService.removePerson(id);
    }

    public void addPersonPhoto(UUID id, InputStream photo) {
        personService.addPersonPhoto(id, photo);
    }

    public byte[] getPersonPhoto(UUID id) {
        return personService.getPersonPhoto(id);
    }
}
