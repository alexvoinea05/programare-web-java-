package ro.unibuc.proiect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.unibuc.proiect.dto.CategoryDTO;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.service.CategoryService;
import ro.unibuc.proiect.util.CategoryDTOUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper  objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Testing creating the category")
    void test_createCategory_ok() throws Exception {
        //Arrange
        CategoryDTO dto = CategoryDTOUtil.aCategoryController("legume");
        when(categoryService.save(dto)).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/category/cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void test_getAll() throws Exception {
        //Arrange
        CategoryDTO dto = CategoryDTOUtil.aCategoryDTO(1L);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDTOList.add(dto);
        when(categoryService.findAll()).thenReturn(categoryDTOList);

        //Act
         mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

    }

    @Test
    void test_getOneCategory() throws Exception {
        Long id = 1L;
        CategoryDTO dto = CategoryDTOUtil.aCategoryDTO(1L);
        when(categoryService.findOne(1L)).thenReturn(dto);

        mockMvc.perform(get("/category/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("categoryName", is(dto.getCategoryName())));
    }

    @Test
    void test_getOneUser_Exception() throws Exception {
        when(categoryService.findOne(1L)).thenThrow(new NoDataFoundException("Nu sunt date"));

        mockMvc.perform(get("/category/" + 1L))
                .andExpect(status().isNotFound());
    }
}
