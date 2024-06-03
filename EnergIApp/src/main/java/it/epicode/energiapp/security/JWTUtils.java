package it.epicode.energiapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secretKey;

    public String createToken(User user) {
        return Jwts.builder()
                .issuedAt(
                        new Date(
                                System.currentTimeMillis()))
                .expiration(
                        new Date(
                                System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(
                        String.valueOf(
                                user.getId()))
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build().parse(token);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Token is not valid.");
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token no longer valid, date has expired");
        } catch (Exception e) {
            throw new UnauthorizedException("Ongoing issues with token, please try to login again.");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build().parseSignedClaims(token).getPayload().getSubject();
    }
}