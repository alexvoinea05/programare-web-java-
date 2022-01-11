package ro.unibuc.proiect.util;

import ro.unibuc.proiect.dto.CategoryDTO;

public class CategoryDTOUtil {

    public static CategoryDTO aCategoryDTO(Long id){
        return CategoryDTO.builder()
                .idCategory(id)
                .categoryName("legume")
                .build();
    }

    public static CategoryDTO aCategoryDTO(String name){
        return CategoryDTO.builder()
                .idCategory(1L)
                .categoryName(name)
                .build();
    }

    public static CategoryDTO aCategoryController(String name) {
        return CategoryDTO.builder()
                .categoryName(name)
                .build();
    }
}
