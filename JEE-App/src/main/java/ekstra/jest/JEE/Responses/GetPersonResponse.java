package ekstra.jest.JEE.Responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPersonResponse {
    private String firstName;
    private Double moneyInBankAcc;
    private String lastName;
    private String dateOfBirth;
    private String ownedClothing;
}
