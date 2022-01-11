package ro.unibuc.proiect.util;

import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.domain.CartOrderDetails;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static ro.unibuc.proiect.util.AppUserUtil.aAppUser;
import static ro.unibuc.proiect.util.CartItemsUtil.bCartItems;

public class CartOrderDetailsUtil {

    public static CartOrderDetails aCartOrderDetails(Long id){
        Set<CartItems> cartItemsSet = new HashSet<>();
        cartItemsSet.add(bCartItems(1L));
        return CartOrderDetails.builder()
                .idCartOrderDetails(id)
                .totalPrice(BigDecimal.valueOf(10L))
                .statusCommand("NEFINALIZAT")
                .cartItems(cartItemsSet)
                .idAppUser(aAppUser(1L))
                .build();
    }

    public static CartOrderDetails bCartOrderDetails(Long id){
        Set<CartItems> cartItemsSet = new HashSet<>();
        cartItemsSet.add(bCartItems(1L));
        return CartOrderDetails.builder()
                .idCartOrderDetails(id)
                .totalPrice(BigDecimal.valueOf(10L))
                .statusCommand("FINALIZAT")
                .cartItems(cartItemsSet)
                .idAppUser(aAppUser(1L))
                .build();
    }
}
