package ekstra.jest.JEE.Requests;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPersonRequest {
    private String firstName;
    private Double moneyInBankAcc;
    private String lastName;
    private String dateOfBirth;
}
