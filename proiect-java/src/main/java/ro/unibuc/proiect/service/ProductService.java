package ro.unibuc.proiect.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final AppUserRepository appUserRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, AppUserRepository appUserRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.appUserRepository = appUserRepository;
    }

    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        if(productDTO.getIdGrower() == null ) {
            throw new BadRequestCustomException("AppUser nu este prezent");
        }

        if(!appUserRepository.findById(productDTO.getIdGrower().getIdAppUser()).isPresent()) {
            throw new NoDataFoundException("AppUser nu exista in baza");
        }

        AppUser appUser = appUserRepository.findById(productDTO.getIdGrower().getIdAppUser()).get();
        Authority authority = new Authority("GROWER");
        if(appUser.getAuthorities().stream().anyMatch(authority1 -> authority1.equals(authority))){
            Product product = productMapper.toEntity(productDTO);
            product = productRepository.save(product);
            return productMapper.toDto(product);
        }
        else{
            throw new GrowerRightsException("Nu aveti drepturi de a adauga un produs");
        }
    }

    public ProductDTO update(Long id,ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        if(productRepository.findById(id).isPresent()) {
            Product compareProduct = productRepository.findById(id).get();
            Product product = productMapper.toEntity(productDTO);
            if(productDTO.getIdGrower().getIdAppUser().equals(compareProduct.getGrower_id().getIdAppUser())) {
                product = productRepository.save(product);
                return productMapper.toDto(product);
            }
            else {
                throw new GrowerRightsException("Nu poti modifica produsele altui producator");
            }
        }
        else {
            throw new NoDataFoundException("Product not found with id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        log.debug("Request to get all Products");
        return productRepository.findAll().stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        if(productRepository.findById(id).isPresent()) {
            return productMapper.toDto(productRepository.findById(id).get());
        }
        else{
            throw new NoDataFoundException("Product not found with id: " + id);
        }
    }

    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}

