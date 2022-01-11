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
import ro.unibuc.proiect.dto.AppUserDTO;
import ro.unibuc.proiect.exception.CertificateException;
import ro.unibuc.proiect.service.AppUserService;
import ro.unibuc.proiect.util.AppUserDtoUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(controllers = AppUserController.class)
public class AppUserControllerTest {

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper  objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Testing create appuser")
    void test_createAppUser_ok() throws Exception {
        //Arrange
        AppUserDTO dto = AppUserDtoUtil.aAppUserDto("username1");
        when(appUserService.save(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/appuser")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    void test_modifyAppUser_ok() throws Exception {
        //Arrange
        Long id = 1L;
        AppUserDTO dto = AppUserDtoUtil.aAppUserDto(id);
        when(appUserService.updateAppUser(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/appuser/role/"+id)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    void test_modifyAppUser_certificat_invalid() throws Exception {
        //Arrange
        Long id = 1L;
        AppUserDTO dto = AppUserDtoUtil.aAppUserDto(id);
        when(appUserService.updateAppUser(any())).thenThrow(new CertificateException("certificat invalid"));

        //Act
        MvcResult result = mockMvc.perform(put("/appuser/role/"+id)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void test_findAll() throws Exception {

        AppUserDTO dto = AppUserDtoUtil.aAppUserDto(1L);
        List<AppUserDTO> appUserDTOList = new ArrayList<>();
        appUserDTOList.add(dto);
        when(appUserService.findAll()).thenReturn(appUserDTOList);

        mockMvc.perform(get("/appuser"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
