package ro.unibuc.proiect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.unibuc.proiect.domain.CartOrderDetails;

import java.util.Optional;

@Repository
public interface CartOrderDetailsRepository extends JpaRepository<CartOrderDetails,Long> {

    @Query(
            "select c from CartOrderDetails c where c.idAppUser.idAppUser = :idAppUser and (c.statusCommand !=:statusCommand " +
                    "and c.statusCommand !=:statusCommand1)"
    )
    Optional<CartOrderDetails> findActiveCartOrderDetailsForCurrentAppUser(
            @Param("idAppUser") Long idAppUser,
            @Param("statusCommand") String statusCommand,
            @Param("statusCommand1") String statusCommand1
    );
}
