package ro.unibuc.proiect.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(CartOrderDetailsService.class);

    private final CartOrderDetailsRepository cartOrderDetailsRepository;

    private final CartOrderDetailsMapper cartOrderDetailsMapper;

    private final CartItemsRepository cartItemsRepository;

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ProductMapper productMapper;

    public CartOrderDetailsService(
            CartOrderDetailsRepository cartOrderDetailsRepository,
            CartOrderDetailsMapper cartOrderDetailsMapper,
            CartItemsRepository cartItemsRepository, ProductRepository productRepository, ProductService productService, ProductMapper productMapper) {
        this.cartOrderDetailsRepository = cartOrderDetailsRepository;
        this.cartOrderDetailsMapper = cartOrderDetailsMapper;
        this.cartItemsRepository = cartItemsRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    public CartOrderDetailsDTO save(CartOrderDetailsDTO cartOrderDetailsDTO) {
        log.debug("Request to save CartOrderDetails : {}", cartOrderDetailsDTO);
        CartOrderDetails cartOrderDetails = cartOrderDetailsMapper.toEntity(cartOrderDetailsDTO);
        cartOrderDetails = cartOrderDetailsRepository.save(cartOrderDetails);
        return cartOrderDetailsMapper.toDto(cartOrderDetails);
    }

    public CartOrderDetailsDTO updateCartOrderDetails(Long idCartOrderDetails){
        if (!cartOrderDetailsRepository.existsById(idCartOrderDetails)) {
            throw new BadRequestCustomException("CartOrderDetails not found");
        }

        CartOrderDetails cartOrderDetails = cartOrderDetailsRepository.findById(idCartOrderDetails).get();
        List<CartItems> cartItemsList = cartItemsRepository.findProductsByIdOrder(cartOrderDetails.getIdCartOrderDetails()).get();
        boolean ok = true;
        for (CartItems cartItems : cartItemsList) {
            if (cartItems.getQuantity().compareTo(cartItems.getProduct_id().getStock()) <= 0) {} else {
                ok = false;
                break;
            }
        }
        if (ok) {
            for (CartItems cartItems : cartItemsList) {
                Product product = productRepository.findById(cartItems.getProduct_id().getIdProduct()).get();
                product.setStock(product.getStock().subtract(cartItems.getQuantity()));
                ProductDTO productDTO = productService.save(productMapper.toDto(product));
            }
            cartOrderDetails.setStatusCommand("FINALIZAT");
        } else {
            cartOrderDetails.setStatusCommand("PRODUSE INDISPONIBILE");
        }
        log.debug("CArt order det:{}", cartOrderDetails);

        return cartOrderDetailsMapper.toDto(cartOrderDetails);
    }

    @Transactional(readOnly = true)
    public List<CartOrderDetailsDTO> findAll() {
        log.debug("Request to get all CartOrderDetails");
        return cartOrderDetailsRepository.findAll().stream().map(cartOrderDetailsMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CartOrderDetailsDTO> findOne(Long id) {
        log.debug("Request to get CartOrderDetails : {}", id);
        return cartOrderDetailsRepository.findById(id).map(cartOrderDetailsMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete CartOrderDetails : {}", id);
        cartOrderDetailsRepository.deleteById(id);
    }
}

