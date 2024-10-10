package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.interfaces.PersonRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PersonService {
    private final PersonRepository personRepository;
    private final Path photoDirectory;

    public PersonService(PersonRepository personRepository, Path photoDirectory) {
        this.personRepository = personRepository;
        this.photoDirectory = photoDirectory;
    }

    public void savePerson(UUID key, Person value){
        personRepository.save(key, value);
    }

    public Optional<Person> getPerson(UUID key){
        return personRepository.get(key);
    }

    public HashMap<UUID, Person> getAllPersons() {
        return personRepository.getAll();
    }

    public void updatePerson(Person person, UpdatePersonRequest updatePersonRequest) {
        personRepository.update(person.getId(), new Person(
                person.getId(),
                person.getFirstName(),
                updatePersonRequest.getMoneyInBankAcc(),
                updatePersonRequest.getLastName(),
                person.getDateOfBirth(),
                person.getOwnedClothing(),
                person.getPhoto()
        ));
    }

    public void removePerson(UUID key) {
        personRepository.remove(key);
    }

    public void addPersonPhoto(Person person, InputStream is) {
        try {
            Files.createDirectories(photoDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Could not create photo directory", e);
        }
        try {
            Path photoPath = photoDirectory.resolve(person.getId().toString() + ".png");
            Files.copy(is, photoPath, StandardCopyOption.REPLACE_EXISTING);
            person.setPhoto(photoPath);
            personRepository.update(person.getId(), person);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getPersonPhoto(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void patchPersonPhoto(Person person, InputStream is) {
        try {
            Path photoPath = photoDirectory.resolve(person.getId().toString() + ".png");
            Files.copy(is, photoPath, StandardCopyOption.REPLACE_EXISTING);
            person.setPhoto(photoPath);
            personRepository.update(person.getId(), person);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
