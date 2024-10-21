package ekstra.jest.JEE.model.categoryOfClothing;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CategoriesOfClothingModel {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Category {
        private UUID id;
        private String name;
        private Boolean isTrendy; // Moze tu string?
        private String whereWearClothing;
    }

    @Singular
    private List<Category> categories;
}
