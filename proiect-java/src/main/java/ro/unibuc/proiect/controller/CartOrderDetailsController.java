package ro.unibuc.proiect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.proiect.dto.CartOrderDetailsDTO;
import ro.unibuc.proiect.service.CartOrderDetailsService;

@RestController
@RequestMapping("/cart-order-details")
public class CartOrderDetailsController {

    private final Logger log = LoggerFactory.getLogger(CartOrderDetailsController.class);

    private final CartOrderDetailsService cartOrderDetailsService;

    public CartOrderDetailsController(CartOrderDetailsService cartOrderDetailsService) {
        this.cartOrderDetailsService = cartOrderDetailsService;
    }

    @PutMapping("/{idCartOrderDetails}")
    public ResponseEntity<CartOrderDetailsDTO> updateCartItems(@PathVariable(value = "idCartOrderDetails") final Long idCartOrderDetails) {
        log.debug("REST request to update CartItems : {}, {}", idCartOrderDetails);

        CartOrderDetailsDTO result = cartOrderDetailsService.updateCartOrderDetails(idCartOrderDetails);

        return ResponseEntity
                .ok()
                .body(result);
    }

}
