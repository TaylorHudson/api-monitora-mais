package br.com.pj2.back.dataprovider.client;

import br.com.pj2.back.dataprovider.client.dto.AuthRequest;
import br.com.pj2.back.dataprovider.client.dto.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Suap", url = "${integration.suap.url}")
public interface SuapClient {
    @PostMapping("/api/jwt/obtain_token/")
    AuthResponse obtainToken(@RequestBody AuthRequest request);
}
