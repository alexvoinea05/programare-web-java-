package ro.unibuc.proiect.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthorityDTO {

    @NotNull
    private String name;
}
