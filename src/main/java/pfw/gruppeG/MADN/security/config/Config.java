package pfw.gruppeG.MADN.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Config
 * Creates a BCryptPasswordEncoder Bean and an AuthenticationProvider Bean
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class Config {

    /**
     * BCryptPasswordEncoder Bean
     * @return BCryptPasswordEncoder Bean
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationProvider Bean
     * @param userDetailsService UserDetailsService
     * @return AuthenticationProvider Bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authProvider;
    }

    /**
     * AuthenticationManager Bean
     * @param config AuthenticationConfiguration
     * @return AuthenticationManager Bean
     * @throws Exception if the AuthenticationManager cannot be created
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
