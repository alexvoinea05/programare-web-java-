package ro.unibuc.proiect.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.domain.CartOrderDetails;
import ro.unibuc.proiect.domain.Product;
import ro.unibuc.proiect.dto.CartItemsDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.CartItemsMapper;
import ro.unibuc.proiect.repository.CartItemsRepository;
import ro.unibuc.proiect.repository.CartOrderDetailsRepository;
import ro.unibuc.proiect.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ro.unibuc.proiect.util.AppUserDtoUtil.aAppUserDto;
import static ro.unibuc.proiect.util.AppUserUtil.aAppUser;
import static ro.unibuc.proiect.util.CartItemsDTOUtil.aCartItemsDto;
import static ro.unibuc.proiect.util.CartItemsDTOUtil.bCartItemsDto;
import static ro.unibuc.proiect.util.CartItemsUtil.aCartItems;
import static ro.unibuc.proiect.util.CartItemsUtil.bCartItems;
import static ro.unibuc.proiect.util.CartOrderDetailsUtil.aCartOrderDetails;
import static ro.unibuc.proiect.util.ProductUtil.aProduct;

@ExtendWith(MockitoExtension.class)
public class CartItemsServiceTest {

    @Mock
    private CartItemsRepository cartItemsRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemsMapper cartItemsMapper;

    @Mock
    private CartOrderDetailsRepository cartOrderDetailsRepository;

    @InjectMocks
    private CartItemsService cartItemsService;

    @Test
    void test_addProduct_quantity_too_much(){
        CartItemsDTO cartItemsDTO = aCartItemsDto(1L);
        CartItems cartItems = aCartItems(1L);
        Product product = aProduct(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));

        BadRequestCustomException badRequestCustomException = assertThrows(BadRequestCustomException.class, () -> cartItemsService.addProduct(cartItemsDTO,1L));
    }

    @Test
    void test_addProduct_ok(){
        CartItemsDTO cartItemsDTO = bCartItemsDto(1L);
        CartItems cartItems = bCartItems(1L);
        Product product = aProduct(1L);
        CartOrderDetails cartOrderDetails = aCartOrderDetails(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        when(cartOrderDetailsRepository.findActiveCartOrderDetailsForCurrentAppUser(1L,"FINALIZAT", "PRODUSE INDISPONIBILE")).thenReturn(Optional.ofNullable(cartOrderDetails));
        when(cartOrderDetailsRepository.saveAndFlush(cartOrderDetails)).thenReturn(cartOrderDetails);
        when(cartItemsRepository.findProductInCart(1L,1L)).thenReturn(Optional.ofNullable(cartItems));
        when(cartItemsRepository.save(cartItems)).thenReturn(cartItems);

        cartItemsService.addProduct(cartItemsDTO,1L);

        verify(productRepository,times(3)).findById(1L);
        verify(cartItemsRepository,times(2)).findProductInCart(any(),any());
        verify(cartItemsRepository,times(1)).save(any());
        verify(cartOrderDetailsRepository,times(1)).saveAndFlush(any());
        verify(cartOrderDetailsRepository,times(2)).findActiveCartOrderDetailsForCurrentAppUser(any(),any(),any());

    }

    @Test
    void test_findAll() {
        CartItems cartItems = aCartItems(1L);
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(cartItems);
        CartItemsDTO cartItemsDTO = aCartItemsDto(1L);
        List<CartItemsDTO> cartItemsDTOList = new ArrayList<>();
        cartItemsDTOList.add(cartItemsDTO);

        when(cartItemsRepository.findAll()).thenReturn(cartItemsList);
        when(cartItemsMapper.toDto(cartItems)).thenReturn(cartItemsDTO);

        //Act
        List<CartItemsDTO> result = cartItemsService.findAll();

        //Assert
        assertNotNull(result);
        verify(cartItemsMapper, times(1)).toDto(cartItems);
        verify(cartItemsRepository, times(1)).findAll();

    }

    @Test
    void test_findById_exists() {
        CartItems cartItems = aCartItems(1L);
        CartItemsDTO cartItemsDTO = aCartItemsDto(1L);

        when(cartItemsRepository.findById(1L)).thenReturn(Optional.of(cartItems));
        when(cartItemsMapper.toDto(cartItems)).thenReturn(cartItemsDTO);

        //Act
        Optional<CartItemsDTO> result = cartItemsService.findOne(1L);

        //Assert
        assertNotNull(result);
        verify(cartItemsMapper, times(1)).toDto(cartItems);
        verify(cartItemsRepository, times(2)).findById(1L);

        assertEquals(result.get().getIdCartItems(), 1L);
    }

    @Test
    void test_findById_not_exists() {

        when(cartItemsRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> cartItemsService.findOne(1L));

    }
}
