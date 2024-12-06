package ekstra.jest.JEE.businessClasses.pieceOfClothing;

import ekstra.jest.JEE.businessClasses.VersionAndCreationDateAuditable;
import ekstra.jest.JEE.businessClasses.categoryOfClothing.CategoryOfClothing;
import ekstra.jest.JEE.businessClasses.person.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pieces_of_clothing")
public class PieceOfClothing extends VersionAndCreationDateAuditable implements Serializable {
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

    @Override
    public String toString() {
        return "PieceOfClothing{" +
                "id=" + id +
                ", resellPrice=" + resellPrice +
                ", size=" + size +
                ", name='" + name + '\'' +
                ", version=" + super.getVersion() +
                '}';
    }

}
