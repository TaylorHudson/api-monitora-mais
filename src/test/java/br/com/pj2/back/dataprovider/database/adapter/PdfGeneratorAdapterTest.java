package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfGeneratorAdapterTest {

    @Mock
    private StudentAdapter studentAdapter;

    @InjectMocks
    private PdfGeneratorAdapter pdfGeneratorAdapter;

    @Test
    void generatePdfSuccessWhenMonitorFulfillsFullWorkload() {
        StudentDomain studentDomain = builderStudendDomain();
        when(studentAdapter.findByRegistration(studentDomain.getRegistration())).thenReturn(studentDomain);

        File pdfFile = pdfGeneratorAdapter.genaratePdf("123");

        assertNotNull(pdfFile);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);

        pdfFile.deleteOnExit();
        verify(studentAdapter, times(1)).findByRegistration("123");
    }

    @Test
    void generatePdfSuccessWhenMonitorDoesNotFulfillFullWorkload() {
        StudentDomain studentDomain = builderStudendDomain();
        studentDomain.subtractMissingWorkload(35);
        when(studentAdapter.findByRegistration(studentDomain.getRegistration())).thenReturn(studentDomain);

        File pdfFile = pdfGeneratorAdapter.genaratePdf("123");

        assertNotNull(pdfFile);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);

        pdfFile.deleteOnExit();
        verify(studentAdapter, times(1)).findByRegistration("123");
    }

    @Test
    void mustErrorGeneratePdf(){
        when(studentAdapter.findByRegistration("789")).thenThrow(new RuntimeException("Erro de banco"));

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> pdfGeneratorAdapter.genaratePdf("789")
        );

        assertEquals(ErrorCode.SERVER_ERROR.getErrorCode(), ex.getErrorCode().getErrorCode());
        assertEquals(ErrorCode.SERVER_ERROR.getMessage(), ex.getErrorCode().getMessage());

    }

    private StudentDomain builderStudendDomain() {

        return StudentDomain.builder()
                .email("teste")
                .role(Role.STUDENT)
                .registration("123")
                .name("teste")
                .missingWeeklyWorkload(0).build();
    }

}