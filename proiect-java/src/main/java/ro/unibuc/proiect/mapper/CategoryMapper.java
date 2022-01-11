package ro.unibuc.proiect.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ro.unibuc.proiect.domain.Category;
import ro.unibuc.proiect.dto.CategoryDTO;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper  extends EntityMapper<CategoryDTO, Category>{
    @Named("idCategory")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCategory", source = "idCategory")
    @Mapping(target = "categoryName", source = "categoryName")
    CategoryDTO toDtoId(Category category);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCategory", source = "idCategory")
    @Mapping(target = "categoryName", source = "categoryName")
    Category toEntityId(CategoryDTO category);
}