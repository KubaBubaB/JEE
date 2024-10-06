package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.interfaces.PersonRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(UUID key, Person value){
        personRepository.save(key, value);
    }

    public Person getPerson(UUID key){
        return personRepository.get(key).orElseThrow(IllegalArgumentException::new);
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
                    throw new IllegalArgumentException();
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
                    throw new IllegalArgumentException();
                }
        );
    }

    public byte[] getPersonPhoto(UUID id) {
        return personRepository.get(id).map(Person::getPhoto).orElseThrow(IllegalArgumentException::new);
    }
}
