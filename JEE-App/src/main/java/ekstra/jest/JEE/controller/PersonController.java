package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.PersonMapper;
import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.Responses.GetPersonResponse;
import ekstra.jest.JEE.Responses.GetPersonsResponse;
import ekstra.jest.JEE.exceptions.BadRequestException;
import ekstra.jest.JEE.exceptions.NotFoundException;
import ekstra.jest.JEE.service.PersonService;

import java.io.InputStream;
import java.util.UUID;

public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public GetPersonResponse getPerson(UUID personId) {
        if (personService.getPerson(personId).isEmpty()) {
            throw new NotFoundException("No person with this id");
        }
        return PersonMapper.mapPersonToGetPersonResponse(personService.getPerson(personId).orElseThrow(NotFoundException::new));
    }

    public GetPersonsResponse getAllPersons() {
        return PersonMapper.mapPersonsToGetPersonsResponse(personService.getAllPersons());
    }

    public void addPerson(UUID id, PutPersonRequest putPersonRequest) {
        personService.getPerson(id).ifPresentOrElse(person -> {
            throw new BadRequestException("Person with this id already exists");
        }, () -> personService.savePerson(id, PersonMapper.mapPutPersonRequestToPerson(putPersonRequest, id)));
    }

    public void updatePerson(UUID id, UpdatePersonRequest updatePersonRequest) {
        var person = personService.getPerson(id).orElseThrow(() -> new NotFoundException("No person with this id"));
        personService.updatePerson(person, updatePersonRequest);
    }

    public void removePerson(UUID id) {
        personService.removePerson(id);
    }

    public void addPersonPhoto(UUID id, InputStream photo) {
        var person = personService.getPerson(id).orElseThrow(() -> new NotFoundException("No person with this id"));
        personService.addPersonPhoto(person, photo);
    }

    public byte[] getPersonPhoto(UUID id) {
        var person = personService.getPerson(id).orElseThrow(() -> new NotFoundException("No person with this id"));
        var photoPath = person.getPhoto();
        if (photoPath == null) {
            throw new NotFoundException("Person does not have a photo");
        }
        return personService.getPersonPhoto(photoPath);
    }

    public void patchPersonPhoto(UUID id, InputStream is) {
        var person = personService.getPerson(id).orElseThrow(() -> new NotFoundException("No person with this id"));
        if (person.getPhoto() == null) {
            throw new BadRequestException("Person does not have a photo to update");
        }
        personService.patchPersonPhoto(person, is);

    }
}
