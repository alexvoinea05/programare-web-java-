package ro.unibuc.proiect.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.dto.CartItemsDTO;

@Mapper(componentModel = "spring", uses = { ProductMapper.class, CartOrderDetailsMapper.class })
public interface CartItemsMapper extends EntityMapper<CartItemsDTO, CartItems> {
    @Mapping(target = "idProduct", source = "product_id", qualifiedByName = "idProduct")
    @Mapping(target = "idOrderDetails", source = "idOrderDetails", qualifiedByName = "idCartOrderDetails")
    CartItemsDTO toDto(CartItems s);
}