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
import pfw.gruppeG.MADN.user.exception.UserException;

import java.util.List;
import java.util.Map;

/**
 * CustomUserDetailService
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserServiceAPI userServiceAPI;
    private final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Map<String, String> user = userServiceAPI.getUser(username);
            return User.builder()
                    .username(user.get("username"))
                    .password(user.get("password"))
                    .authorities(createAuthority(user.get("role")))
                    .build();
        } catch (UserException e) {
            log.error("User not found" + e.getMessage());
            throw new UsernameNotFoundException("User not found");
        }
    }

    private List<GrantedAuthority> createAuthority(String role) {
        if (role == "ADMIN") {
            return List.of((GrantedAuthority) () -> ROLE_PREFIX + "ADMIN",
                    (GrantedAuthority) () -> ROLE_PREFIX + "USER");
        } else {
            return List.of((GrantedAuthority) () -> ROLE_PREFIX + "USER");
        }
    }
}
