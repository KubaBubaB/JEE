package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class CategoryOfClothingPersistentRepository implements CategoryOfClothingRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<CategoryOfClothing> get(UUID key) {
        return Optional.ofNullable(em.find(CategoryOfClothing.class, key));
    }

    @Override
    public HashMap<UUID, CategoryOfClothing> getAll() {
        HashMap<UUID, CategoryOfClothing> map = new HashMap<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryOfClothing> query = cb.createQuery(CategoryOfClothing.class);
        Root<CategoryOfClothing> root = query.from(CategoryOfClothing.class);
        query.select(root);
        em.createQuery(query).getResultList().forEach(categoryOfClothing -> map.put(categoryOfClothing.getId(), categoryOfClothing));
        return map;
    }

    @Override
    public void save(UUID key, CategoryOfClothing value) {
        //em.refresh(value);
        em.persist(value);
    }

    @Override
    public void remove(UUID key) {
        em.refresh(em.find(CategoryOfClothing.class, key));
        em.remove(em.find(CategoryOfClothing.class, key));
    }

    @Override
    public void update(UUID key, CategoryOfClothing value) {
        em.merge(value);
    }
}
