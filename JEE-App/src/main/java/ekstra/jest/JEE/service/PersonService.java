package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePersonRequest;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.exceptions.BadRequestException;
import ekstra.jest.JEE.interfaces.PersonRepository;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final PieceOfClothingRepository pieceOfClothingRepository;
    private final Path photoDirectory;
    private final Pbkdf2PasswordHash passwordHash;
    private final SecurityContext securityContext;

    @Inject
    public PersonService(PersonRepository personRepository, PieceOfClothingRepository pieceOfClothingRepository, Pbkdf2PasswordHash passwordHash, SecurityContext securityContext) {
        this.personRepository = personRepository;
        this.pieceOfClothingRepository = pieceOfClothingRepository;
        this.passwordHash = passwordHash;
        this.securityContext = securityContext;
        this.photoDirectory = Path.of("C:\\StudiaHere\\TEMP");
    }

    @PermitAll
    public void savePerson(UUID key, Person value){
        if(personRepository.contains(key, value.getLogin())){
            throw new BadRequestException("Person with this login or ID already exists");
        }
        value.setPassword(passwordHash.generate(value.getPassword().toCharArray()));
        personRepository.save(key, value);
    }

    @RolesAllowed(PersonRoles.USER)
    public Optional<Person> getPerson(UUID key){
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            return personRepository.get(key).filter(person -> securityContext.getCallerPrincipal().getName().equals(person.getLogin()));
        }
        return personRepository.get(key);
    }

    @RolesAllowed(PersonRoles.ADMIN)
    public Optional<Person> getPersonByFirstNameAndLastName(String name) {
        String[] names = name.split(" ");
        return personRepository.getAll().values().stream()
                .filter(person -> person.getFirstName().equals(names[0]) && person.getLastName().equals(names[1]))
                .findFirst();
    }

    @RolesAllowed(PersonRoles.ADMIN)
    public HashMap<UUID, Person> getAllPersons() {
        return personRepository.getAll();
    }


    @RolesAllowed(PersonRoles.ADMIN)
    public void updatePerson(Person person, UpdatePersonRequest updatePersonRequest) {
        personRepository.update(person.getId(), new Person(
                person.getId(),
                person.getLogin(),
                person.getPassword(),
                person.getFirstName(),
                updatePersonRequest.getMoneyInBankAcc(),
                updatePersonRequest.getLastName(),
                person.getDateOfBirth(),
                person.getOwnedClothing(),
                person.getPhoto(),
                person.getRoles()
        ));
    }

    @RolesAllowed(PersonRoles.ADMIN)
    public void removePerson(UUID key) {
        // Not needed with JPA
        //personRepository.get(key).ifPresent(person -> person.getOwnedClothing().forEach(pieceOfClothing -> {
        //    pieceOfClothingRepository.get(pieceOfClothing.getId()).ifPresent(piece -> {
        //        piece.setOwner(null);
        //        pieceOfClothingRepository.update(piece.getId(), piece);
        //    });
        //}));
        personRepository.remove(key);
    }

    @PermitAll
    public String generateHash(String password){
        return passwordHash.generate(password.toCharArray());
    }

    @PermitAll
    public boolean verify(String login, String password){
        Optional<Person> person = personRepository.getAll().values().stream()
                .filter(p -> p.getLogin().equals(login))
                .findFirst();
        return person.isPresent() && passwordHash.verify(password.toCharArray(), person.get().getPassword());
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

    public void removePersonPhoto(Person person) {
        try {
            Files.delete(person.getPhoto());
            person.setPhoto(null);
            personRepository.update(person.getId(), person);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}