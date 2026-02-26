package tech.origem.payment.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.origem.payment.auth.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
