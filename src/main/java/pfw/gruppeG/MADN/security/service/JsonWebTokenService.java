package pfw.gruppeG.MADN.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pfw.gruppeG.MADN.security.JsonWebToken;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * JsonWebTokenService
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@Service
public class JsonWebTokenService implements JsonWebToken {

    /**The secret key for the token*/
    @Value("${jwt.secret}")
    private String secret;
    /**The expiration time of the token*/
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Extracts the username from the token
     * @param token json web token
     * @return the username
     */
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the claim from the token
     * @param token json web token
     * @param claimsResolver function to resolve the claims
     * @return the claim as an object
     * @param <T> the type of the object
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a token for the given user
     * @return the generated token
     */
    @Override
    public String generateToken(String username) {
        return
                Jwts.builder()
                        .claim("name", username)
                        .setSubject(username)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                        .compact();
    }

    /**
     * Extracts the expiration date from the token
     * @param token json web token
     * @return the expiration
     */
    @Override
    public long getExpiration(String token) {
        return expiration;
    }

    /**
     * Validates the token
     * @param token json web token
     * @param userDetails the user details
     * @return if the token is valid true, else false
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is expired
     * @param token json web token
     * @return if the token is expired true, else false
     */
    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts all claims from the token
     * @param token json web token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the expiration date from the token
     * @param token json web token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * Returns the sign in key
     * @return the sign in key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
