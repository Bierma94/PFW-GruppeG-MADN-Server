package pfw.gruppeG.MADN.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfw.gruppeG.MADN.user.UserServiceAPI;
import pfw.gruppeG.MADN.user.exception.UserException;
import pfw.gruppeG.MADN.user.model.User;
import pfw.gruppeG.MADN.user.model.UserRole;
import pfw.gruppeG.MADN.user.repository.UserRepository;
import pfw.gruppeG.MADN.user.wrapper.UserWrapper;

import java.util.Map;

/**
 * UserService
 * Implementation of the UserServiceAPI
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceAPI {

    /** The UserRepository */
    private final UserRepository userRepository;
    /** The UserWrapper */
    private final UserWrapper userWrapper;
    /** The PasswordEncoder */
    private final PasswordEncoder passwordEncoder;


    /**
     * Creates a new user
     * @param username username of the user
     * @param password password of the user (encoded)
     * @param email email of the user
     * @param role role of the user
     * @return Map<String, String> user
     * @throws UserException if user already exists
     */
    @Override
    public Map<String, String> create(String username, String password, String email, String role)
            throws UserException {
       UserRole userRole = UserRole.valueOf(role);
        if(existsByUsername(username)) {
            throw new UserException("User already registered");
        }
        if(existsByEmail(email)) {
            throw new UserException("Email already registered");
        }
        if (userRole == null) {
            throw new UserException("Role not found");
        }
         User user = User.builder()
                 .username(username)
                 .role(userRole)
                 .password(passwordEncoder.encode(password))
                 .email(email)
                 .build();
             save(user);
             return userWrapper.map(user);
    }

    /**
     * Gets a user by its id
     * @param id id of the user
     * @return Map<String, String> user
     * @throws UserException if user not found
     */
    @Override
    public Map<String,String> getUser(Long id) throws UserException {
        return userRepository.findById(id)
                 .map(userWrapper::map)
                .orElseThrow(() -> new UserException("User not found with id: " + id ));
    }

    /**
     * Gets a user by its username
     * @param username username of the user
     * @return Map<String, String> user
     * @throws UserException if user not found
     */
    @Override
    public Map<String,String> getUser(String username) throws UserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));
        return userWrapper.map(user);
    }

    /**
     * Updates a user
     * @param id id of the user
     * @param username username of the user
     * @param password password of the user
     * @param email email of the user
     * @param role role of the user
     * @throws UserException if user not found or role not found
     */
    @Override
    public void update(Long id, String username, String password, String email, String role)
            throws UserException {
        UserRole userRole = UserRole.valueOf(role);
        if(userRole == null) {
            throw new UserException("Role not found");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        if(existsByUsername(username)) {
            throw new UserException("Username already registered");
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(userRole);
         save(user);
    }

    /**
     * Deletes a user by its id
     * @param id id of the user
     * @throws UserException if user not found
     */
    @Override
    public void delete(Long id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        userRepository.delete(user);
    }

    /**
     * Saves a user
     * @param user user to save
     */
    private void save(User user) {
        userRepository.save(user);
    }

    /**
     * Checks if a user exists by its username
     * @param username username of the user
     * @return true if user exists
     */
     private Boolean existsByUsername(String username) {
         return userRepository.existsByUsername(username);
     }

    /**
     * Checks if a user exists by its email
     * @param email email of the user
     * @return true if user exists
     */
     private Boolean existsByEmail(String email) {
         return userRepository.existsByEmail(email);
     }

}
