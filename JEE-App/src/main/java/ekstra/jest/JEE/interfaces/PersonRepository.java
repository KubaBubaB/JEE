package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.businessClasses.person.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends Repository<UUID, Person> {
    @Override
    public Optional<Person> get(UUID key);

    @Override
    public HashMap<UUID, Person> getAll();

    @Override
    public void save(UUID key, Person value);
    @Override
    public void remove(UUID key);

    @Override
    public void update(UUID key, Person value);
}
