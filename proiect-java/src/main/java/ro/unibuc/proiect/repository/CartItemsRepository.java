package ro.unibuc.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.unibuc.proiect.domain.CartItems;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {

    @Query(
            value = "select c from CartItems c where c.product_id.idProduct=:idProduct and c.idOrderDetails.idCartOrderDetails=:idCartOrderDetails"
    )
    Optional<CartItems> findProductInCart(@Param("idProduct") Long idProduct, @Param("idCartOrderDetails") Long idCartOrderDetails);

    @Query(value = "select c from CartItems c where c.idOrderDetails.idCartOrderDetails=:idCartOrderDetails")
    Optional<List<CartItems>> findProductsByIdOrder(@Param("idCartOrderDetails") Long idCartOrderDetails);

}
