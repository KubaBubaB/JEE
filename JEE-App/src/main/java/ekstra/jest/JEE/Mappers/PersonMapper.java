package ekstra.jest.JEE.Mappers;

import ekstra.jest.JEE.Requests.PutPersonRequest;
import ekstra.jest.JEE.Responses.GetPersonResponse;
import ekstra.jest.JEE.Responses.GetPersonsResponse;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;

import java.util.*;
import java.util.stream.Collectors;

public class PersonMapper {
    public static Person mapPutPersonRequestToPerson(PutPersonRequest putPersonRequest, UUID id) {
        return Person.builder()
                .id(id)
                .firstName(putPersonRequest.getFirstName())
                .lastName(putPersonRequest.getLastName())
                .dateOfBirth(new Date(Long.parseLong(putPersonRequest.getDateOfBirth())))
                .moneyInBankAcc(putPersonRequest.getMoneyInBankAcc())
                .ownedClothing(new ArrayList<>())
                .build();
    }

    public static GetPersonResponse mapPersonToGetPersonResponse(Person person) {
        return GetPersonResponse.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth().toString())
                .moneyInBankAcc(person.getMoneyInBankAcc())
                .ownedClothing(person.getOwnedClothing().stream().map(PieceOfClothing::toString).collect(Collectors.joining(", ")))
                .build();
    }

    public static GetPersonsResponse mapPersonsToGetPersonsResponse(HashMap<UUID, Person> persons){
        GetPersonsResponse response = new GetPersonsResponse();
        response.persons = new ArrayList<>();
        for (Map.Entry<UUID, Person> entry : persons.entrySet()) {
            Person person = entry.getValue();
            GetPersonsResponse.PersonDTO personDTO = new GetPersonsResponse.PersonDTO();
            personDTO.id = person.getId();
            personDTO.firstName = person.getFirstName();
            personDTO.lastName = person.getLastName();
            personDTO.dateOfBirth = person.getDateOfBirth().toString();
            personDTO.moneyInBankAcc = person.getMoneyInBankAcc();
            response.persons.add(personDTO);
        }
        return response;
    }
}
