package pfw.gruppeG.MADN.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pfw.gruppeG.MADN.user.model.User;

import java.util.Optional;

/**
 * UserRepository
 * Repository for the User
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by username
     * @param username the username of the user
     * @return the user
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user exists by username
     * @param username username of the user
     * @return true if the user exists
     */
    Boolean existsByUsername(String username);

    /**
     * Check if a user exists by email
     * @param email email of the user
     * @return true if the user exists
     */
    Boolean existsByEmail(String email);
}
