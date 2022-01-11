package ro.unibuc.proiect.dto;

import lombok.*;
import ro.unibuc.proiect.domain.CartItems;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartOrderDetailsDTO {

    private Long idCartOrderDetails;

    @DecimalMin(value = "0.1")
    private BigDecimal totalPrice;

    private String statusCommand;

    private AppUserDTO idAppUser;

    private Set<CartItems> cartItems = new HashSet<>();
}
