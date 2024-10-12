package ekstra.jest.JEE.Requests;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateCategoryOfClothingRequest {
    private Boolean isTrendy;
}
