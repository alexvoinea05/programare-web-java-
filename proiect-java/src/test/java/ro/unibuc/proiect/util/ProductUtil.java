package ro.unibuc.proiect.util;


import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.domain.Category;
import ro.unibuc.proiect.domain.Product;

import java.math.BigDecimal;

public class ProductUtil {

    public static Product aProduct(Long id) {

        return Product.builder()
                .idProduct(id)
                .name("rosii")
                .stock(BigDecimal.valueOf(3L))
                .price(BigDecimal.valueOf(2L))
                .category_id(Category.builder()
                        .idCategory(1L)
                        .categoryName("legume")
                        .build())
                .grower_id(AppUser.builder()
                        .idAppUser(1L)
                        .email("test@yahoo.com")
                        .firstName("a")
                        .lastName("b")
                        .certificateUrl("test")
                        .username("test")
                        .build())
                .build();
    }

}
