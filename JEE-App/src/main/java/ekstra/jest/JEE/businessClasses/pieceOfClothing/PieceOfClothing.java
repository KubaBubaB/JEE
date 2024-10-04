package ekstra.jest.JEE.businessClasses.pieceOfClothing;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.typeOfClothing.TypeOfClothing;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PieceOfClothing {
    private Person owner;
    private Double resellPrice;
    private ClothingSize size;
    private String name;
    private TypeOfClothing typeOfClothing;
    public enum ClothingSize{
        XS,
        S,
        M,
        L,
        XL
    }

}
