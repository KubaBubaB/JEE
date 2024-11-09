package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
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
        em.createQuery("select c from CategoryOfClothing c", CategoryOfClothing.class).getResultList().forEach(categoryOfClothing -> map.put(categoryOfClothing.getId(), categoryOfClothing));
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
