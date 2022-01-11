package ro.unibuc.proiect.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private Long idCategory;

    @NotNull
    @Size(min = 1)
    private String categoryName;
}
