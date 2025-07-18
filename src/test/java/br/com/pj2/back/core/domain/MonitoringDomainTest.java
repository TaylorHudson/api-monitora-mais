package br.com.pj2.back.core.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MonitoringDomainTest {

    @Test
    void shouldSubscribeStudent() {
        MonitoringDomain monitoring = MonitoringDomain.builder()
                .students(new ArrayList<>())
                .build();

        String studentRegistration = "student1";
        monitoring.subscribeStudent(studentRegistration);

        assertTrue(monitoring.getStudents().contains(studentRegistration));
    }

    @Test
    void shouldNotSubscribeSameStudentTwice() {
        MonitoringDomain monitoring = MonitoringDomain.builder()
                .students(new ArrayList<>())
                .build();

        String studentRegistration = "student1";
        monitoring.subscribeStudent(studentRegistration);
        monitoring.subscribeStudent(studentRegistration);

        assertEquals(1, monitoring.getStudents().size());
        assertTrue(monitoring.getStudents().contains(studentRegistration));
    }

    @Test
    void shouldSubscribeStudentWhenStudentsListIsNull() {
        MonitoringDomain monitoring = MonitoringDomain.builder()
                .students(null)
                .build();

        String studentRegistration = "student1";
        monitoring.subscribeStudent(studentRegistration);

        assertNotNull(monitoring.getStudents());
        assertTrue(monitoring.getStudents().contains(studentRegistration));
    }

    @Test
    void shouldContainStudent() {
        MonitoringDomain monitoring = MonitoringDomain.builder()
                .students(new ArrayList<>())
                .build();

        String studentRegistration = "student1";
        monitoring.subscribeStudent(studentRegistration);

        assertTrue(monitoring.containsStudent(studentRegistration));
    }

    @Test
    void shouldNotContainNonSubscribedStudent() {
        MonitoringDomain monitoring = MonitoringDomain.builder()
                .students(new ArrayList<>())
                .build();

        String studentRegistration = "student1";

        assertFalse(monitoring.containsStudent(studentRegistration));
    }
}
