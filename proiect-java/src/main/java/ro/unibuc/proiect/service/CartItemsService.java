package ro.unibuc.proiect.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.proiect.domain.CartItems;
import ro.unibuc.proiect.domain.CartOrderDetails;
import ro.unibuc.proiect.domain.Product;
import ro.unibuc.proiect.dto.CartItemsDTO;
import ro.unibuc.proiect.exception.BadRequestCustomException;
import ro.unibuc.proiect.exception.NoDataFoundException;
import ro.unibuc.proiect.mapper.CartItemsMapper;
import ro.unibuc.proiect.mapper.ProductMapper;
import ro.unibuc.proiect.repository.AppUserRepository;
import ro.unibuc.proiect.repository.CartItemsRepository;
import ro.unibuc.proiect.repository.CartOrderDetailsRepository;
import ro.unibuc.proiect.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartItemsService {

    private final Logger log = LoggerFactory.getLogger(CartItemsService.class);

    @Autowired
    private final CartItemsRepository cartItemsRepository;

    @Autowired
    private final CartOrderDetailsRepository cartOrderDetailsRepository;

    @Autowired
    private final CartItemsMapper cartItemsMapper;

    @Autowired
    private final ProductMapper productMapper;

    @Autowired
    private final AppUserRepository appUserRepository;

    @Autowired
    private final ProductRepository productRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository, CartOrderDetailsRepository cartOrderDetailsRepository, CartItemsMapper cartItemsMapper, ProductMapper productMapper, AppUserRepository appUserRepository, ProductRepository productRepository) {
        this.cartItemsRepository = cartItemsRepository;
        this.cartOrderDetailsRepository = cartOrderDetailsRepository;
        this.cartItemsMapper = cartItemsMapper;
        this.productMapper = productMapper;
        this.appUserRepository = appUserRepository;
        this.productRepository = productRepository;
    }

    public CartItemsDTO save(CartItemsDTO cartItemsDTO) {
        log.debug("Request to save CartItems : {}", cartItemsDTO);
        CartItems cartItems = cartItemsMapper.toEntity(cartItemsDTO);
        cartItems = cartItemsRepository.save(cartItems);
        return cartItemsMapper.toDto(cartItems);
    }

    @Transactional(readOnly = true)
    public List<CartItemsDTO> findAll() {
        log.debug("Request to get all CartItems");
        return cartItemsRepository.findAll().stream().map(cartItems -> cartItemsMapper.toDto(cartItems)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CartItemsDTO> findOne(Long id) {
        log.debug("Request to get CartItems : {}", id);
        if(!cartItemsRepository.findById(id).isPresent()){
            throw new NoDataFoundException("CartItems not found for id : " + id);
        }
        return cartItemsRepository.findById(id).map(cartItemsMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete CartItems : {}", id);
        cartItemsRepository.deleteById(id);
    }

    public String addProduct(CartItemsDTO cartItemsDTO, Long idAppUser) {
        log.debug("Cart Intems ok");
        if (productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).get().getStock().compareTo(cartItemsDTO.getQuantity()) < 0) {
            throw new BadRequestCustomException("Cantitatea selectata este mai mare decat stocul!");
        }
        if (cartOrderDetailsRepository
                .findActiveCartOrderDetailsForCurrentAppUser(idAppUser, "FINALIZAT", "PRODUSE INDISPONIBILE")
                .isPresent()
        ) {
            CartOrderDetails cartOrderDetails = cartOrderDetailsRepository
                    .findActiveCartOrderDetailsForCurrentAppUser(idAppUser, "FINALIZAT", "PRODUSE INDISPONIBILE")
                    .get();
            log.debug("Cart Order Details has been retrieved", cartOrderDetails);
            BigDecimal quantity = cartItemsDTO.getQuantity();
            if (productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).isPresent()) {
                Product product = productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).get();
                BigDecimal price = product.getPrice();
                BigDecimal totalPrice = cartOrderDetails.getTotalPrice().add(quantity.multiply(price));
                cartOrderDetails.setTotalPrice(totalPrice);
            }
            cartOrderDetailsRepository.saveAndFlush(cartOrderDetails);

            if (
                    cartItemsRepository
                            .findProductInCart(cartItemsDTO.getIdProduct().getIdProduct(), cartOrderDetails.getIdCartOrderDetails())
                            .isPresent()
            ) {
                log.debug("This object is already in the cart");
                CartItems cartItems = cartItemsRepository
                        .findProductInCart(cartItemsDTO.getIdProduct().getIdProduct(), cartOrderDetails.getIdCartOrderDetails())
                        .get();
                BigDecimal totalQuantity = cartItems.getQuantity().add(quantity);
                cartItems.setQuantity(totalQuantity);
                cartItemsRepository.save(cartItems);
            } else {
                log.debug("This object is not in the cart");
                CartItems cartItems = new CartItems();
                cartItems.setQuantity(quantity);
                if (productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).isPresent()) {
                    cartItems.setProduct_id(productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).get());
                }
                cartItems.setIdOrderDetails(cartOrderDetails);
                cartItemsRepository.save(cartItems);
            }
        } else {
            CartOrderDetails cartOrderDetails = new CartOrderDetails();
            BigDecimal quantity = cartItemsDTO.getQuantity();
            if (productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).isPresent()) {
                Product product = productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).get();
                BigDecimal price = product.getPrice();
                BigDecimal totalPrice = quantity.multiply(price);
                cartOrderDetails.setTotalPrice(totalPrice);
            }
            cartOrderDetails.setIdAppUser(appUserRepository.getById(idAppUser));
            cartOrderDetails.setStatusCommand("NEFINALIZAT");
            cartOrderDetailsRepository.saveAndFlush(cartOrderDetails);

            CartItems cartItems = new CartItems();
            cartItems.setQuantity(quantity);
            if (productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).isPresent()) {
                cartItems.setProduct_id(productRepository.findById(cartItemsDTO.getIdProduct().getIdProduct()).get());
            }
            cartItems.setIdOrderDetails(cartOrderDetails);
            cartItemsRepository.save(cartItems);
        }

        return "Produs adaugat";
    }
}
