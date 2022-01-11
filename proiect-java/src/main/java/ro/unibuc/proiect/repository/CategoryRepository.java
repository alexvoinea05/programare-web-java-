package ro.unibuc.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.proiect.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
