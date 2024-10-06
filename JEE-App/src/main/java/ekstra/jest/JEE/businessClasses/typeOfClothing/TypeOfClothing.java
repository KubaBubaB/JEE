package ekstra.jest.JEE.businessClasses.typeOfClothing;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TypeOfClothing {
    private WhereWearClothing whereWearClothing;
    private String name;
    private Boolean isTrendy;
    @EqualsAndHashCode.Exclude
    private List<PieceOfClothing> clothingBelongingToType;
    public enum WhereWearClothing {
        HEAD,
        NECK,
        TORSO,
        HANDS,
        LEGS,
        FEET
    }

}
