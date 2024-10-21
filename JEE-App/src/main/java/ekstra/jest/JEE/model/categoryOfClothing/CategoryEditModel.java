package ekstra.jest.JEE.model.categoryOfClothing;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CategoryEditModel {
    private String name;
    private String isTrendy;
}
