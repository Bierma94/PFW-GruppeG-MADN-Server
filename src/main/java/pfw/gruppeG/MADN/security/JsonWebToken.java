package pfw.gruppeG.MADN.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * JsonWebToken
 * Copyright (c) Jannes Bierma -All Rights Reserved.
 *
 * @author Jannes Bierma (jannes.bierma@stud.hs-emden-leer.de)
 * @version 1.0 - 21.10.2024
 */
public interface JsonWebToken {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(String username);

    long getExpiration(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}
