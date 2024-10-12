package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.interfaces.PersonRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class PersonInMemRepository implements PersonRepository {

    private final PseudoDatabase pseudoDatabase;

    @Inject
    public PersonInMemRepository(PseudoDatabase pseudoDatabase) {
        this.pseudoDatabase = pseudoDatabase;
    }

    @Override
    public Optional<Person> get(UUID key) {
        return Optional.ofNullable(pseudoDatabase.getPersonMap().get(key));
    }

    @Override
    public HashMap<UUID, Person> getAll() {
        return pseudoDatabase.getPersonMap();
    }

    @Override
    public void save(UUID key, Person value) {
        pseudoDatabase.getPersonMap().put(key, value);
    }

    @Override
    public void remove(UUID key) {
        pseudoDatabase.getPersonMap().remove(key);
    }

    @Override
    public void update(UUID key, Person value) {
        pseudoDatabase.getPersonMap().put(key, value);
    }
}
