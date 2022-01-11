package ro.unibuc.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.proiect.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
