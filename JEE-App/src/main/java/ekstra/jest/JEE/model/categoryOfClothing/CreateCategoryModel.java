package ekstra.jest.JEE.model.categoryOfClothing;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateCategoryModel {
    private String name;
    private String whereWearClothing;
    private String isTrendy;
}
