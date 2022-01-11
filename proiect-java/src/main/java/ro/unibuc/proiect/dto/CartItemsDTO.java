package ro.unibuc.proiect.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemsDTO {

    private Long idCartItems;

    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal quantity;

    private ProductDTO idProduct;

    private CartOrderDetailsDTO idOrderDetails;
}
