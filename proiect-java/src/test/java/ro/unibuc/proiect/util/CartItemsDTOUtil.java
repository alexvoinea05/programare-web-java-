package ro.unibuc.proiect.util;

import ro.unibuc.proiect.dto.AppUserDTO;
import ro.unibuc.proiect.dto.CartItemsDTO;
import ro.unibuc.proiect.dto.CartOrderDetailsDTO;
import ro.unibuc.proiect.dto.ProductDTO;

import java.math.BigDecimal;

public class CartItemsDTOUtil {

    public static CartItemsDTO aCartItemsDto(Long id){
        return CartItemsDTO.builder()
                .idCartItems(id)
                .quantity(BigDecimal.valueOf(50L))
                .idProduct(ProductDTO.builder()
                        .idProduct(1L)
                        .build())
                .build();
    }

    public static CartItemsDTO bCartItemsDto(Long id){
        return CartItemsDTO.builder()
                .idCartItems(id)
                .quantity(BigDecimal.valueOf(1L))
                .idProduct(ProductDTO.builder()
                        .idProduct(1L)
                        .build())
                .idOrderDetails(CartOrderDetailsDTO.builder()
                        .idCartOrderDetails(1L)
                        .idAppUser(AppUserDTO.builder()
                                .idAppUser(1L)
                                .build())
                        .statusCommand("NEFINALIZAT")
                        .totalPrice(BigDecimal.valueOf(5L))
                        .build())
                .build();
    }
}
