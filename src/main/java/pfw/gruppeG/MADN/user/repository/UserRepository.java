package pfw.gruppeG.MADN.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pfw.gruppeG.MADN.user.model.User;

import java.util.Optional;

/**
 * UserRepository
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
