package ro.unibuc.proiect.mapper;

import org.mapstruct.Mapper;
import ro.unibuc.proiect.domain.Authority;
import ro.unibuc.proiect.dto.AuthorityDTO;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {


//    @Named("authorityName")
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "name", source = "name")
//    Set<Authority> toAuthorityAppUser(Set<AuthorityDTO> authority);
//
//    @Named("authorityNameDto")
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "name", source = "name")
//    Set<AuthorityDTO> toAuthorityAppUserDto(Set<Authority> authority);
}

