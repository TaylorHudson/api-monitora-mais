package br.com.pj2.back.dataprovider.adapter;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.UnauthorizedException;
import br.com.pj2.back.core.gateway.TokenGateway;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenAdapter implements TokenGateway {
    private static final String IS_ACCESS_TOKEN = "isAccessToken";

    @Value("${security.jwt.key}")
    private String key;
    @Value("${security.jwt.access-expires-in}")
    private Long accessExpiresIn;
    @Value("${security.jwt.refresh-expires-in}")
    private Long refreshExpiresIn;

    @Override
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractSubjectFromAuthorization(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        return extractSubject(token);
    }

    @Override
    public boolean isTokenValid(String token, String subject) {
        return subject.equals(extractSubject(token)) && !isTokenExpired(token);
    }

    @Override
    public boolean isAccessToken(String token) {
        return extractAllClaims(token).get(IS_ACCESS_TOKEN, Boolean.class);
    }

    @Override
    public String generateAccessToken(String subject) {
        final Map<String, Object> claims = Map.of(IS_ACCESS_TOKEN, true);
        return buildToken(claims, subject, accessExpiresIn);
    }

    @Override
    public String generateRefreshToken(String subject) {
        final Map<String, Object> claims = Map.of(IS_ACCESS_TOKEN, false);
        return buildToken(claims, subject, refreshExpiresIn);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
        final var claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String buildToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }
}
