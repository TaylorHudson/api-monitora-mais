package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.FindStartedMonitoringSessionUseCase;
import br.com.pj2.back.core.usecase.FinishMonitoringSessionUseCase;
import br.com.pj2.back.core.usecase.StartMonitoringSessionUseCase;
import br.com.pj2.back.entrypoint.api.dto.request.FinishMonitoringSessionRequest;
import br.com.pj2.back.entrypoint.api.dto.request.StartMonitoringSessionRequest;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringSessionStartedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.instancio.Select.field;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MonitoringSessionControllerTest {
    private static final String AUTH_HEADER = "Bearer token";
    private static final String REGISTRATION = "202300123456";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private StartMonitoringSessionUseCase startSessionUseCase;
    @Mock
    private FinishMonitoringSessionUseCase finishSessionUseCase;
    @Mock
    private FindStartedMonitoringSessionUseCase findStartedMonitoringSessionUseCase;
    @InjectMocks
    private MonitoringSessionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnStartedSessionForStudent() throws Exception {
        var response = Instancio.of(MonitoringSessionStartedResponse.class)
                .set(field(MonitoringSessionStartedResponse::getMonitoringScheduleId), 1L)
                .create();
        when(tokenGateway.extractSubjectFromAuthorization(AUTH_HEADER)).thenReturn(REGISTRATION);
        when(findStartedMonitoringSessionUseCase.execute(REGISTRATION)).thenReturn(response);

        mockMvc.perform(get("/monitoring/sessions/students/started")
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monitoringScheduleId").value(1));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissingOnFindStartedSession() throws Exception {
        mockMvc.perform(get("/monitoring/sessions/students/started"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldStartSessionSuccessfully() throws Exception {
        var request = Instancio.create(StartMonitoringSessionRequest.class);
        when(tokenGateway.extractSubjectFromAuthorization(AUTH_HEADER)).thenReturn(REGISTRATION);
        doNothing().when(startSessionUseCase).execute(request.getMonitoringScheduleId(), REGISTRATION);

        mockMvc.perform(post("/monitoring/sessions/students/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestWhenStartSessionRequestIsInvalid() throws Exception {
        var invalidRequest = new StartMonitoringSessionRequest();

        mockMvc.perform(post("/monitoring/sessions/students/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissingOnStartSession() throws Exception {
        var request = Instancio.create(StartMonitoringSessionRequest.class);

        mockMvc.perform(post("/monitoring/sessions/students/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldFinishSessionSuccessfully() throws Exception {
        var request = Instancio.create(FinishMonitoringSessionRequest.class);
        when(tokenGateway.extractSubjectFromAuthorization(AUTH_HEADER)).thenReturn(REGISTRATION);
        doNothing().when(finishSessionUseCase).execute(request.getMonitoringScheduleId(), request.getTopics(), REGISTRATION);

        mockMvc.perform(post("/monitoring/sessions/students/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestWhenFinishSessionRequestIsInvalid() throws Exception {
        var invalidRequest = new FinishMonitoringSessionRequest();

        mockMvc.perform(post("/monitoring/sessions/students/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissingOnFinishSession() throws Exception {
        var request = Instancio.create(FinishMonitoringSessionRequest.class);

        mockMvc.perform(post("/monitoring/sessions/students/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }
}
