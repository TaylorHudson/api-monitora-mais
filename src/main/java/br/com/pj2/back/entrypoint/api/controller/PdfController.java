package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.usecase.PdfGeneratorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@Tag(name = "PDF com carga horaria mensal")
@RestController
@RequestMapping("/pdf/month-workloads")
@RequiredArgsConstructor
public class PdfController {

    private final PdfGeneratorUseCase pdfGeneratorUseCase;

    @Operation(summary = "Baixar PDF com carga horaria mensal")
    @PostMapping
    public ResponseEntity<byte[]> generateMonthlyTimeLoadPdf(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        File pdf = pdfGeneratorUseCase.execute(authorizationHeader);
        try {
            byte[] content = Files.readAllBytes(pdf.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=declaracao.pdf")
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(content);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
