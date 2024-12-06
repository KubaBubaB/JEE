package ekstra.jest.JEE.model.pieceOfClothing;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PieceOfClothingEditModel {
    private String name;
    private double resellPrice;

    private Long version;
}
