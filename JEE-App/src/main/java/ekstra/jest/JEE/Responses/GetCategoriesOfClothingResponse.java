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
public class GetCategoriesOfClothingResponse {
    public static class CategoryOfClothingDTO{
        public String id;
        public String name;
        public String whereWearClothing;
        public Boolean isTrendy;
    }
    public List<CategoryOfClothingDTO> categories;
}
