package ro.unibuc.proiect.util;

import ro.unibuc.proiect.dto.AppUserDTO;
import ro.unibuc.proiect.dto.CategoryDTO;
import ro.unibuc.proiect.dto.ProductDTO;

import java.math.BigDecimal;

public class ProductDTOUtil {

    public static ProductDTO aProductDTO(Long id){
        return ProductDTO.builder()
                .idProduct(id)
                .name("rosii")
                .stock(BigDecimal.valueOf(3L))
                .price(BigDecimal.valueOf(2L))
                .idCategory(CategoryDTO.builder()
                        .idCategory(1L)
                        .categoryName("legume")
                        .build())
                .idGrower(AppUserDTO.builder()
                        .idAppUser(1L)
                        .email("test@yahoo.com")
                        .firstName("a")
                        .lastName("b")
                        .username("test")
                        .build())
                .build()
                ;
    }

    public static ProductDTO aProductDTOWithNoGrower(Long id){
        return ProductDTO.builder()
                .idProduct(id)
                .name("rosii")
                .stock(BigDecimal.valueOf(3L))
                .price(BigDecimal.valueOf(2L))
                .idCategory(CategoryDTO.builder()
                        .idCategory(1L)
                        .categoryName("legume")
                        .build())
                .build()
                ;
    }
}
