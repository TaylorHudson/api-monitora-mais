package br.com.pj2.back.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDomainTest {

    @Test
    void shouldSubtractMissingWorkload() {
        StudentDomain student = StudentDomain.builder()
                .missingWeeklyWorkload(600)
                .build();

        int durationInMinutes = 120;
        student.subtractMissingWorkload(durationInMinutes);
        assertEquals(480, student.getMissingWeeklyWorkload());
    }

}
