package ekstra.jest.JEE.component;


import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import lombok.Getter;

import java.util.*;

public class PseudoDatabase {

    private static PseudoDatabase instance = null;

    private PseudoDatabase() {
        // Initialize the database with some data
        Person person1 = new Person(UUID.randomUUID(), "Wlodzimierz", 100000.0, "Bialy", new Date(24062880000L), new ArrayList<>(), null);
        Person person2 = new Person(UUID.randomUUID(), "Jeremiasz", 10000.0, "Rozowy-Czlowiek", new Date(812981280000L), new ArrayList<>(), null);
        Person person3 = new Person(UUID.randomUUID(), "Anna", 5000.0, "Kowalska", new Date(915148800000L), new ArrayList<>(), null);
        Person person4 = new Person(UUID.randomUUID(), "Piotr", 2000.0, "Nowak", new Date(631152000000L), new ArrayList<>(), null);

        CategoryOfClothing categoryOfClothing1 = new CategoryOfClothing(UUID.randomUUID(), CategoryOfClothing.WhereWearClothing.TORSO, "T-shirt", false, new ArrayList<>());
        CategoryOfClothing categoryOfClothing2 = new CategoryOfClothing(UUID.randomUUID(), CategoryOfClothing.WhereWearClothing.LEGS, "Jeans", true, new ArrayList<>());
        CategoryOfClothing categoryOfClothing3 = new CategoryOfClothing(UUID.randomUUID(), CategoryOfClothing.WhereWearClothing.FEET, "Sneakers", true, new ArrayList<>());

        PieceOfClothing pieceOfClothing1 = new PieceOfClothing(UUID.randomUUID(), person1, 100.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Fajny", categoryOfClothing1);
        PieceOfClothing pieceOfClothing2 = new PieceOfClothing(UUID.randomUUID(), person2, 1000.0, PieceOfClothing.ClothingSize.L, "Jeans Typu Fajny", categoryOfClothing2);
        PieceOfClothing pieceOfClothing3 = new PieceOfClothing(UUID.randomUUID(), person1, 50.0, PieceOfClothing.ClothingSize.M, "T-shirt Taki Sb", categoryOfClothing1);
        PieceOfClothing pieceOfClothing4 = new PieceOfClothing(UUID.randomUUID(), person3, 200.0, PieceOfClothing.ClothingSize.S, "Sneakers Typu Sport", categoryOfClothing3);
        PieceOfClothing pieceOfClothing5 = new PieceOfClothing(UUID.randomUUID(), person4, 150.0, PieceOfClothing.ClothingSize.XL, "Jeans Typu Casual", categoryOfClothing2);
        PieceOfClothing pieceOfClothing6 = new PieceOfClothing(UUID.randomUUID(), person3, 80.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Casual", categoryOfClothing1);
        PieceOfClothing pieceOfClothing7 = new PieceOfClothing(UUID.randomUUID(), person4, 120.0, PieceOfClothing.ClothingSize.L, "Sneakers Typu Casual", categoryOfClothing3);
        PieceOfClothing pieceOfClothing8 = new PieceOfClothing(UUID.randomUUID(), person1, 60.0, PieceOfClothing.ClothingSize.S, "T-shirt Typu Basic", categoryOfClothing1);

        categoryOfClothing1.getClothingBelongingToType().add(pieceOfClothing1);
        categoryOfClothing2.getClothingBelongingToType().add(pieceOfClothing2);
        categoryOfClothing1.getClothingBelongingToType().add(pieceOfClothing3);
        categoryOfClothing3.getClothingBelongingToType().add(pieceOfClothing4);
        categoryOfClothing2.getClothingBelongingToType().add(pieceOfClothing5);
        categoryOfClothing1.getClothingBelongingToType().add(pieceOfClothing6);
        categoryOfClothing3.getClothingBelongingToType().add(pieceOfClothing7);
        categoryOfClothing1.getClothingBelongingToType().add(pieceOfClothing8);

        person1.getOwnedClothing().add(pieceOfClothing1);
        person1.getOwnedClothing().add(pieceOfClothing3);
        person1.getOwnedClothing().add(pieceOfClothing8);
        person2.getOwnedClothing().add(pieceOfClothing2);
        person3.getOwnedClothing().add(pieceOfClothing4);
        person3.getOwnedClothing().add(pieceOfClothing6);
        person4.getOwnedClothing().add(pieceOfClothing5);
        person4.getOwnedClothing().add(pieceOfClothing7);

        personMap.put(person1.getId(), person1);
        personMap.put(person2.getId(), person2);
        personMap.put(person3.getId(), person3);
        personMap.put(person4.getId(), person4);

        categoryOfClothingMap.put(categoryOfClothing1.getId(), categoryOfClothing1);
        categoryOfClothingMap.put(categoryOfClothing2.getId(), categoryOfClothing2);
        categoryOfClothingMap.put(categoryOfClothing3.getId(), categoryOfClothing3);

        pieceOfClothingMap.put(pieceOfClothing1.getId(), pieceOfClothing1);
        pieceOfClothingMap.put(pieceOfClothing2.getId(), pieceOfClothing2);
        pieceOfClothingMap.put(pieceOfClothing3.getId(), pieceOfClothing3);
        pieceOfClothingMap.put(pieceOfClothing4.getId(), pieceOfClothing4);
        pieceOfClothingMap.put(pieceOfClothing5.getId(), pieceOfClothing5);
        pieceOfClothingMap.put(pieceOfClothing6.getId(), pieceOfClothing6);
        pieceOfClothingMap.put(pieceOfClothing7.getId(), pieceOfClothing7);
        pieceOfClothingMap.put(pieceOfClothing8.getId(), pieceOfClothing8);
    }

    public static PseudoDatabase getInstance() {
        if (instance == null) {
            instance = new PseudoDatabase();
        }
        return instance;
    }
    @Getter
    private final HashMap<UUID, Person> personMap = new HashMap<>();

    @Getter
    private final HashMap<UUID, PieceOfClothing> pieceOfClothingMap = new HashMap<>();

    @Getter
    private final HashMap<UUID, CategoryOfClothing> categoryOfClothingMap = new HashMap<>();
}
