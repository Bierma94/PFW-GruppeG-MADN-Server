package pfw.gruppeG.MADN.security.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfw.gruppeG.MADN.user.UserServiceAPI;
import pfw.gruppeG.MADN.user.exception.UserException;

import java.util.Map;

/**
 * Service for authenticating users
 * Registers and authenticates users
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    /** The UserService */
    private final UserServiceAPI userService;
    /** The JsonWebTokenService */
    private final JsonWebTokenService jwtService;
    /** The AuthenticationManager */
    private final AuthenticationManager authenticationManager;


    /**
     * Authenticates a user
     * @param username username of the user
     * @param password password of the user
     * @return String JsonWebToken
     */
    public String authenticate(String username, String password) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        try {
           Map<String,String> user = userService.getUser(username);
            return jwtService.generateToken(user.get("username"));
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

}
