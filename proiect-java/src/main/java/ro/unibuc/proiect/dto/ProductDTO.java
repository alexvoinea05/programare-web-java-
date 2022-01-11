package ro.unibuc.proiect.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {

    private Long idProduct;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal price;

    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal stock;

    private CategoryDTO idCategory;

    private AppUserDTO idGrower;

}
