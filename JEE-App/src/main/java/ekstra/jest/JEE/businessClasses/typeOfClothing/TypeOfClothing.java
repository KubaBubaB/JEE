package ekstra.jest.JEE.businessClasses.typeOfClothing;

import lombok.*;

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
    public enum WhereWearClothing {
        HEAD,
        NECK,
        TORSO,
        HANDS,
        LEGS,
        FEET
    }

}
