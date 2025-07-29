package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.PdfGeneratorGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfGeneratorUseCaseTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private PdfGeneratorGateway pdfGeneratorGateway;
    @InjectMocks
    private PdfGeneratorUseCase pdfGeneratorUseCase;

    @Test
    void shouldGeneratePdfWhenAuthorizationHeaderIsValid() {
        String authHeader = "Bearer token";
        String registration = "user123";
        File expectedFile = mock(File.class);

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(pdfGeneratorGateway.genaratePdf(registration)).thenReturn(expectedFile);

        File result = pdfGeneratorUseCase.execute(authHeader);

        assertEquals(expectedFile, result);
        verify(tokenGateway).extractSubjectFromAuthorization(authHeader);
        verify(pdfGeneratorGateway).genaratePdf(registration);
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        when(tokenGateway.extractSubjectFromAuthorization(null)).thenThrow(new RuntimeException("Invalid header"));

        assertThrows(RuntimeException.class, () -> pdfGeneratorUseCase.execute(null));
        verify(tokenGateway).extractSubjectFromAuthorization(null);
        verifyNoInteractions(pdfGeneratorGateway);
    }

}
