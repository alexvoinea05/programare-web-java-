package ro.unibuc.proiect.util;

import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.domain.Authority;

import java.util.HashSet;
import java.util.Set;

public class AppUserUtil {

    public static AppUser aAppUser(String username) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setEmail("test@yahoo.com");
        appUser.setFirstName("a");
        appUser.setLastName("b");

        return appUser;
    }

    public static AppUser aAppUser(Long id) {
        AppUser appUser = new AppUser();
        appUser.setIdAppUser(id);
        appUser.setUsername("username1");
        appUser.setEmail("test@yahoo.com");
        appUser.setFirstName("a");
        appUser.setLastName("b");

        return appUser;
    }

    public static AppUser AppUserWithCertificate(Long id) {
        AppUser appUser = new AppUser();
        appUser.setIdAppUser(id);
        appUser.setUsername("username1");
        appUser.setEmail("test@yahoo.com");
        appUser.setFirstName("a");
        appUser.setLastName("b");
        appUser.setCertificateUrl("test");

        return appUser;
    }

    public static AppUser AppUserAuthorities(Authority authority){
        AppUser appUser = new AppUser();
        appUser.setIdAppUser(1L);
        appUser.setUsername("username1");
        appUser.setEmail("test@yahoo.com");
        appUser.setFirstName("a");
        appUser.setLastName("b");
        appUser.setCertificateUrl("test");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        appUser.setAuthorities(authoritySet);

        return appUser;
    }
}
