package ro.unibuc.proiect.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appuser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appuser")
    private Long idAppUser;

    @Size(min = 1, max = 50)
    @Column(name= "username",length = 50, unique = true, nullable = false)
    private String username;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(name="email", length = 100, unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "appuser_authority",
            joinColumns = { @JoinColumn(name = "appuser_id", referencedColumnName = "id_appuser") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    @ToString.Exclude
    private Set<Authority> authorities = new HashSet<>();

    @Size(max = 256)
    @Column(name = "certificate_url", length = 50)
    private String certificateUrl;

    @Column(name = "account_created")
    private LocalDateTime accountCreated;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return idAppUser != null && idAppUser.equals(((AppUser) o).idAppUser);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", certificateUrl='" + certificateUrl + '\'' +
                "}";
    }
}
