package ro.unibuc.proiect.dto;

import lombok.*;
import ro.unibuc.proiect.domain.Authority;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppUserDTO {
    private Long idAppUser;

    @NotNull
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Authority> authorities;

}
