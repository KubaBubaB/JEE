package ekstra.jest.JEE.businessClasses.pieceOfClothing;

import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "pieces_of_clothing")
public class PieceOfClothing implements Serializable {
    @Id
    private UUID id;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Person owner;
    private Double resellPrice;
    private ClothingSize size;
    private String name;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryOfClothing categoryOfClothing;
    public enum ClothingSize{
        XS,
        S,
        M,
        L,
        XL
    }

}
