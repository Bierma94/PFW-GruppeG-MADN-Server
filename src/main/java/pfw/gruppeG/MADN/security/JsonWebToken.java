package pfw.gruppeG.MADN.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * JsonWebToken
 * Interface for the JsonWebTokenService
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
public interface JsonWebToken {
    /**
     * Extracts the username from the token
     * @param token JsonWebToken
     * @return String username of the user
     */
    String extractUsername(String token);

    /**
     * Extracts the claim from the token
     * @param token JsonWebToken
     * @param claimsResolver Function<Claims, T>
     * @return Object
     * @param <T> Object
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    /**
     * Generates a token for the given user
     * @param username username of the user
     * @return String JsonWebToken
     */
    String generateToken(String username);
    /**
     * Extracts the expiration date from the token
     * @param token JsonWebToken
     * @return long expiration date
     */
    long getExpiration(String token);

    /**
     * Validates the token
     * @param token JsonWebToken
     * @param userDetails  UserDetails
     * @return boolean
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Checks if the token is expired
     * @param token JsonWebToken
     * @return true if the token is expired, false if not
     */
    boolean isTokenExpired(String token);
}
