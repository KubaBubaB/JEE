package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public interface CategoryOfClothingRepository extends Repository<UUID, CategoryOfClothing> {
    @Override
    public Optional<CategoryOfClothing> get(UUID key);

    @Override
    public HashMap<UUID, CategoryOfClothing> getAll();

    @Override
    public void save(UUID key, CategoryOfClothing value);
    @Override
    public void remove(UUID key);

    @Override
    public void update(UUID key, CategoryOfClothing value);
}
