package ro.unibuc.proiect.util;

import ro.unibuc.proiect.domain.Category;

public class CategoryUtil {

    public static Category aCategory(Long id){
        return Category.builder()
                .idCategory(id)
                .categoryName("legume")
                .build();
    }

    public static Category aCategory(String name){
        return Category.builder()
                .idCategory(1L)
                .categoryName(name)
                .build();
    }
}
