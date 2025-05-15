package br.com.pj2.back.core.gateway;

public interface TokenGateway {
    String extractSubject(String token);
    String extractSubjectFromAuthorization(String authHeader);
    boolean isTokenValid(String token, String subject);
    boolean isAccessToken(String token);
    String generateAccessToken(String subject);
    String generateRefreshToken(String subject);
}