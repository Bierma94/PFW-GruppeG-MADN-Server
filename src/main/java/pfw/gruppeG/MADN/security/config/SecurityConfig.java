package pfw.gruppeG.MADN.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SecurityConfig
 * Configures the security for the application
 * Modifies the security filter chain
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /** AUTH_WHITELIST permit all */
    private final static String[] AUTH_WHITELIST = {
            "/api/login/**",
            "/api/users/**",
            "/actuator/**",
    };

    /** USER_WHITELIST permit all that has role USER */
    private final static String[] USER_WHITELIST = {
            "/user/**",
    };
    /** ADMIN_WHITELIST permit all that has role ADMIN */
    private final static String[] ADMIN_WHITELIST = {
            "/admin/**",
    };

    /** The Authentication Provider */
    private final AuthenticationProvider authenticationProvider;

    /** The JwtAuthenticationFilter */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain
     * @param http HttpSecurity object to configure
     * @return the security filter chain
     * @throws Exception if the security filter chain cannot be created
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(USER_WHITELIST).hasRole("USER")
                .requestMatchers(ADMIN_WHITELIST).hasRole("ADMIN")
                .anyRequest().authenticated())
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfiguration()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

    /**
     * Configures the Cors Configuration
     * @return the Cors Configuration Source
     */
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("**"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
