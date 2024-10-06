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
        if(personService.getPerson(personId).isEmpty()){
            throw new NotFoundException("No person with this id");
        }
        return PersonMapper.mapPersonToGetPersonResponse(personService.getPerson(personId).orElseThrow(NotFoundException::new));
    }

    public GetPersonsResponse getAllPersons() {
        return PersonMapper.mapPersonsToGetPersonsResponse(personService.getAllPersons());
    }

    public void addPerson(UUID id, PutPersonRequest putPersonRequest) {
        try{
            personService.savePerson(id, PersonMapper.mapPutPersonRequestToPerson(putPersonRequest));
        } catch (IllegalArgumentException e){
            throw new BadRequestException("Person with this id already exists");
        }
    }

    public void updatePerson(UUID id, UpdatePersonRequest updatePersonRequest) {
        try{
            personService.updatePerson(id, updatePersonRequest);
        }
        catch (NotFoundException e){
            throw new NotFoundException("No person with this id");
        }
    }

    public void removePerson(UUID id) {
        personService.removePerson(id);
    }

    public void addPersonPhoto(UUID id, InputStream photo) {
        try{
            personService.addPersonPhoto(id, photo);
        }
        catch (NotFoundException e){
            throw new NotFoundException("No person with this id");
        }
    }

    public byte[] getPersonPhoto(UUID id) {
        try{
            return personService.getPersonPhoto(id);
        }
        catch (NotFoundException e){
            throw new NotFoundException("No person with this id");
        }
    }
}
