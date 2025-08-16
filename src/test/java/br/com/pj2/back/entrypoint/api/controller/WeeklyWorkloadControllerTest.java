package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.usecase.FindMissingWorkloadUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WeeklyWorkloadControllerTest {

    @Mock
    private FindMissingWorkloadUseCase findMissingWorkloadUseCase;
    @InjectMocks
    private WeeklyWorkloadController weeklyWorkloadController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(weeklyWorkloadController).build();
    }

    @Test
    void shouldReturnMissingWeeklyWorkloadWhenAuthorizationHeaderIsValid() throws Exception {
        when(findMissingWorkloadUseCase.execute(anyString())).thenReturn(LocalTime.of(10, 0));
        mockMvc.perform(get("/weekly-workloads/missing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissingOnMissingWeeklyWorkload() throws Exception {
        mockMvc.perform(get("/weekly-workloads/missing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
