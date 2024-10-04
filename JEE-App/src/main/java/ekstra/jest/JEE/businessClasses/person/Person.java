package ekstra.jest.JEE.businessClasses.person;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Person {
    private String firstName;
    private Double moneyInBankAcc;
    private String lastName;
    private Date dateOfBirth;
}
