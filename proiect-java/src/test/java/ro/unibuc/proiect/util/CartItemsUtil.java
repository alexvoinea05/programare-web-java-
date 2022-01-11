package ro.unibuc.proiect.util;

import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.domain.CartOrderDetails;
import ro.unibuc.proiect.domain.Product;

import java.math.BigDecimal;

public class CartItemsUtil {

    public static CartItems aCartItems(Long id){
        return CartItems.builder()
                .idCartItems(id)
                .quantity(BigDecimal.valueOf(50L))
                .product_id(Product.builder()
                        .idProduct(1L)
                        .build())
                .build();
    }

    public static CartItems bCartItems(Long id){
        return CartItems.builder()
                .idCartItems(id)
                .quantity(BigDecimal.valueOf(1L))
                .product_id(Product.builder()
                        .idProduct(1L)
                        .stock(BigDecimal.valueOf(10L))
                        .build())
                .idOrderDetails(CartOrderDetails.builder()
                        .idCartOrderDetails(1L)
                        .idAppUser(AppUser.builder()
                                .idAppUser(1L)
                                .build())
                        .statusCommand("NEFINALIZAT")
                        .totalPrice(BigDecimal.valueOf(5L))
                        .build())
                .build();
    }
}
