package ro.unibuc.proiect.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.domain.Authority;
import ro.unibuc.proiect.dto.AppUserDTO;
import ro.unibuc.proiect.exception.CertificateException;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.AppUserMapper;
import ro.unibuc.proiect.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ro.unibuc.proiect.util.AppUserDtoUtil.AppUserDTOAuthorities;
import static ro.unibuc.proiect.util.AppUserDtoUtil.aAppUserDto;
import static ro.unibuc.proiect.util.AppUserUtil.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private AppUserMapper appUserMapper;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    void test_create() {
        //Arrange
        AppUserDTO appUserDto = aAppUserDto("username1");
        AppUser appUser = aAppUser("username1");
        AppUser savedUser = aAppUser(1L);

        when(appUserMapper.toEntity(appUserDto)).thenReturn(appUser);
        when(appUserRepository.save(appUser)).thenReturn(savedUser);
        when(appUserMapper.toDto(savedUser)).thenReturn(appUserDto);

        //Act
        AppUserDTO result = appUserService.save(appUserDto);

        //Assert
        assertNotNull(result);
        verify(appUserMapper, times(1)).toEntity(appUserDto);
        verify(appUserMapper, times(1)).toDto(savedUser);
        verify(appUserRepository, times(1)).save(appUser);

        assertEquals(result.getEmail(), savedUser.getEmail());

    }

    @Test
    void test_update_appuser_exists_with_no_certificate() {
        AppUser savedUser = aAppUser(1L);

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(savedUser));

        CertificateException certificateException = assertThrows(CertificateException.class, () -> appUserService.updateAppUser(1L));

    }

    @Test
    void test_update_appuser_exists_with_certificate() {
        AppUserDTO appUserDto = AppUserDTOAuthorities(new Authority("GROWER"));
        AppUser savedUser = AppUserWithCertificate(3L);
        AppUser appUserGrower = AppUserAuthorities(new Authority("GROWER"));

        when(appUserRepository.findById(3L)).thenReturn(Optional.of(savedUser));
        when(appUserRepository.save(savedUser)).thenReturn(appUserGrower);
        when(appUserMapper.toDto(appUserGrower)).thenReturn(appUserDto);

        //Act
        AppUserDTO result = appUserService.updateAppUser(3L);

        //Assert
        assertNotNull(result);
        verify(appUserMapper, times(1)).toDto(appUserGrower);
        verify(appUserRepository, times(2)).findById(3L);
        verify(appUserRepository, times(1)).save(savedUser);

        assertEquals(result.getEmail(), savedUser.getEmail());

    }

    @Test
    void test_update_appuser_does_not_exist() {

        when(appUserRepository.findById(3L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> appUserService.updateAppUser(3L));
    }

    @Test
    void test_findAll() {
        AppUser appUser = aAppUser("username1");
        List<AppUser> appUserList = new ArrayList<>();
        appUserList.add(appUser);
        AppUserDTO appUserDTO = aAppUserDto("username1");
        List<AppUserDTO> appUserDTOList = new ArrayList<>();
        appUserDTOList.add(appUserDTO);

        when(appUserRepository.findAll()).thenReturn(appUserList);
        when(appUserMapper.toDto(appUser)).thenReturn(appUserDTO);

        //Act
        List<AppUserDTO> result = appUserService.findAll();

        //Assert
        assertNotNull(result);
        verify(appUserMapper, times(1)).toDto(appUser);
        verify(appUserRepository, times(1)).findAll();

        assertEquals(result.get(0).getEmail(), "test@yahoo.com");
    }

    @Test
    void test_findById_exists() {
        AppUser appUser = aAppUser(1L);
        AppUserDTO appUserDTO = aAppUserDto(1L);

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        when(appUserMapper.toDto(appUser)).thenReturn(appUserDTO);

        //Act
        Optional<AppUserDTO> result = appUserService.findOne(1L);

        //Assert
        assertNotNull(result);
        verify(appUserMapper, times(1)).toDto(appUser);
        verify(appUserRepository, times(2)).findById(1L);

        assertEquals(result.get().getEmail(), "test@yahoo.com");
    }

    @Test
    void test_findById_not_exists() {

        when(appUserRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> appUserService.findOne(1L));

    }

    @Test
    void test_deleteById(){
        appUserRepository.deleteById(1L);

        verify(appUserRepository, times(1)).deleteById(1L);
    }


}
