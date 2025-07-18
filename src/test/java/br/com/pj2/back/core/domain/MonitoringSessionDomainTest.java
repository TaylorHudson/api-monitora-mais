package br.com.pj2.back.core.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonitoringSessionDomainTest {

    @Test
    void shouldStartSession() {
        MonitoringSessionDomain session = new MonitoringSessionDomain("Monitor1", "Monitoring1", 1L);
        session.startSession();

        assertNotNull(session.getStartTime());
        assertTrue(session.isStarted());
    }

    @Test
    void shouldFinishSession() {
        MonitoringSessionDomain session = new MonitoringSessionDomain("Monitor1", "Monitoring1", 1L);
        session.startSession();

        session.finishSession(List.of("Topic1", "Topic2"));

        assertNotNull(session.getEndTime());
        assertFalse(session.isStarted());
        assertEquals(2, session.getTopics().size());
        assertTrue(session.getTopics().contains("Topic1"));
        assertTrue(session.getTopics().contains("Topic2"));
    }

}
