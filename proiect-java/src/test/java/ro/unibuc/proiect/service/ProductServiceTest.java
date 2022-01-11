package ro.unibuc.proiect.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.proiect.domain.AppUser;
import ro.unibuc.proiect.domain.Authority;
import ro.unibuc.proiect.domain.Product;
import ro.unibuc.proiect.dto.ProductDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.exception.GrowerRightsException;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.ProductMapper;
import ro.unibuc.proiect.repository.AppUserRepository;
import ro.unibuc.proiect.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static ro.unibuc.proiect.util.AppUserUtil.AppUserAuthorities;
import static ro.unibuc.proiect.util.ProductDTOUtil.aProductDTO;
import static ro.unibuc.proiect.util.ProductDTOUtil.aProductDTOWithNoGrower;
import static ro.unibuc.proiect.util.ProductUtil.aProduct;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void test_save_with_rights(){
        Product product = aProduct(1L);
        AppUser appUser = AppUserAuthorities(new Authority("GROWER"));
        ProductDTO productDTO = aProductDTO(1L);

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDTO);

        ProductDTO result = productService.save(productDTO);

        assertNotNull(result);
        assertEquals(1L,result.getIdProduct());
    }

    @Test
    void test_save_with_no_rights(){
        AppUser appUser = AppUserAuthorities(new Authority("USER"));
        ProductDTO productDTO = aProductDTO(1L);

        when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));

        GrowerRightsException growerRightsException = assertThrows(GrowerRightsException.class, () -> productService.save(productDTO));
    }

    @Test
    void test_save_app_not_exist(){
        ProductDTO productDTO = aProductDTOWithNoGrower(1L);

        BadRequestCustomException badRequestCustomException = assertThrows(BadRequestCustomException.class, () -> productService.save(productDTO));
    }

    @Test
    void test_save_app_not_found() {

        ProductDTO productDTO = aProductDTO(1L);
        when(appUserRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> productService.save(productDTO));
    }

    @Test
    void test_findAll() {
        Product product = aProduct(1L);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        ProductDTO productDTO = aProductDTO(1L);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toDto(product)).thenReturn(productDTO);

        //Act
        List<ProductDTO> result = productService.findAll();

        //Assert
        assertNotNull(result);
        verify(productMapper, times(1)).toDto(product);
        verify(productRepository, times(1)).findAll();

    }

    @Test
    void test_findById_exists() {
        Product product = aProduct(1L);
        ProductDTO productDTO = aProductDTO(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDTO);

        //Act
        ProductDTO result = productService.findOne(1L);

        //Assert
        assertNotNull(result);
        verify(productMapper, times(1)).toDto(product);
        verify(productRepository, times(2)).findById(1L);

        assertEquals(result.getIdProduct(), 1L);
    }

    @Test
    void test_findById_not_exists() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        NoDataFoundException noDataFoundException = assertThrows(NoDataFoundException.class, () -> productService.findOne(1L));

    }
}
