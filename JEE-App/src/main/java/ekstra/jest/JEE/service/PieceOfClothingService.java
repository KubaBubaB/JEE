package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.exceptions.NotFoundException;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import ekstra.jest.JEE.interfaces.PersonRepository;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PieceOfClothingService {
    private final PieceOfClothingRepository pieceOfClothingRepository;
    private final PersonRepository personRepository;
    private final CategoryOfClothingRepository categoryOfClothingRepository;
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

    public void assignPieceOfClothingToPerson(UUID pieceOfClothingId, UUID personId) {
        PieceOfClothing pieceOfClothing = pieceOfClothingRepository.get(pieceOfClothingId).orElseThrow(() -> new NotFoundException("Piece of clothing not found"));
        Person person = personRepository.get(personId).orElseThrow(()-> new NotFoundException("Person not found"));
        pieceOfClothing.setOwner(person);
        person.getOwnedClothing().add(pieceOfClothing);
        pieceOfClothingRepository.update(pieceOfClothingId, pieceOfClothing);
        personRepository.update(personId, person);
    }

    public void assignPieceOfClothingToCategory(UUID pieceOfClothingId, UUID categoryOfClothingId) {
        PieceOfClothing pieceOfClothing = pieceOfClothingRepository.get(pieceOfClothingId).orElseThrow(() -> new NotFoundException("Piece of clothing not found"));
        CategoryOfClothing categoryOfClothing = categoryOfClothingRepository.get(categoryOfClothingId).orElseThrow(()-> new NotFoundException("Category not found"));
        pieceOfClothing.setCategoryOfClothing(categoryOfClothing);
        categoryOfClothing.getClothingBelongingToType().add(pieceOfClothing);
        pieceOfClothingRepository.update(pieceOfClothingId, pieceOfClothing);
        categoryOfClothingRepository.update(categoryOfClothingId, categoryOfClothing);
    }

    public void updatePieceOfClothing(PieceOfClothing pieceOfClothing, UpdatePieceOfClothingRequest request){
        pieceOfClothing.setResellPrice(request.getResellPrice());
        pieceOfClothingRepository.update(pieceOfClothing.getId(), pieceOfClothing);
    }
}
