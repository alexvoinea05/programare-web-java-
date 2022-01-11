package ro.unibuc.proiect.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.proiect.domain.Category;
import ro.unibuc.proiect.dto.CategoryDTO;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.CategoryMapper;
import ro.unibuc.proiect.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ro.unibuc.proiect.util.CategoryDTOUtil.aCategoryDTO;
import static ro.unibuc.proiect.util.CategoryUtil.aCategory;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void test_save(){
        Category category = aCategory("legume");
        CategoryDTO categoryDTO = aCategoryDTO("legume");
        Category savedCategory = aCategory(1L);

        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(categoryDTO);

        //Act
        CategoryDTO result = categoryService.save(categoryDTO);

        //Assert
        assertNotNull(result);
        verify(categoryMapper, times(1)).toEntity(categoryDTO);
        verify(categoryMapper, times(1)).toDto(savedCategory);
        verify(categoryRepository, times(1)).save(category);

        assertEquals(1L, result.getIdCategory());
    }

    @Test
    void test_update_does_not_exist() {

        when(categoryRepository.findById(3L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> categoryService.update(3L,any()));

    }

    @Test
    void test_findAll() {
        Category category = aCategory(1L);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        CategoryDTO categoryDTO = aCategoryDTO(1L);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDTOList.add(categoryDTO);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        //Act
        List<CategoryDTO> result = categoryService.findAll();

        //Assert
        assertNotNull(result);
        verify(categoryMapper, times(1)).toDto(category);
        verify(categoryRepository, times(1)).findAll();

        assertEquals(1L,result.get(0).getIdCategory());
        assertEquals("legume",result.get(0).getCategoryName());

    }

    @Test
    void test_findById_exists() {
        Category category = aCategory(1L);
        CategoryDTO categoryDTO = aCategoryDTO(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        //Act
        CategoryDTO result = categoryService.findOne(1L);

        //Assert
        assertNotNull(result);
        verify(categoryMapper, times(1)).toDto(category);
        verify(categoryRepository, times(2)).findById(1L);

        assertEquals( 1L, result.getIdCategory());
    }

    @Test
    void test_findById_not_exists() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> categoryService.findOne(1L));

    }
}
