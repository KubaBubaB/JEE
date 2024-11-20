package ekstra.jest.JEE.controller;

import ekstra.jest.JEE.Mappers.PersonMapper;
import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.Responses.GetPersonResponse;
import ekstra.jest.JEE.Responses.GetPersonsResponse;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.interfaces.IPersonController;
import ekstra.jest.JEE.service.PersonService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;

import java.io.InputStream;
import java.util.UUID;

@Path("")
@RolesAllowed(PersonRoles.USER)
public class PersonController implements IPersonController {
    private PersonService personService;

    @EJB
    public void setService(PersonService personService) {
        this.personService = personService;
    }

    public GetPersonResponse getPerson(UUID personId) {
        try{
            var person = personService.getPerson(personId).orElseThrow(() -> new NotFoundException("No person with this id"));
            return PersonMapper.mapPersonToGetPersonResponse(person);
        }
        catch(EJBException ex){
            throw new BadRequestException("Wrong access level");
        }
    }

    public GetPersonsResponse getAllPersons() {
        try{
            return PersonMapper.mapPersonsToGetPersonsResponse(personService.getAllPersons());
        }
        catch(EJBException ex){
            throw new BadRequestException("Wrong access level");
        }
    }

    @PermitAll
    public void addPerson(UUID id, PutPersonRequest putPersonRequest) {
        personService.savePerson(id, PersonMapper.mapPutPersonRequestToPerson(putPersonRequest, id));
    }

    public void updatePerson(UUID id, UpdatePersonRequest updatePersonRequest) {
        try{
            var person = personService.getPerson(id).orElseThrow(() -> new NotFoundException("No person with this id"));
            personService.updatePerson(person, updatePersonRequest);
        }
        catch(EJBException ex){
            throw new BadRequestException("Wrong access level");
        }
    }

    public void removePerson(UUID id) {
        try{
            personService.removePerson(id);
        }
        catch(EJBException ex){
            throw new BadRequestException("Wrong access level");
        }
    }

    @PermitAll
    public String login(String login, String password) {
        if(personService.verify(login, password)){
            return personService.generateHash(password);
        }
        throw new BadRequestException("Invalid login or password");
    }



                                    /*               DEPRECETED                   */
                                     /*              \ /\ /\ /                   */
                                      /*              v  v  v                   */



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

    public void removePersonPhoto(UUID id) {
        var person = personService.getPerson(id).orElseThrow(() -> new NotFoundException("No person with this id"));
        if (person.getPhoto() == null) {
            throw new BadRequestException("Person does not have a photo to remove");
        }
        personService.removePersonPhoto(person);
    }
}
