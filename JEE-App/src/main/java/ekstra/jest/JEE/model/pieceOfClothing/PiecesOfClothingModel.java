package ekstra.jest.JEE.model.pieceOfClothing;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PiecesOfClothingModel {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class PieceOfClothing {
        private UUID id;
        private String name;
        private String size;
        private double resellPrice;
        private String nameOfOwner;
        private Long version;
        private LocalDateTime creationDateTime;
        private LocalDateTime editionDateTime;
    }

    @Singular
    private List<PieceOfClothing> pieces;
}
