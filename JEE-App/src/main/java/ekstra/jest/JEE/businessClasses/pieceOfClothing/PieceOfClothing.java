package ekstra.jest.JEE.businessClasses.pieceOfClothing;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.typeOfClothing.TypeOfClothing;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PieceOfClothing {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person owner;
    private Double resellPrice;
    private ClothingSize size;
    private String name;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private TypeOfClothing typeOfClothing;
    public enum ClothingSize{
        XS,
        S,
        M,
        L,
        XL
    }

}
