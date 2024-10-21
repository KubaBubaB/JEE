package ekstra.jest.JEE.model.pieceOfClothing;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PieceOfClothingCreateModel {
    private UUID id;
    private String name;
    private double resellPrice;
    private String size;
    private String categoryName;
    private String ownersName;
}
