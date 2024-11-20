package ekstra.jest.JEE.controller.config;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.service.CategoryOfClothingService;
import ekstra.jest.JEE.service.PersonService;
import ekstra.jest.JEE.service.PieceOfClothingService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({PersonRoles.ADMIN, PersonRoles.USER})
@RunAs(PersonRoles.ADMIN)
public class InitData {
    private PieceOfClothingService pieceOfClothingService;
    private CategoryOfClothingService categoryOfClothingService;
    private PersonService personService;

    @EJB
    public void setPieceOfClothingService(PieceOfClothingService pieceOfClothingService) {
        this.pieceOfClothingService = pieceOfClothingService;
    }

    @EJB
    public void setCategoryOfClothingService(CategoryOfClothingService categoryOfClothingService) {
        this.categoryOfClothingService = categoryOfClothingService;
    }

    @EJB
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    @SneakyThrows
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void init(){

        Person admin = new Person(UUID.fromString("00000000-0000-0000-0000-000000000000"),"admin","admin", "Admin", 0.0, "Admin", new Date(0L), new ArrayList<>(), null, List.of(PersonRoles.ADMIN, PersonRoles.USER));
        Person person1 = new Person(UUID.fromString("00000000-0000-0000-0000-000000000001"),"Wlodek123","silnehaslo", "Wlodzimierz", 100000.0, "Bialy", new Date(24062880000L), new ArrayList<>(), null, List.of(PersonRoles.USER));
        Person person2 = new Person(UUID.fromString("00000000-0000-0000-0000-000000000002"),"Jerry2147","silnehaslo", "Jeremiasz", 10000.0, "Rozowy-Czlowiek", new Date(812981280000L), new ArrayList<>(), null, List.of(PersonRoles.USER));
        Person person3 = new Person(UUID.fromString("00000000-0000-0000-0000-000000000003"),"AnkaFiranka","silnehaslo", "Anna", 5000.0, "Kowalska", new Date(915148800000L), new ArrayList<>(), null, List.of(PersonRoles.USER));
        Person person4 = new Person(UUID.fromString("00000000-0000-0000-0000-000000000004"),"Piotrek","silnehaslo", "Piotr", 2000.0, "Nowak", new Date(631152000000L), new ArrayList<>(), null, List.of(PersonRoles.USER));
        if(personService.getPerson(person1.getId()).isEmpty()){
            personService.savePerson(person1.getId(), person1);
            personService.savePerson(person2.getId(), person2);
            personService.savePerson(person3.getId(), person3);
            personService.savePerson(person4.getId(), person4);
        }
        if(personService.getPerson(admin.getId()).isEmpty()){
            personService.savePerson(admin.getId(), admin);
        }

        CategoryOfClothing categoryOfClothing1 = new CategoryOfClothing(UUID.fromString("00000000-0000-0000-0000-000000000005"), CategoryOfClothing.WhereWearClothing.TORSO, "T-shirt", false, new ArrayList<>());
        CategoryOfClothing categoryOfClothing2 = new CategoryOfClothing(UUID.fromString("00000000-0000-0000-0000-000000000006"), CategoryOfClothing.WhereWearClothing.LEGS, "Jeans", true, new ArrayList<>());
        CategoryOfClothing categoryOfClothing3 = new CategoryOfClothing(UUID.fromString("00000000-0000-0000-0000-000000000007"), CategoryOfClothing.WhereWearClothing.FEET, "Sneakers", true, new ArrayList<>());

        if(categoryOfClothingService.getAllCategoryOfClothing().isEmpty()){
            categoryOfClothingService.saveCategoryOfClothing(categoryOfClothing1.getId(), categoryOfClothing1);
            categoryOfClothingService.saveCategoryOfClothing(categoryOfClothing2.getId(), categoryOfClothing2);
            categoryOfClothingService.saveCategoryOfClothing(categoryOfClothing3.getId(), categoryOfClothing3);
        }

        var dupa1 = (categoryOfClothingService.getAllCategoryOfClothing());
        var dupa2 = (personService.getAllPersons());

        PieceOfClothing pieceOfClothing1 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-000000000008"), person1, 100.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Fajny", categoryOfClothing1);
        PieceOfClothing pieceOfClothing2 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-000000000009"), person2, 1000.0, PieceOfClothing.ClothingSize.L, "Jeans Typu Fajny", categoryOfClothing2);
        PieceOfClothing pieceOfClothing3 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-00000000000A"), person1, 50.0, PieceOfClothing.ClothingSize.M, "T-shirt Taki Sb", categoryOfClothing1);
        PieceOfClothing pieceOfClothing4 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-00000000000B"), person3, 200.0, PieceOfClothing.ClothingSize.S, "Sneakers Typu Sport", categoryOfClothing3);
        PieceOfClothing pieceOfClothing5 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-00000000000C"), person4, 150.0, PieceOfClothing.ClothingSize.XL, "Jeans Typu Casual", categoryOfClothing2);
        PieceOfClothing pieceOfClothing6 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-00000000000D"), person3, 80.0, PieceOfClothing.ClothingSize.M, "T-shirt Typu Casual", categoryOfClothing1);
        PieceOfClothing pieceOfClothing7 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-00000000000E"), person4, 120.0, PieceOfClothing.ClothingSize.L, "Sneakers Typu Casual", categoryOfClothing3);
        PieceOfClothing pieceOfClothing8 = new PieceOfClothing(UUID.fromString("00000000-0000-0000-0000-00000000000F"), person1, 60.0, PieceOfClothing.ClothingSize.S, "T-shirt Typu Basic", categoryOfClothing1);

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
    }
}
