package ekstra.jest.JEE.businessClasses.pieceOfClothing;

import ekstra.jest.JEE.businessClasses.person.Person;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class PieceOfClothing {
    private UUID id;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person owner;
    private Double resellPrice;
    private ClothingSize size;
    private String name;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CategoryOfClothing categoryOfClothing;
    public enum ClothingSize{
        XS,
        S,
        M,
        L,
        XL
    }

}
