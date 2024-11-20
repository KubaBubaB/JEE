package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.interfaces.PersonRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class PersonPersistentRepository implements PersonRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Person> get(UUID key) {
        return Optional.ofNullable(em.find(Person.class, key));
    }

    @Override
    public HashMap<UUID, Person> getAll() {
        HashMap<UUID, Person> map = new HashMap<>();
        em.createQuery("select p from Person p", Person.class).getResultList().forEach(person -> map.put(person.getId(), person));
        return map;
    }

    @Override
    public void save(UUID key, Person value) {
        //em.refresh(value);
        em.persist(value);
    }

    @Override
    public void remove(UUID key) {
        em.refresh(em.find(Person.class, key));
        em.remove(em.find(Person.class, key));
    }

    @Override
    public void update(UUID key, Person value) {
        em.merge(value);
    }

    @Override
    public Optional<Person> getByLogin(String login) {
        return em.createQuery("select p from Person p where p.login = :login", Person.class)
                .setParameter("login", login)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public boolean contains(UUID key, String login) {
        var dupa = em.createQuery("select p from Person p where p.id = :key or p.login = :login", Person.class)
                .setParameter("key", key)
                .setParameter("login", login)
                .getResultList();
        return !em.createQuery("select p from Person p where p.id = :key or p.login = :login", Person.class)
                .setParameter("key", key)
                .setParameter("login", login)
                .getResultList().isEmpty();
    }
}
