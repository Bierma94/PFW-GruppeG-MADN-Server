package pfw.gruppeG.MADN.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pfw.gruppeG.MADN.user.UserServiceAPI;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * CustomUserDetailService
 * Implements the UserDetailsService interface to load user information from the UserServiceAPI
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    /** UserServiceAPI */
    private final UserServiceAPI userServiceAPI;
    /** Role Prefix */
    private final String ROLE_PREFIX = "ROLE_";

    /**
     * Loads the user by username
     * @param username the username of the user
     * @return the UserDetails object
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Map<String, String> user = userServiceAPI.getUser(username);
            return User.builder()
                    .username(user.get("username"))
                    .password(user.get("password"))
                    .authorities(createAuthority(user.get("role")))
                    .build();
        } catch (Exception e) {
            log.error("User not found" + e.getMessage());
            throw new UsernameNotFoundException("User not found");
        }
    }

    /**
     * Creates the authority for the user based on the role
     * Add the ROLE_PREFIX to the role
     * If the role is ADMIN, add the USER role as well
     * @param role the role of the user
     * @return the authority
     */
    private List<GrantedAuthority> createAuthority(String role) {
        if (Objects.equals(role, "ADMIN")) {
            return List.of((GrantedAuthority) () -> ROLE_PREFIX + "ADMIN",
                    (GrantedAuthority) () -> ROLE_PREFIX + "USER");
        } else {
            return List.of((GrantedAuthority) () -> ROLE_PREFIX + "USER");
        }
    }
}
