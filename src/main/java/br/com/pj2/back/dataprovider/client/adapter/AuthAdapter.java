package br.com.pj2.back.dataprovider.client.adapter;

import br.com.pj2.back.core.gateway.AuthGateway;
import br.com.pj2.back.dataprovider.client.SuapClient;
import br.com.pj2.back.dataprovider.client.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthAdapter implements AuthGateway {
    private final SuapClient suapClient;

    @Override
    public void validateCredentials(String registration, String password) {
        suapClient.obtainToken(new AuthRequest(registration, password));
    }
}
