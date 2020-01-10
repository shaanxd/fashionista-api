package com.fashionista.api.security;

import com.fashionista.api.entities.User;
import com.fashionista.api.exceptions.GenericException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.fashionista.api.constants.SecurityConstants.JWT_SECRET;
import static com.fashionista.api.constants.SecurityConstants.VALID_DURATION;

@Component
public class JwtTokenProvider {
    public String generateToken(User user) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + VALID_DURATION);

        return Jwts.builder()
                .setSubject(user.getId())
                .claim("id", user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new GenericException("Invalid Token Signature.", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException ex) {
            throw new GenericException("Token is altered.", HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException ex) {
            throw new GenericException("Token has expired.", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            throw new GenericException("Unsupported Token.", HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException ex) {
            throw new GenericException("Token claims string is empty.", HttpStatus.UNAUTHORIZED);
        }
    }

    String extractUserFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

        return claims.get("id", String.class);
    }
}
