package ro.unibuc.proiect.util;

import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.dto.CartOrderDetailsDTO;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static ro.unibuc.proiect.util.AppUserDtoUtil.aAppUserDto;
import static ro.unibuc.proiect.util.CartItemsUtil.bCartItems;

public class CartOrderDetailsDTOUtil {

    public static CartOrderDetailsDTO aCartOrderDetailsDto(Long id){
        Set<CartItems> cartItemsSet = new HashSet<>();
        cartItemsSet.add(bCartItems(1L));
        return CartOrderDetailsDTO.builder()
                .idCartOrderDetails(id)
                .totalPrice(BigDecimal.valueOf(10L))
                .statusCommand("NEFINALIZAT")
                .idAppUser(aAppUserDto(1L))
                .cartItems(cartItemsSet)
                .build();
    }

    public static CartOrderDetailsDTO bCartOrderDetailsDto(Long id){
        Set<CartItems> cartItemsSet = new HashSet<>();
        cartItemsSet.add(bCartItems(1L));
        return CartOrderDetailsDTO.builder()
                .idCartOrderDetails(id)
                .totalPrice(BigDecimal.valueOf(10L))
                .statusCommand("FINALIZAT")
                .idAppUser(aAppUserDto(1L))
                .cartItems(cartItemsSet)
                .build();
    }
}
