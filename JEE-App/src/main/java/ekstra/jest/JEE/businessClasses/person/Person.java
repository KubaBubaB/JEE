package ekstra.jest.JEE.businessClasses.person;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    @EqualsAndHashCode.Exclude
    private List<PieceOfClothing> ownedClothing;
}
