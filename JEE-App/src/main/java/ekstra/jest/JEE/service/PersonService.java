package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.exceptions.NotFoundException;
import ekstra.jest.JEE.interfaces.PersonRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(UUID key, Person value){
        personRepository.get(key).ifPresentOrElse(
                person -> {
                    throw new IllegalArgumentException();
                },
                () -> {
                    personRepository.save(key, value);
                }
        );
    }

    public Optional<Person> getPerson(UUID key){
        return personRepository.get(key);
    }

    public HashMap<UUID, Person> getAllPersons() {
        return personRepository.getAll();
    }

    public void updatePerson(UUID key, UpdatePersonRequest updatePersonRequest) {
        personRepository.get(key).ifPresentOrElse(
                person -> personRepository.update(key, new Person(
                        person.getFirstName(),
                        updatePersonRequest.getMoneyInBankAcc(),
                        updatePersonRequest.getLastName(),
                        person.getDateOfBirth(),
                        person.getOwnedClothing(),
                        person.getPhoto()
                )),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public void removePerson(UUID key) {
        personRepository.remove(key);
    }

    public void addPersonPhoto(UUID id, InputStream is) {
        personRepository.get(id).ifPresentOrElse(
                person -> {
                    try {
                        person.setPhoto(is.readAllBytes());
                        personRepository.update(id, person);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public byte[] getPersonPhoto(UUID id) {
        return personRepository.get(id).map(Person::getPhoto).orElseThrow(NotFoundException::new);
    }
}
