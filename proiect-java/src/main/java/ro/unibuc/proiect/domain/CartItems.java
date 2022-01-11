package ro.unibuc.proiect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cart_items")
    private Long idCartItems;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName = "id_product")
    @JsonIgnoreProperties(value = {"cartItems", "idCategory", "idGrower"}, allowSetters = true)
    private Product product_id;

    @ManyToOne
    @JoinColumn(name="cart_order_details_id",referencedColumnName = "id_cart_order_details")
    @JsonIgnoreProperties(value = {"cartItems", "idAppUser"}, allowSetters = true)
    private CartOrderDetails idOrderDetails;
}
