package ro.unibuc.proiect.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ro.unibuc.proiect.domain.Product;
import ro.unibuc.proiect.dto.ProductDTO;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, AppUserMapper.class })
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "idCategory", source = "category_id", qualifiedByName = "idCategory")
    @Mapping(target = "idGrower", source = "grower_id", qualifiedByName = "idAppUser")
    ProductDTO toDto(Product s);

    @Named("idProduct")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idProduct", source = "idProduct")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "idGrower", source = "grower_id", qualifiedByName = "idAppUser")
    ProductDTO toDtoId(Product product);

    @Mapping(target = "category_id", source = "idCategory", qualifiedByName = "categoryId")
    @Mapping(target = "grower_id", source = "idGrower", qualifiedByName = "growerId")
    Product toEntity(ProductDTO s);



}