package ekstra.jest.JEE.Requests;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPieceOfClothingRequest {
    private Double resellPrice;
    private String name;
    private String size;
}
