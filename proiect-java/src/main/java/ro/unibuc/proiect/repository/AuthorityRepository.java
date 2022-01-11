package ro.unibuc.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.proiect.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
