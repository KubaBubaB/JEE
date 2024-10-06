package ekstra.jest.JEE.component;


import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.businessClasses.typeOfClothing.TypeOfClothing;
import lombok.Getter;

import java.util.*;

public class PseudoDatabase {

    private static PseudoDatabase instance = null;

    private PseudoDatabase() {
        // Initialize the database with some data
        var person1 = new Person("Wlodzimierz", 100000.0, "Bialy", new Date(24062880000L), new ArrayList<>(), null);
        var person2 = new Person("Jeremiasz", 10000.0, "Rozowy-Czlowiek", new Date(812981280000L), new ArrayList<>(), null);
        var person3 = new Person("Anna", 5000.0, "Kowalska", new Date(915148800000L), new ArrayList<>(), null);
        var person4 = new Person("Piotr", 2000.0, "Nowak", new Date(631152000000L), new ArrayList<>(), null);

        var typeOfClothing1 = new TypeOfClothing(TypeOfClothing.WhereWearClothing.TORSO, "T-shirt", false, new ArrayList<>());
        var typeOfClothing2 = new TypeOfClothing(TypeOfClothing.WhereWearClothing.LEGS, "Jeans", true, new ArrayList<>());
        var typeOfClothing3 = new TypeOfClothing(TypeOfClothing.WhereWearClothing.FEET, "Sneakers", true, new ArrayList<>());

        var pieceOfClothing1 = new PieceOfClothing(person1, 100.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Fajny", typeOfClothing1);
        var pieceOfClothing2 = new PieceOfClothing(person2, 1000.0, PieceOfClothing.ClothingSize.L, "Jeans Typu Fajny", typeOfClothing2);
        var pieceOfClothing3 = new PieceOfClothing(person1, 50.0, PieceOfClothing.ClothingSize.M, "T-shirt Taki Sb", typeOfClothing1);
        var pieceOfClothing4 = new PieceOfClothing(person3, 200.0, PieceOfClothing.ClothingSize.S, "Sneakers Typu Sport", typeOfClothing3);
        var pieceOfClothing5 = new PieceOfClothing(person4, 150.0, PieceOfClothing.ClothingSize.XL, "Jeans Typu Casual", typeOfClothing2);
        var pieceOfClothing6 = new PieceOfClothing(person3, 80.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Casual", typeOfClothing1);
        var pieceOfClothing7 = new PieceOfClothing(person4, 120.0, PieceOfClothing.ClothingSize.L, "Sneakers Typu Casual", typeOfClothing3);
        var pieceOfClothing8 = new PieceOfClothing(person1, 60.0, PieceOfClothing.ClothingSize.S, "T-shirt Typu Basic", typeOfClothing1);

        typeOfClothing1.getClothingBelongingToType().add(pieceOfClothing1);
        typeOfClothing2.getClothingBelongingToType().add(pieceOfClothing2);
        typeOfClothing1.getClothingBelongingToType().add(pieceOfClothing3);
        typeOfClothing3.getClothingBelongingToType().add(pieceOfClothing4);
        typeOfClothing2.getClothingBelongingToType().add(pieceOfClothing5);
        typeOfClothing1.getClothingBelongingToType().add(pieceOfClothing6);
        typeOfClothing3.getClothingBelongingToType().add(pieceOfClothing7);
        typeOfClothing1.getClothingBelongingToType().add(pieceOfClothing8);

        person1.getOwnedClothing().add(pieceOfClothing1);
        person1.getOwnedClothing().add(pieceOfClothing3);
        person1.getOwnedClothing().add(pieceOfClothing8);
        person2.getOwnedClothing().add(pieceOfClothing2);
        person3.getOwnedClothing().add(pieceOfClothing4);
        person3.getOwnedClothing().add(pieceOfClothing6);
        person4.getOwnedClothing().add(pieceOfClothing5);
        person4.getOwnedClothing().add(pieceOfClothing7);

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();

        personMap.put(UUID.randomUUID(), person1);
        personMap.put(UUID.randomUUID(), person2);
        personMap.put(UUID.randomUUID(), person3);
        personMap.put(UUID.randomUUID(), person4);

        System.out.println("Database initialized with 4 persons with ids: \n" + uuid1 + "\n" + uuid2 + "\n" + uuid3 + "\n" + uuid4);

    }

    public static PseudoDatabase getInstance() {
        if (instance == null) {
            instance = new PseudoDatabase();
        }
        return instance;
    }
    @Getter
    private HashMap<UUID, Person> personMap = new HashMap<>();
}
