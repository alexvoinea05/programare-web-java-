package ro.unibuc.proiect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.unibuc.proiect.dto.CartOrderDetailsDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.service.CartOrderDetailsService;
import ro.unibuc.proiect.util.CartOrderDetailsDTOUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartOrderDetailsController.class)
public class CartOrderDetailsControllerTest {

    @MockBean
    private CartOrderDetailsService cartOrderDetailsService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test_modifyCartOrderDetails_ok() throws Exception {
        //Arrange
        Long id = 1L;
        CartOrderDetailsDTO dto = CartOrderDetailsDTOUtil.bCartOrderDetailsDto(id);
        when(cartOrderDetailsService.updateCartOrderDetails(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/cart-order-details/"+id)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    void test_modifyCartOrderDetails_id_not_found() throws Exception {
        //Arrange
        Long id = 1L;
        when(cartOrderDetailsService.updateCartOrderDetails(any())).thenThrow(new BadRequestCustomException("Id not found"));

        //Act
        MvcResult result = mockMvc.perform(put("/cart-order-details/"+id))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
