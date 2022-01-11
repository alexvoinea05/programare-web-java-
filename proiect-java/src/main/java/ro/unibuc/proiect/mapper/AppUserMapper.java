package ro.unibuc.proiect.mapper;

import org.mapstruct.*;
import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.dto.AppUserDTO;


@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {


//    @Mapping(target="authorities", source="authorities", qualifiedByName = "authorityNameDto")
//    AppUserDTO toDto(AppUser appUser);

    @Named("idAppUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idAppUser", source = "idAppUser")
    @Mapping(target = "authorities", source = "authorities")
        //@Mapping(target="authorities", source="authorities", qualifiedByName = "authorityNameDto")
    AppUserDTO toDtoId(AppUser appUser);

    @Named("growerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idAppUser", source = "idAppUser")
    @Mapping(target = "authorities", source = "authorities")
        //@Mapping(target="authorities", source="authorities", qualifiedByName = "authorityNameDto")
    AppUser toEntityId(AppUserDTO appUserDTO);

//    @Mapping(target="authorities", source="authorities", qualifiedByName = "authorityName")
//    AppUser toEntity(AppUserDTO appUserDTO);
}
