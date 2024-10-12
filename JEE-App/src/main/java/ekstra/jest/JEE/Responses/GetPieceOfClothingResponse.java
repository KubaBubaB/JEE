package ekstra.jest.JEE.Responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPieceOfClothingResponse {
    private String name;
    private String owner;
    private String categoryOfClothing;
    private String resellPrice;
    private String size;
}
