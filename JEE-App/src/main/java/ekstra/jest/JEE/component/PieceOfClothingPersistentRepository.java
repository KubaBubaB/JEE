package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class PieceOfClothingPersistentRepository implements PieceOfClothingRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<PieceOfClothing> get(UUID key) {
        return Optional.ofNullable(em.find(PieceOfClothing.class, key));
    }

    @Override
    public HashMap<UUID, PieceOfClothing> getAll() {
        HashMap<UUID, PieceOfClothing> map = new HashMap<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PieceOfClothing> query = cb.createQuery(PieceOfClothing.class);
        Root<PieceOfClothing> root = query.from(PieceOfClothing.class);
        query.select(root);
        em.createQuery(query).getResultList().forEach(pieceOfClothing -> map.put(pieceOfClothing.getId(), pieceOfClothing));
        return map;
    }

    @Override
    public void save(UUID key, PieceOfClothing value) {
        em.persist(value);
        em.refresh(em.find(CategoryOfClothing.class, value.getCategoryOfClothing().getId()));
        em.refresh(em.find(Person.class, value.getOwner().getId()));
    }

    @Override
    public void remove(UUID key) {
        em.refresh(em.find(PieceOfClothing.class, key));
        em.remove(em.find(PieceOfClothing.class, key));
    }

    @Override
    public void update(UUID key, PieceOfClothing value) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(value);
    }

    @Override
    public HashMap<UUID, PieceOfClothing> getAllByPerson(Person person) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PieceOfClothing> cq = cb.createQuery(PieceOfClothing.class);
        Root<PieceOfClothing> root = cq.from(PieceOfClothing.class);
        cq.where(cb.equal(root.get("owner"), person));

        HashMap<UUID, PieceOfClothing> map = new HashMap<>();
        em.createQuery(cq)
                .getResultList()
                .forEach(pieceOfClothing -> map.put(pieceOfClothing.getId(), pieceOfClothing));
        return map;
    }


    @Override
    public Optional<PieceOfClothing> getByPersonAndId(Person person, UUID key) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<PieceOfClothing> cq = cb.createQuery(PieceOfClothing.class);
            Root<PieceOfClothing> root = cq.from(PieceOfClothing.class);
            cq.where(
                    cb.and(
                            cb.equal(root.get("id"), key),
                            cb.equal(root.get("owner").get("id"), person.getId())
                    )
            );

            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
