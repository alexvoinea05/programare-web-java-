package ro.unibuc.proiect.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ro.unibuc.proiect.domain.CartOrderDetails;
import ro.unibuc.proiect.dto.CartOrderDetailsDTO;

@Mapper(componentModel = "spring", uses = { AppUserMapper.class, CartItemsMapper.class })
public interface CartOrderDetailsMapper extends EntityMapper<CartOrderDetailsDTO, CartOrderDetails> {
    //    @Mapping(target = "idAppUser", source = "idAppUser", qualifiedByName = "idAppUser")
    //    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "cartItems")
    CartOrderDetailsDTO toDto(CartOrderDetails s);

    @Named(value = "withoutCartItems")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idAppUser", source = "idAppUser", qualifiedByName = "idAppUser")
    @Mapping(target = "idCartOrderDetails", source = "idCartOrderDetails")
    @Mapping(target = "statusCommand", source = "statusCommand")
    @Mapping(target = "totalPrice", source = "totalPrice")
    CartOrderDetailsDTO toDtoWithoutCartItems(CartOrderDetails s);

    @Named("idCartOrderDetails")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCartOrderDetails", source = "idCartOrderDetails")
    CartOrderDetailsDTO toDtoId(CartOrderDetails cartOrderDetails);
}

