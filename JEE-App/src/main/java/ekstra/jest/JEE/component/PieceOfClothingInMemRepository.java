package ekstra.jest.JEE.component;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PieceOfClothingInMemRepository implements PieceOfClothingRepository {

    private final PseudoDatabase pseudoDatabase;

    public PieceOfClothingInMemRepository(PseudoDatabase pseudoDatabase) {
        this.pseudoDatabase = pseudoDatabase;
    }

    @Override
    public Optional<PieceOfClothing> get(UUID key) {
        return Optional.ofNullable(pseudoDatabase.getPieceOfClothingMap().get(key));
    }

    @Override
    public HashMap<UUID, PieceOfClothing> getAll() {
        return pseudoDatabase.getPieceOfClothingMap();
    }

    @Override
    public void save(UUID key, PieceOfClothing value) {
        pseudoDatabase.getPieceOfClothingMap().put(key, value);
    }

    @Override
    public void remove(UUID key) {
        pseudoDatabase.getPieceOfClothingMap().remove(key);
    }

    @Override
    public void update(UUID key, PieceOfClothing value) {
        pseudoDatabase.getPieceOfClothingMap().put(key, value);
    }
}
