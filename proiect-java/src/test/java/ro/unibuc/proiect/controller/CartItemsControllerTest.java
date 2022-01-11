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
import ro.unibuc.proiect.dto.CartItemsDTO;
import ro.unibuc.proiect.service.CartItemsService;
import ro.unibuc.proiect.util.CartItemsDTOUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartItemsController.class)
public class CartItemsControllerTest {

    @MockBean
    private CartItemsService cartItemsService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Add product in cart")
    void test_addProduct_ok() throws Exception {
        //Arrange
        CartItemsDTO dto = CartItemsDTOUtil.bCartItemsDto(1L);
        when(cartItemsService.addProduct(any(),any())).thenReturn("Produs adaugat");

        //Act
        MvcResult result = mockMvc.perform(post("/cart-items/add/product/"+1L)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Produs adaugat");
    }

}
