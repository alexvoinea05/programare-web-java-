package ro.unibuc.proiect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_product")
    private Long idProduct;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(name = "stock", precision = 21, scale = 2, nullable = false)
    private BigDecimal stock;

    @OneToMany(mappedBy = "product_id")
    @JsonIgnoreProperties(value = {"idProduct", "idOrderDetails"}, allowSetters = true)
    private Set<CartItems> cartItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="category_id",referencedColumnName = "id_category")
    @JsonIgnoreProperties(value = {"products"}, allowSetters = true)
    private Category category_id;

    @ManyToOne
    @JoinColumn(name="grower_id",referencedColumnName = "id_appuser")
    @JsonIgnoreProperties(value = {"user", "cartOrderDetails", "products"}, allowSetters = true)
    private AppUser grower_id;

}
