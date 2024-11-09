package ekstra.jest.JEE.controller.config;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import ekstra.jest.JEE.service.PersonService;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class InitData {
    private final PieceOfClothingService pieceOfClothingService;
    private final CategoryOfClothingService categoryOfClothingService;
    private final PersonService personService;

    private final RequestContextController requestContextController;


    @Inject
    public InitData(PieceOfClothingService pieceOfClothingService, CategoryOfClothingService categoryOfClothingService, PersonService personService, RequestContextController requestContextController) {
        this.pieceOfClothingService = pieceOfClothingService;
        this.categoryOfClothingService = categoryOfClothingService;
        this.personService = personService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private void init(){
        requestContextController.activate();

        Person person1 = new Person(UUID.randomUUID(), "Wlodzimierz", 100000.0, "Bialy", new Date(24062880000L), new ArrayList<>(), null);
        Person person2 = new Person(UUID.randomUUID(), "Jeremiasz", 10000.0, "Rozowy-Czlowiek", new Date(812981280000L), new ArrayList<>(), null);
        Person person3 = new Person(UUID.randomUUID(), "Anna", 5000.0, "Kowalska", new Date(915148800000L), new ArrayList<>(), null);
        Person person4 = new Person(UUID.randomUUID(), "Piotr", 2000.0, "Nowak", new Date(631152000000L), new ArrayList<>(), null);
        if(personService.getAllPersons().isEmpty()){
            personService.savePerson(person1.getId(), person1);
            personService.savePerson(person2.getId(), person2);
            personService.savePerson(person3.getId(), person3);
            personService.savePerson(person4.getId(), person4);
        }


        CategoryOfClothing categoryOfClothing1 = new CategoryOfClothing(UUID.randomUUID(), CategoryOfClothing.WhereWearClothing.TORSO, "T-shirt", false, new ArrayList<>());
        CategoryOfClothing categoryOfClothing2 = new CategoryOfClothing(UUID.randomUUID(), CategoryOfClothing.WhereWearClothing.LEGS, "Jeans", true, new ArrayList<>());
        CategoryOfClothing categoryOfClothing3 = new CategoryOfClothing(UUID.randomUUID(), CategoryOfClothing.WhereWearClothing.FEET, "Sneakers", true, new ArrayList<>());

        if(categoryOfClothingService.getAllCategoryOfClothing().isEmpty()){
            categoryOfClothingService.saveCategoryOfClothing(categoryOfClothing1.getId(), categoryOfClothing1);
            categoryOfClothingService.saveCategoryOfClothing(categoryOfClothing2.getId(), categoryOfClothing2);
            categoryOfClothingService.saveCategoryOfClothing(categoryOfClothing3.getId(), categoryOfClothing3);
        }

        PieceOfClothing pieceOfClothing1 = new PieceOfClothing(UUID.randomUUID(), person1, 100.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Fajny", categoryOfClothing1);
        PieceOfClothing pieceOfClothing2 = new PieceOfClothing(UUID.randomUUID(), person2, 1000.0, PieceOfClothing.ClothingSize.L, "Jeans Typu Fajny", categoryOfClothing2);
        PieceOfClothing pieceOfClothing3 = new PieceOfClothing(UUID.randomUUID(), person1, 50.0, PieceOfClothing.ClothingSize.M, "T-shirt Taki Sb", categoryOfClothing1);
        PieceOfClothing pieceOfClothing4 = new PieceOfClothing(UUID.randomUUID(), person3, 200.0, PieceOfClothing.ClothingSize.S, "Sneakers Typu Sport", categoryOfClothing3);
        PieceOfClothing pieceOfClothing5 = new PieceOfClothing(UUID.randomUUID(), person4, 150.0, PieceOfClothing.ClothingSize.XL, "Jeans Typu Casual", categoryOfClothing2);
        PieceOfClothing pieceOfClothing6 = new PieceOfClothing(UUID.randomUUID(), person3, 80.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Casual", categoryOfClothing1);
        PieceOfClothing pieceOfClothing7 = new PieceOfClothing(UUID.randomUUID(), person4, 120.0, PieceOfClothing.ClothingSize.L, "Sneakers Typu Casual", categoryOfClothing3);
        PieceOfClothing pieceOfClothing8 = new PieceOfClothing(UUID.randomUUID(), person1, 60.0, PieceOfClothing.ClothingSize.S, "T-shirt Typu Basic", categoryOfClothing1);

        if(pieceOfClothingService.getAllPieceOfClothing().isEmpty()){
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing1.getId(), pieceOfClothing1);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing2.getId(), pieceOfClothing2);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing3.getId(), pieceOfClothing3);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing4.getId(), pieceOfClothing4);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing5.getId(), pieceOfClothing5);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing6.getId(), pieceOfClothing6);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing7.getId(), pieceOfClothing7);
            pieceOfClothingService.savePieceOfClothing(pieceOfClothing8.getId(), pieceOfClothing8);
        }
        requestContextController.deactivate();
    }
}
