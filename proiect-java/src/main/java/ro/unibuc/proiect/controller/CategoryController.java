package ro.unibuc.proiect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.proiect.dto.CategoryDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.service.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/cat")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getIdCategory() != null) {
            throw new BadRequestCustomException("A new category cannot already have an ID");
        }
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/{idCategory}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable(value = "idCategory", required = false) final Long idCategory,
            @Valid @RequestBody CategoryDTO categoryDTO){
        log.debug("REST request to update Category : {}, {}", idCategory, categoryDTO);
        if (categoryDTO.getIdCategory() == null) {
            throw new BadRequestCustomException("Invalid id");
        }
        if (!Objects.equals(idCategory, categoryDTO.getIdCategory())) {
            throw new BadRequestCustomException("Invalid ID");
        }

        CategoryDTO result = categoryService.update(idCategory,categoryDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        log.debug("REST request to get Categories");
        return ResponseEntity.ok().body(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        return ResponseEntity.ok().body(categoryService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
