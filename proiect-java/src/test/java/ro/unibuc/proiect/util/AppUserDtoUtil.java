package ro.unibuc.proiect.util;

import ro.unibuc.proiect.domain.Authority;
import ro.unibuc.proiect.dto.AppUserDTO;

import java.util.HashSet;
import java.util.Set;

public class AppUserDtoUtil {

    public static AppUserDTO aAppUserDto(String username){
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setIdAppUser(1L);
        appUserDTO.setUsername(username);
        appUserDTO.setEmail("test@yahoo.com");
        appUserDTO.setFirstName("a");
        appUserDTO.setLastName("b");

        return appUserDTO;
    }
    public static AppUserDTO aAppUserDtoUsernameNull(){
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setIdAppUser(1L);
        appUserDTO.setUsername(null);
        appUserDTO.setEmail("test@yahoo.com");
        appUserDTO.setFirstName("a");
        appUserDTO.setLastName("b");

        return appUserDTO;
    }


    public static AppUserDTO aAppUserDto(Long id){
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setIdAppUser(id);
        appUserDTO.setUsername("username1");
        appUserDTO.setEmail("test@yahoo.com");
        appUserDTO.setFirstName("a");
        appUserDTO.setLastName("b");

        return appUserDTO;
    }

    public static AppUserDTO AppUserDTOAuthorities(Authority authority){
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setIdAppUser(3L);
        appUserDTO.setUsername("username1");
        appUserDTO.setEmail("test@yahoo.com");
        appUserDTO.setFirstName("a");
        appUserDTO.setLastName("b");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        appUserDTO.setAuthorities(authoritySet);

        return appUserDTO;
    }
}
