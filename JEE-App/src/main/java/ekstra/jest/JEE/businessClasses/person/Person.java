package ekstra.jest.JEE.businessClasses.person;

import ekstra.jest.JEE.businessClasses.pieceOfClothing.PieceOfClothing;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "persons")
public class Person implements Serializable {
    @Id
    private UUID id;

    private String login;

    @ToString.Exclude
    private String password;

    private String firstName;
    private Double moneyInBankAcc;
    private String lastName;
    private Date dateOfBirth;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.ALL})
    private List<PieceOfClothing> ownedClothing;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Transient                  // <--- tmp workaround
    private Path photo;

    @CollectionTable(name = "persons__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
