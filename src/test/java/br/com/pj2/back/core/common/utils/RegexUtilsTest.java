package br.com.pj2.back.core.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilsTest {

    @Test
    void shouldReturnTrueForValidStudentRegistration() {
        assertTrue(RegexUtils.isStudentRegistration("202300123456"));
    }

    @Test
    void shouldReturnFalseForInvalidStudentRegistration() {
        assertFalse(RegexUtils.isStudentRegistration("1234567"));
        assertFalse(RegexUtils.isStudentRegistration("abcd12345678"));
    }

    @Test
    void shouldReturnTrueForValidTeacherRegistration() {
        assertTrue(RegexUtils.isTeacherRegistration("1234567"));
    }

    @Test
    void shouldReturnFalseForInvalidTeacherRegistration() {
        assertFalse(RegexUtils.isTeacherRegistration("202300123456"));
        assertFalse(RegexUtils.isTeacherRegistration("abcdefg"));
    }
}
