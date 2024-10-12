package ekstra.jest.JEE.Responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPiecesOfClothingResponse {
    public static class PieceOfClothingDTO {
        public String id;
        public String owner;
        public Double resellPrice;
        public String size;
        public String name;
        public String categoryOfClothing;
    }

    public List<PieceOfClothingDTO> piecesOfClothing;
}
