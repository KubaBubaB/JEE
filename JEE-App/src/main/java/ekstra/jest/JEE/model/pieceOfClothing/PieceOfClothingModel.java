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
public class PieceOfClothingModel {
    private UUID id;
    private String name;
    private String ownersName;
    private String size;
    private double resellPrice;
    private String categoryName;
}
