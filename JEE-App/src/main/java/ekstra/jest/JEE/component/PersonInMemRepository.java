package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.interfaces.PersonRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonInMemRepository implements PersonRepository {

    private final PseudoDatabase pseudoDatabase = PseudoDatabase.getInstance();

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
        if(pseudoDatabase.getPersonMap().containsKey(key)){
            pseudoDatabase.getPersonMap().put(key, value);
        }
        else{
            throw new IllegalArgumentException("Person with UUID: " + key + " does not exist in the \"database\"");
        }
    }
}
