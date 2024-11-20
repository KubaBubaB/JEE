package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
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
        em.createQuery("select p from PieceOfClothing p", PieceOfClothing.class).getResultList().forEach(pieceOfClothing -> map.put(pieceOfClothing.getId(), pieceOfClothing));
        return map;
    }

    @Override
    public void save(UUID key, PieceOfClothing value) {
        em.persist(value);
        em.refresh(em.find(CategoryOfClothing.class, value.getCategoryOfClothing().getId()));
    }

    @Override
    public void remove(UUID key) {
        em.refresh(em.find(PieceOfClothing.class, key));
        em.remove(em.find(PieceOfClothing.class, key));
    }

    @Override
    public void update(UUID key, PieceOfClothing value) {
        em.merge(value);
    }
}
