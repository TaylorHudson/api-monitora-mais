package br.com.pj2.back.dataprovider.client;

import br.com.pj2.back.dataprovider.client.config.FeignConfig;
import br.com.pj2.back.dataprovider.client.dto.AuthRequest;
import br.com.pj2.back.dataprovider.client.dto.AuthResponse;
import br.com.pj2.back.dataprovider.client.dto.FindStudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Suap", url = "${integration.suap.url}", configuration = FeignConfig.class)
public interface SuapClient {
    @PostMapping("/api/jwt/obtain_token/")
    AuthResponse obtainToken(@RequestBody AuthRequest request);

    @GetMapping("/api/ensino/alunos/v1/")
    FindStudentResponse findStudent(@RequestHeader("Authorization") String token, @RequestParam("search") String search);
}
