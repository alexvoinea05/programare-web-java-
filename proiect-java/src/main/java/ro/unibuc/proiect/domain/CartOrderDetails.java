package ro.unibuc.proiect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_order_details")
public class CartOrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cart_order_details")
    private Long idCartOrderDetails;

    @DecimalMin(value = "0.1")
    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "status_command")
    private String statusCommand;

    @OneToMany(mappedBy = "idOrderDetails")
    @JsonIgnoreProperties(value = {"idProduct", "idOrderDetails"}, allowSetters = true)
    private Set<CartItems> cartItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="appuser_id",referencedColumnName = "id_appuser")
    @JsonIgnoreProperties(value = {"user", "cartOrderDetails", "products"}, allowSetters = true)
    private AppUser idAppUser;
}
