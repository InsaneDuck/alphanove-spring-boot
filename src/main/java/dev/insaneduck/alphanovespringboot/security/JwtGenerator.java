package dev.insaneduck.alphanovespringboot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {
    public static final long JWT_EXPIRATION = 1000000000;
    public static final String USER_TYPE = "usertype";
    //todo fix token
    SecretKey key = Jwts.SIG.HS256.key().build();

    //todo fix deprecated
    public String generateToken(Authentication authentication, String userType) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expriryDate = new Date(currentDate.getTime() + JWT_EXPIRATION);


        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expriryDate)
                .signWith(key)
                .claim(USER_TYPE, userType).compact();

    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key).build().parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public String getUserTypeFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get(USER_TYPE).toString();
    }

    public boolean validateToken(String jwsToken) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwsToken);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is invalid");
        }
    }
}
