package ro.unibuc.proiect.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.proiect.domain.Category;
import ro.unibuc.proiect.dto.CategoryDTO;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.CategoryMapper;
import ro.unibuc.proiect.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        if(categoryRepository.findById(id).isPresent()) {
            Category category = categoryMapper.toEntity(categoryDTO);
            category = categoryRepository.save(category);
            return categoryMapper.toDto(category);
        }
        else {
            throw new NoDataFoundException("Category not found with id" + id);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        if(categoryRepository.findById(id).isPresent()) {
            return categoryMapper.toDto(categoryRepository.findById(id).get());
        }
        else{
            throw new NoDataFoundException("Category not found with id : " + id);
        }
    }

    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}

