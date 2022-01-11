package ro.unibuc.proiect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.proiect.dto.ProductDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        log.debug("REST request to create Product : {}", productDTO);

        return ResponseEntity.ok().body(productService.save(productDTO));

    }

    @PutMapping("/{idProduct}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable(value = "idProduct", required = false) final Long idProduct,
            @Valid @RequestBody ProductDTO productDTO){
        log.debug("REST request to update Product : {}, {}", idProduct, productDTO);
        if (productDTO.getIdProduct() == null) {
            throw new BadRequestCustomException("Invalid id");
        }
        if (!Objects.equals(idProduct, productDTO.getIdProduct())) {
            throw new BadRequestCustomException("Invalid ID");
        }

        ProductDTO result = productService.update(idProduct,productDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }


    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.debug("REST request to get Products: {}");
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        return ResponseEntity.ok().body(productService.findOne(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

