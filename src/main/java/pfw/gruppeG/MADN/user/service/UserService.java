package pfw.gruppeG.MADN.user.service;


import lombok.RequiredArgsConstructor;
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
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceAPI {

    private final UserRepository userRepository;
    private final UserWrapper userWrapper;



    @Override
    public Boolean create(String username, String password, String email, String role)
            throws UserException {
       UserRole userRole = UserRole.valueOf(role);
        if(existsByUsername(username)) {
            throw new UserException("User already registered");
        }
        if (userRole == null) {
            throw new UserException("Role not found");
        }
         User user = User.builder()
                 .username(username)
                 .role(userRole)
                 .password(password)
                 .email(email)
                 .build();
            return save(user);
    }

    @Override
    public Map<String,String> getUser(Long id) throws UserException {
        return userRepository.findById(id)
                 .map(userWrapper::map)
                .orElseThrow(() -> new UserException("User not found with id: " + id ));
    }

    @Override
    public Map<String,String> getUser(String username) throws UserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));
        return userWrapper.map(user);
    }

    @Override
    public Boolean update(Long id, String username, String password, String email, String role)
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
        return save(user);
    }

    @Override
    public Boolean delete(Long id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
        userRepository.delete(user);
        return true;
    }

    private Boolean save(User user) {
        userRepository.save(user);
        return true;
    }

     private Boolean existsByUsername(String username) {
         return userRepository.existsByUsername(username);
     }

}
