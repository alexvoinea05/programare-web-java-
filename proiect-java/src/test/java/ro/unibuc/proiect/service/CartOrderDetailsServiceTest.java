package ro.unibuc.proiect.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.domain.CartOrderDetails;
import ro.unibuc.proiect.domain.Product;
import ro.unibuc.proiect.dto.CartOrderDetailsDTO;
import ro.unibuc.proiect.dto.ProductDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.mapper.CartOrderDetailsMapper;
import ro.unibuc.proiect.mapper.ProductMapper;
import ro.unibuc.proiect.repository.CartItemsRepository;
import ro.unibuc.proiect.repository.CartOrderDetailsRepository;
import ro.unibuc.proiect.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ro.unibuc.proiect.util.CartItemsUtil.bCartItems;
import static ro.unibuc.proiect.util.CartOrderDetailsDTOUtil.aCartOrderDetailsDto;
import static ro.unibuc.proiect.util.CartOrderDetailsDTOUtil.bCartOrderDetailsDto;
import static ro.unibuc.proiect.util.CartOrderDetailsUtil.*;
import static ro.unibuc.proiect.util.ProductDTOUtil.aProductDTO;
import static ro.unibuc.proiect.util.ProductUtil.aProduct;

@ExtendWith(MockitoExtension.class)
public class CartOrderDetailsServiceTest {
    
    @Mock
    private CartOrderDetailsRepository cartOrderDetailsRepository;
    
    @Mock 
    private CartOrderDetailsMapper cartOrderDetailsMapper;

    @Mock
    private CartItemsRepository cartItemsRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductService productService;
    
    @InjectMocks
    private CartOrderDetailsService cartOrderDetailsService;

    @Test
    void test_updateCartOrderDetails_id_not_exist(){

        when(cartOrderDetailsRepository.existsById(1L)).thenReturn(false);

        BadRequestCustomException badRequestCustomException = assertThrows(BadRequestCustomException.class, () -> cartOrderDetailsService.updateCartOrderDetails(1L));

    }

    @Test
    void test_updateCartOrderDetails_ok(){

        CartOrderDetails cartOrderDetails = aCartOrderDetails(1L);
        CartOrderDetailsDTO updatedCartOrderDetailsDto = bCartOrderDetailsDto(1L);
        Product product = aProduct(1L);
        ProductDTO productDTO = aProductDTO(1L);
        CartItems cartItems = bCartItems(1L);
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(cartItems);

        when(cartOrderDetailsRepository.existsById(1L)).thenReturn(true);
        when(cartOrderDetailsRepository.findById(1L)).thenReturn(Optional.ofNullable(cartOrderDetails));
        when(cartItemsRepository.findProductsByIdOrder(1L)).thenReturn(Optional.of(cartItemsList));
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        when(productMapper.toDto(product)).thenReturn(productDTO);
        when(cartOrderDetailsMapper.toDto(cartOrderDetails)).thenReturn(updatedCartOrderDetailsDto);
        when(productService.save(productDTO)).thenReturn(productDTO);

        CartOrderDetailsDTO result = cartOrderDetailsService.updateCartOrderDetails(1L);

        assertNotNull(result);
    }

    @Test
    void test_findAll() {
        CartOrderDetails cartOrderDetails = aCartOrderDetails(1L);
        List<CartOrderDetails> cartOrderDetailsList = new ArrayList<>();
        cartOrderDetailsList.add(cartOrderDetails);
        CartOrderDetailsDTO cartOrderDetailsDTO = aCartOrderDetailsDto(1L);
        List<CartOrderDetailsDTO> cartOrderDetailsDTOList = new ArrayList<>();
        cartOrderDetailsDTOList.add(cartOrderDetailsDTO);

        when(cartOrderDetailsRepository.findAll()).thenReturn(cartOrderDetailsList);
        when(cartOrderDetailsMapper.toDto(cartOrderDetails)).thenReturn(cartOrderDetailsDTO);

        //Act
        List<CartOrderDetailsDTO> result = cartOrderDetailsService.findAll();

        //Assert
        assertNotNull(result);
        verify(cartOrderDetailsMapper, times(1)).toDto(cartOrderDetails);
        verify(cartOrderDetailsRepository, times(1)).findAll();

    }

    @Test
    void test_findById_exists() {
        CartOrderDetails cartOrderDetails = aCartOrderDetails(1L);
        CartOrderDetailsDTO cartOrderDetailsDTO = aCartOrderDetailsDto(1L);

        when(cartOrderDetailsRepository.findById(1L)).thenReturn(Optional.of(cartOrderDetails));
        when(cartOrderDetailsMapper.toDto(cartOrderDetails)).thenReturn(cartOrderDetailsDTO);

        //Act
        Optional<CartOrderDetailsDTO> result = cartOrderDetailsService.findOne(1L);

        //Assert
        assertNotNull(result);
        verify(cartOrderDetailsMapper, times(1)).toDto(cartOrderDetails);
        verify(cartOrderDetailsRepository, times(1)).findById(1L);

        assertEquals(result.get().getIdCartOrderDetails(), 1L);
    }
}
