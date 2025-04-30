package br.com.pj2.back.core.gateway;

public interface AuthGateway {
    void validateCredentials(String registration, String password);
}
