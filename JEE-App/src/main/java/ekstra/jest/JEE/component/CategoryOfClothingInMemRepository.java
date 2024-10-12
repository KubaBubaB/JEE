package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class CategoryOfClothingInMemRepository implements CategoryOfClothingRepository {
    private final PseudoDatabase pseudoDatabase;

    @Inject
    public CategoryOfClothingInMemRepository(PseudoDatabase pseudoDatabase) {
        this.pseudoDatabase = pseudoDatabase;
    }

    @Override
    public Optional<CategoryOfClothing> get(UUID key) {
        return Optional.ofNullable(pseudoDatabase.getCategoryOfClothingMap().get(key));
    }

    @Override
    public HashMap<UUID, CategoryOfClothing> getAll() {
        return pseudoDatabase.getCategoryOfClothingMap();
    }

    @Override
    public void save(UUID key, CategoryOfClothing value) {
        pseudoDatabase.getCategoryOfClothingMap().put(key, value);
    }

    @Override
    public void remove(UUID key) {
        pseudoDatabase.getCategoryOfClothingMap().remove(key);
    }

    @Override
    public void update(UUID key, CategoryOfClothing value) {
        pseudoDatabase.getCategoryOfClothingMap().put(key, value);
    }
}
