package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import ekstra.jest.JEE.interfaces.PersonRepository;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class PieceOfClothingService {
    private final PieceOfClothingRepository pieceOfClothingRepository;
    private final PersonRepository personRepository;
    private final CategoryOfClothingRepository categoryOfClothingRepository;

    @Inject
    public PieceOfClothingService(PieceOfClothingRepository pieceOfClothingRepository, PersonRepository personRepository, CategoryOfClothingRepository categoryOfClothingRepository) {
        this.pieceOfClothingRepository = pieceOfClothingRepository;
        this.personRepository = personRepository;
        this.categoryOfClothingRepository = categoryOfClothingRepository;
    }

    public void savePieceOfClothing(UUID key, PieceOfClothing value){
        pieceOfClothingRepository.save(key, value);
    }

    public Optional<PieceOfClothing> getPieceOfClothing(UUID key){
        return pieceOfClothingRepository.get(key);
    }

    public HashMap<UUID, PieceOfClothing> getAllPieceOfClothing() {
        return pieceOfClothingRepository.getAll();
    }

    public void removePieceOfClothing(UUID key) {
        Optional<Person> owner = personRepository.getAll().values().stream()
                .filter(person -> person.getOwnedClothing().stream()
                        .anyMatch(pieceOfClothing -> pieceOfClothing.getId().equals(key)))
                .findFirst();
        Optional<CategoryOfClothing> category = categoryOfClothingRepository.getAll().values().stream()
                .filter(categoryOfClothing -> categoryOfClothing.getClothingBelongingToType().stream()
                        .anyMatch(pieceOfClothing -> pieceOfClothing.getId().equals(key)))
                .findFirst();

        owner.ifPresent(person ->
        {
            person.getOwnedClothing().removeIf(pieceOfClothing -> pieceOfClothing.getId().equals(key));
            personRepository.update(person.getId(), person);
        });
        category.ifPresent(categoryOfClothing ->
        {
            categoryOfClothing.getClothingBelongingToType().removeIf(pieceOfClothing -> pieceOfClothing.getId().equals(key));
            categoryOfClothingRepository.update(categoryOfClothing.getId(), categoryOfClothing);
        });

        pieceOfClothingRepository.remove(key);
    }

    public void assignPieceOfClothingToPerson(PieceOfClothing pieceOfClothing, Person person) {
        pieceOfClothing.setOwner(person);
        person.getOwnedClothing().add(pieceOfClothing);
        pieceOfClothingRepository.update(pieceOfClothing.getId(), pieceOfClothing);
        personRepository.update(person.getId(), person);
    }

    public void assignPieceOfClothingToCategory(PieceOfClothing pieceOfClothing, CategoryOfClothing categoryOfClothing) {
        pieceOfClothing.setCategoryOfClothing(categoryOfClothing);
        categoryOfClothing.getClothingBelongingToType().add(pieceOfClothing);
        pieceOfClothingRepository.update(pieceOfClothing.getId(), pieceOfClothing);
        categoryOfClothingRepository.update(categoryOfClothing.getId(), categoryOfClothing);
    }

    public void updatePieceOfClothing(PieceOfClothing pieceOfClothing, UpdatePieceOfClothingRequest request){
        pieceOfClothing.setResellPrice(request.getResellPrice());
        pieceOfClothingRepository.update(pieceOfClothing.getId(), pieceOfClothing);
    }
}
