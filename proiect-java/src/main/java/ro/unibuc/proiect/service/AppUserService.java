package ro.unibuc.proiect.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.domain.Authority;
import ro.unibuc.proiect.dto.AppUserDTO;
import ro.unibuc.proiect.exception.CertificateException;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.AppUserMapper;
import ro.unibuc.proiect.repository.AppUserRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    @Value("${custom.location}")
    private String location;

    public AppUserService(AppUserRepository appUserRepository, AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        Authority authority = new Authority("USER");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        appUser.setAuthorities(authoritySet);
        appUser.setAccountCreated(LocalDateTime.now());
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }


    public AppUserDTO updateAppUser(Long id) {
        log.debug("Request to update AppUser : {}", id);
        if(appUserRepository.findById(id).isPresent()) {
            AppUser appUser = appUserRepository.findById(id).get();
            if(appUser.getCertificateUrl() != null) {
                Set<Authority> authoritySet = appUser.getAuthorities();
                authoritySet.add(new Authority("GROWER"));
                appUser.setAuthorities(authoritySet);
                appUser = appUserRepository.save(appUser);
                return appUserMapper.toDto(appUser);
            }
            else {
                throw new CertificateException("Certificat invalid");
            }
        }
        else{
            throw new NoDataFoundException("No AppUser for id : " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<AppUserDTO> findAll() {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll().stream().map(appUserMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        if(!appUserRepository.findById(id).isPresent()){
            throw new NoDataFoundException("AppUser not found for id : " + id);
        }
        return appUserRepository.findById(id).map(appUserMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }

    public void uploadCertificate(Long id, MultipartFile multipartFile) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
        AppUser userCurrent = appUserRepository.findById(id).get();

        userCurrent.setCertificateUrl(fileName);
        AppUser savedUser = appUserRepository.save(userCurrent);

        String uploadDir = location + savedUser.getIdAppUser();

        saveFile(uploadDir,fileName,multipartFile);
    }

    public byte[] getImageForAppUser(Long id) {
        byte[] image = new byte[0];
        if (!appUserRepository.findById(id).isPresent()) {
            throw new NoDataFoundException("AppUser not found for id: " + id);
        }
        if(appUserRepository.getById(id).getCertificateUrl() == null) {
            throw new CertificateException("Nu exista un certificat");
        }
        String filename = appUserRepository.findById(id).get().getCertificateUrl();
        try {
            image = FileUtils.readFileToByteArray(new File( location + id + "/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Nu s-a putut salva imaginea : " + fileName, ioe);
        }
    }


}
