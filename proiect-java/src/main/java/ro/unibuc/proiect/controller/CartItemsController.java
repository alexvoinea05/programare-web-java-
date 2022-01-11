package ro.unibuc.proiect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.proiect.dto.CartItemsDTO;
import ro.unibuc.proiect.service.CartItemsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart-items")
public class CartItemsController {

    private final Logger log = LoggerFactory.getLogger(CartItemsController.class);

    @Autowired
    private final CartItemsService cartItemsService;

    public CartItemsController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @PostMapping("/add/product/{idAppUser}")
    public ResponseEntity<String> addProductToCart(
            @PathVariable(value = "idAppUser", required = true) final Long idAppUser,
            @Valid @RequestBody CartItemsDTO cartItemsDTO) {
        String result = cartItemsService.addProduct(cartItemsDTO,idAppUser);
        return ResponseEntity.ok().body(result);
    }


}

