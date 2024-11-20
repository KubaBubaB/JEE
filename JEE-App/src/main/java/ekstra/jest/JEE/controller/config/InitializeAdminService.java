package ekstra.jest.JEE.controller.config;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.person.PersonRoles;
import ekstra.jest.JEE.interfaces.PersonRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final PersonRepository personRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            PersonRepository personRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.personRepository = personRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (personRepository.getByLogin("admin-service").isEmpty()) {

            Person admin = Person.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .firstName("Admin")
                    .lastName("Service")
                    .dateOfBirth(new Date(0L))
                    .moneyInBankAcc(0.0)
                    .password(passwordHash.generate("admin".toCharArray()))
                    .ownedClothing(new ArrayList<>())
                    .roles(List.of(PersonRoles.ADMIN, PersonRoles.USER))
                    .build();

            personRepository.save(admin.getId(), admin);
        }
    }

}
