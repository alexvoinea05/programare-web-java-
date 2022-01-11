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
import ro.unibuc.proiect.dto.ProductDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.service.ProductService;
import ro.unibuc.proiect.util.ProductDTOUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Testing creating product")
    void test_createProduct_ok() throws Exception {
        //Arrange
        ProductDTO dto = ProductDTOUtil.aProductDTO(1L);
        when(productService.save(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/product")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    void test_createProduct_no_id_grower() throws Exception {
        //Arrange
        ProductDTO dto = ProductDTOUtil.aProductDTOWithNoGrower(1L);
        when(productService.save(any())).thenThrow(new BadRequestCustomException("Grower not present"));

        //Act
        MvcResult result = mockMvc.perform(post("/product")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void test_findAll() throws Exception {

        ProductDTO dto = ProductDTOUtil.aProductDTO(1L);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(dto);
        when(productService.findAll()).thenReturn(productDTOList);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void test_modifyProduct_ok() throws Exception {
        //Arrange
        Long id = 1L;
        ProductDTO dto = ProductDTOUtil.aProductDTO(id);
        when(productService.update(any(),any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/product/"+id)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    void test_getOneCategory() throws Exception {
        Long id = 1L;
        ProductDTO dto = ProductDTOUtil.aProductDTO(1L);
        when(productService.findOne(1L)).thenReturn(dto);

        mockMvc.perform(get("/product/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(dto.getName())));
    }

    @Test
    void test_getOneUser_Exception() throws Exception {
        when(productService.findOne(1L)).thenThrow(new NoDataFoundException("Nu sunt date"));

        mockMvc.perform(get("/product/" + 1L))
                .andExpect(status().isNotFound());
    }
//    @Test
//    void test_getOneUser() throws Exception {
//        String username = "Cristina";
//        UserDto dto = UserDtoUtil.aUserDto(username);
//        when(userService.getOne(username)).thenReturn(dto);
//
//        mockMvc.perform(get("/users/" + username))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("username", is(dto.getUsername())))
//                .andExpect(jsonPath("$.otherInformation", is(dto.getOtherInformation())));
//    }
//
//    @Test
//    void test_getOneUser_Exception() throws Exception {
//        String username = "Cristina";
//        when(userService.getOne(username)).thenThrow(new UserNotFoundException(username));
//
//        mockMvc.perform(get("/users/" + username))
//                .andExpect(status().isNoContent());
//    }
}
