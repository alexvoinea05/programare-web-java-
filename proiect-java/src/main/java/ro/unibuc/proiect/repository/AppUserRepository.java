package ro.unibuc.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.proiect.domain.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
