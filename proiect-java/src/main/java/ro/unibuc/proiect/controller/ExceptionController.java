package ro.unibuc.proiect.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.exception.CertificateException;
import ro.unibuc.proiect.exception.GrowerRightsException;
import ro.unibuc.proiect.exception.NoDataFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({BadRequestCustomException.class})
    public final ResponseEntity<Object> handleBadRequestCustomException(Exception ex, HttpServletRequest request) {
        log.error("Bad request custom exception ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( ex.getLocalizedMessage());
    }

    @ExceptionHandler({NoDataFoundException.class})
    public final ResponseEntity<Object> handleNoDataFoundException(Exception ex, HttpServletRequest request) {
        log.error("No data found exception ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( ex.getLocalizedMessage());
    }

    @ExceptionHandler({CertificateException.class})
    public final ResponseEntity<Object> handleCertificateException(Exception ex, HttpServletRequest request) {
        log.error("Certificat invalid ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body( ex.getLocalizedMessage());
    }

    @ExceptionHandler({GrowerRightsException.class})
    public final ResponseEntity<Object> handleGrowerRightsException(Exception ex, HttpServletRequest request) {
        log.error("Drepturi invalide pentru a adauga un produs ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body( ex.getLocalizedMessage());
    }


}
