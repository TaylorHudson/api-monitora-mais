package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonitoringScheduleDomainTest {

    @Test
    void shouldApprovePendingSchedule() {
        MonitoringScheduleDomain schedule = new MonitoringScheduleDomain();
        schedule.setStatus(MonitoringScheduleStatus.PENDING);

        boolean result = schedule.approve();

        assertTrue(result);
        assertEquals(MonitoringScheduleStatus.APPROVED, schedule.getStatus());
    }

    @Test
    void shouldNotApproveAlreadyApprovedSchedule() {
        MonitoringScheduleDomain schedule = new MonitoringScheduleDomain();
        schedule.setStatus(MonitoringScheduleStatus.APPROVED);

        boolean result = schedule.approve();

        assertFalse(result);
        assertEquals(MonitoringScheduleStatus.APPROVED, schedule.getStatus());
    }

    @Test
    void shouldDenyPendingSchedule() {
        MonitoringScheduleDomain schedule = new MonitoringScheduleDomain();
        schedule.setStatus(MonitoringScheduleStatus.PENDING);

        boolean result = schedule.deny();

        assertTrue(result);
        assertEquals(MonitoringScheduleStatus.DENIED, schedule.getStatus());
    }

    @Test
    void shouldNotDenyAlreadyDeniedSchedule() {
        MonitoringScheduleDomain schedule = new MonitoringScheduleDomain();
        schedule.setStatus(MonitoringScheduleStatus.DENIED);

        boolean result = schedule.deny();

        assertFalse(result);
        assertEquals(MonitoringScheduleStatus.DENIED, schedule.getStatus());
    }

}
