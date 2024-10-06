package ekstra.jest.JEE.Responses;

import java.util.List;
import java.util.UUID;

public class GetPersonsResponse {
    public static class PersonDTO {
        public UUID id;
        public String firstName;
        public String lastName;
        public String dateOfBirth;
        public Double moneyInBankAcc;
    }

    public List<PersonDTO> persons;
}
