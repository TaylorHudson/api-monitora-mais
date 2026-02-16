package br.com.pj2.back.core.gateway;

import br.com.pj2.back.dataprovider.client.dto.FindStudentResponse;

public interface AuthGateway {
    void validateCredentials(String registration, String password);
    FindStudentResponse.UserResponse checkIfStudentExists(String registration);
}
