package com.ecommerce.auth_service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.*;

/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JWTUtil {

    // Secret key used for signing the JWT (must be at least 256 bits for HS256)
    private static final String SECRET_KEY = "my-super-secret-key-123456789012345";

    // Convert the secret string into a cryptographic key
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts any claim using a custom resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver the resolver function to apply
     * @param <T> the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses and extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return all claims
     * @throws JwtException if token is invalid
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a new JWT token for the given user.
     *
     * @param userDetails the user details (typically username and roles)
     * @return the generated JWT token
     */
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates the JWT token with given claims and subject.
     *
     * @param claims the extra data to include in the token
     * @param subject the subject (typically username)
     * @return the JWT token
     */
    private static String createToken(Map<String, Object> claims, String subject) {
        // Token validity: 10 hours
        long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the JWT token by checking username and expiration.
     *
     * @param token the JWT token
     * @param userDetails the user details for comparison
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
