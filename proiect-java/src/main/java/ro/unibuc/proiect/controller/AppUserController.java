package ro.unibuc.proiect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.unibuc.proiect.dto.AppUserDTO;
import ro.unibuc.proiect.service.AppUserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/appuser")
public class AppUserController {

    private final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    private final AppUserService appUserService;


    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @PostMapping()
    public ResponseEntity<AppUserDTO> addAppUser(@Valid @RequestBody AppUserDTO appUserDTO) {

        return ResponseEntity.ok().body(appUserService.save(appUserDTO));
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<AppUserDTO> updateAppUserRole(@PathVariable(name="id") Long id) {

        return ResponseEntity.ok().body(appUserService.updateAppUser(id));
    }

    @GetMapping()
    public ResponseEntity<List<AppUserDTO>> getAllAppUsers(){

        return ResponseEntity.ok().body(appUserService.findAll());
    }

    @PostMapping("/upload/certificate")
    public ResponseEntity<String> saveCertificate(@RequestParam("id") Long id,
                                 @RequestParam("file") MultipartFile multipartFile) throws IOException {
        appUserService.uploadCertificate(id,multipartFile);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(appUserService.getImageForAppUser(id));
    }

}
