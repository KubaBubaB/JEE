package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.interfaces.PersonRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.HashMap;
import java.util.List;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> query = cb.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.select(root);
        em.createQuery(query).getResultList().forEach(person -> map.put(person.getId(), person));
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.where(cb.equal(root.get("login"), login));

        List<Person> results = em.createQuery(cq).getResultList();
        return results.stream().findFirst();
    }

    @Override
    public boolean contains(UUID key, String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.where(cb.or(
                cb.equal(root.get("id"), key),
                cb.equal(root.get("login"), login)
        ));

        return !em.createQuery(cq).getResultList().isEmpty();
    }
}
