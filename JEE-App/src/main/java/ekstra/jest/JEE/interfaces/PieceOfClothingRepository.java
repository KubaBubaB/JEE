package ekstra.jest.JEE.interfaces;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public interface PieceOfClothingRepository extends Repository<UUID, PieceOfClothing> {
    @Override
    public Optional<PieceOfClothing> get(UUID key);

    @Override
    public HashMap<UUID, PieceOfClothing> getAll();

    @Override
    public void save(UUID key, PieceOfClothing value);
    @Override
    public void remove(UUID key);

    @Override
    public void update(UUID key, PieceOfClothing value);
}
