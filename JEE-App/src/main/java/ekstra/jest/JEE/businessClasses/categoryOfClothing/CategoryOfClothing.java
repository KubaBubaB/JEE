package ekstra.jest.JEE.businessClasses.categoryOfClothing;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "categories_of_clothing")
public class CategoryOfClothing implements Serializable {
    @Id
    private UUID id;
    private WhereWearClothing whereWearClothing;
    private String name;
    private Boolean isTrendy;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "categoryOfClothing", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
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
