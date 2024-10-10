package ekstra.jest.JEE.businessClasses.person;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import lombok.*;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Person {
    private UUID id;
    private String firstName;
    private Double moneyInBankAcc;
    private String lastName;
    private Date dateOfBirth;
    @EqualsAndHashCode.Exclude
    private List<PieceOfClothing> ownedClothing;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Path photo;
}
