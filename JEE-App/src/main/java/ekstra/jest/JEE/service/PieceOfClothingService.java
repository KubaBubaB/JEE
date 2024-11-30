package ekstra.jest.JEE.service;

import ekstra.jest.JEE.Requests.UpdatePieceOfClothingRequest;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import ekstra.jest.JEE.interfaces.CategoryOfClothingRepository;
import ekstra.jest.JEE.interfaces.PersonRepository;
import ekstra.jest.JEE.interfaces.PieceOfClothingRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class PieceOfClothingService {
    private final PieceOfClothingRepository pieceOfClothingRepository;
    private final PersonRepository personRepository;
    private final CategoryOfClothingRepository categoryOfClothingRepository;
    private final SecurityContext securityContext;


    @Inject
    public PieceOfClothingService(PieceOfClothingRepository pieceOfClothingRepository,
                                  PersonRepository personRepository,
                                  CategoryOfClothingRepository categoryOfClothingRepository,
                                  @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.pieceOfClothingRepository = pieceOfClothingRepository;
        this.personRepository = personRepository;
        this.categoryOfClothingRepository = categoryOfClothingRepository;
        this.securityContext = securityContext;
    }

    //@RolesAllowed(PersonRoles.USER)
    public HashMap<UUID, PieceOfClothing> getAllPieceOfClothingInCategory(CategoryOfClothing category) {
        checkUserRole();
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName()).get();
            var response1 = pieceOfClothingRepository.getAllByPerson(person);
            var toret1 = new HashMap<UUID, PieceOfClothing>();
            response1.forEach((key, value) -> {
                if(value.getCategoryOfClothing().getId().equals(category.getId())){
                    toret1.put(key, value);
                }
            });
            return toret1;
        }
        var response = getAllPieceOfClothing();
        var toret = new HashMap<UUID, PieceOfClothing>();
        response.forEach((key, value) -> {
            if(value.getCategoryOfClothing().getId().equals(category.getId())){
                toret.put(key, value);
            }
        });
        return toret;
    }


    //@RolesAllowed(PersonRoles.USER)
    public void savePieceOfClothing(UUID key, PieceOfClothing value){
        checkUserRole();
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName()).get();
            value.setOwner(person);
        }
        pieceOfClothingRepository.save(key, value);
    }

    public void saveWithExtraSteps(UUID key, PieceOfClothing value){
        pieceOfClothingRepository.save(key, value);
        personRepository.get(value.getOwner().getId()).ifPresent(person -> {
            System.out.println(person.toString());
            person.getOwnedClothing().add(value);
            personRepository.update(person.getId(), person);
        });

        categoryOfClothingRepository.get(value.getCategoryOfClothing().getId()).ifPresent(categoryOfClothing -> {
            System.out.println(categoryOfClothing.toString());
            categoryOfClothing.getClothingBelongingToType().add(value);
            categoryOfClothingRepository.update(categoryOfClothing.getId(), categoryOfClothing);
        });
    }

    //@RolesAllowed(PersonRoles.USER)
    public Optional<PieceOfClothing> getPieceOfClothing(UUID key){
        checkUserRole();
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName()).get();
            return Optional.ofNullable(pieceOfClothingRepository.getAllByPerson(person).get(key));
        }
        return pieceOfClothingRepository.get(key);
    }

    //@RolesAllowed(PersonRoles.USER)
    public HashMap<UUID, PieceOfClothing> getAllPieceOfClothing() {
        checkUserRole();
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName()).get();
            return pieceOfClothingRepository.getAllByPerson(person);
        }
        return pieceOfClothingRepository.getAll();
    }

    public HashMap<UUID, PieceOfClothing> getAllPieceOfClothingByCallerPrincipal(){
        checkUserRole();
        if (securityContext.isCallerInRole(PersonRoles.ADMIN)) {
            return pieceOfClothingRepository.getAll();
        }
        Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return pieceOfClothingRepository.getAllByPerson(person);
    }

    public Optional<PieceOfClothing> getPieceOfClothingByCallerPrincipal(UUID key){
        checkUserRole();
        if (securityContext.isCallerInRole(PersonRoles.ADMIN)) {
            return pieceOfClothingRepository.get(key);
        }
        Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return pieceOfClothingRepository.getByPersonAndId(person, key);
    }

    //@RolesAllowed(PersonRoles.USER)
    public void removePieceOfClothing(UUID key) {
        // Not needed with JPA
        //Optional<Person> owner = personRepository.getAll().values().stream()
        //        .filter(person -> person.getOwnedClothing().stream()
        //                .anyMatch(pieceOfClothing -> pieceOfClothing.getId().equals(key)))
        //        .findFirst();
        //Optional<CategoryOfClothing> category = categoryOfClothingRepository.getAll().values().stream()
        //        .filter(categoryOfClothing -> categoryOfClothing.getClothingBelongingToType().stream()
        //                .anyMatch(pieceOfClothing -> pieceOfClothing.getId().equals(key)))
        //        .findFirst();
        //
        //owner.ifPresent(person ->
        //{
        //    person.getOwnedClothing().removeIf(pieceOfClothing -> pieceOfClothing.getId().equals(key));
        //    personRepository.update(person.getId(), person);
        //});
        //category.ifPresent(categoryOfClothing ->
        //{
        //    categoryOfClothing.getClothingBelongingToType().removeIf(pieceOfClothing -> pieceOfClothing.getId().equals(key));
        //    categoryOfClothingRepository.update(categoryOfClothing.getId(), categoryOfClothing);
        //});
        checkUserRole();
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName()).get();
            if(!person.getOwnedClothing().contains(pieceOfClothingRepository.get(key).get())){
                return;
            }
        }
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

    //@RolesAllowed(PersonRoles.USER)
    public void updatePieceOfClothing(PieceOfClothing pieceOfClothing, UpdatePieceOfClothingRequest request){
        checkUserRole();
        if(!securityContext.isCallerInRole(PersonRoles.ADMIN)){
            Person person = personRepository.getByLogin(securityContext.getCallerPrincipal().getName()).get();
            if(!person.getOwnedClothing().contains(pieceOfClothing)){
                return;
            }
        }
        pieceOfClothing.setResellPrice(request.getResellPrice());
        pieceOfClothingRepository.update(pieceOfClothing.getId(), pieceOfClothing);
    }

    private void checkUserRole() throws SecurityException {
        if (!securityContext.isCallerInRole(PersonRoles.USER)) {
            throw new SecurityException("Caller not authorized.");
        }
    }

    private void checkAdminRoleOrOwner(Optional<PieceOfClothing> piece) throws SecurityException {
        if (securityContext.isCallerInRole(PersonRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(PersonRoles.USER)
                && piece.isPresent()
                && piece.get().getOwner().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new SecurityException("Caller not authorized.");
    }
}
