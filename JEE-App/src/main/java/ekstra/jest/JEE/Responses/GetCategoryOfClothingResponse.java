package ekstra.jest.JEE.Responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCategoryOfClothingResponse {
    private String whereWearClothing;
    private String name;
    private Boolean isTrendy;
    private String clothingBelongingToType;
}
