package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.*;
import br.com.pj2.back.entrypoint.api.dto.request.LoginRequest;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringRequest;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringUpdateRequest;
import br.com.pj2.back.entrypoint.api.dto.request.SubscribeStudentRequest;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MonitoringControllerTest {
    private static final String AUTH_HEADER = "Bearer token";
    private static final MonitoringRequest MONITORING_REQUEST = Instancio.create(MonitoringRequest.class);
    private static final MonitoringDomain MONITORING = Instancio.of(MonitoringDomain.class)
            .set(field(MonitoringDomain::getId), 1L)
            .set(field(MonitoringDomain::getName), "monitoring").create();

    @Mock
    private CreateMonitoringUseCase createMonitoringUseCase;
    @Mock
    private SubscribeStudentUseCase subscribeStudentUseCase;
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private FindAllMonitoringUseCase findAllMonitoringUseCase;
    @Mock
    private DeleteMonitoringByIdUseCase deleteMonitoringByIdUseCase;
    @Mock
    private UpdateMonitoringUseCase updateMonitoringUseCase;
    @Mock
    private FindMonitoringByIdUseCase findMonitoringByIdUseCase;
    @InjectMocks
    private MonitoringController monitoringController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(monitoringController).build();
    }

    @Test
    void shouldCreateMonitoringSuccessfully() throws Exception {
        when(createMonitoringUseCase.execute(any(MonitoringRequest.class), anyString())).thenReturn(MONITORING);

        mockMvc.perform(post("/monitoring/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(MONITORING_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("monitoring"));
    }

    @Test
        void shouldReturnBadRequestWhenMonitoringRequestIsInvalid() throws Exception {
            MonitoringRequest invalidRequest = new MonitoringRequest();

            mockMvc.perform(post("/monitoring/teachers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", AUTH_HEADER)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissingOnCreate() throws Exception {
            mockMvc.perform(post("/monitoring/teachers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(MONITORING_REQUEST)))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        void shouldDeleteMonitoringSuccessfully() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/monitoring/teachers/{id}", 1L)
                            .header("Authorization", AUTH_HEADER))
                    .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnAllMonitoringsForTeacher() throws Exception {
            when(findAllMonitoringUseCase.execute(anyString())).thenReturn(List.of(MONITORING));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/monitoring/teachers/me")
                            .header("Authorization", AUTH_HEADER))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("monitoring"));
        }

        @Test
        void shouldUpdateMonitoringSuccessfully() throws Exception {
            var updateRequest = Instancio.create(MonitoringUpdateRequest.class);
            var updatedDomain = Instancio.of(MonitoringDomain.class)
                    .set(field(MonitoringDomain::getId), 1L)
                    .set(field(MonitoringDomain::getName), "Updated Monitoring")
                    .create();
            when(updateMonitoringUseCase.execute(anyLong(), any(MonitoringUpdateRequest.class), anyString())).thenReturn(updatedDomain);

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/monitoring/teachers/{id}", 1L)
                            .header("Authorization", AUTH_HEADER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Updated Monitoring"));
        }

        @Test
        void shouldReturnMonitoringById() throws Exception {
            when(findMonitoringByIdUseCase.execute(anyLong(), anyString())).thenReturn(MONITORING);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/monitoring/teachers/{id}", 1L)
                            .header("Authorization", AUTH_HEADER))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("monitoring"));
        }

        @Test
        void shouldSubscribeStudentSuccessfully() throws Exception {
            var subscribeRequest = Instancio.create(SubscribeStudentRequest.class);

            mockMvc.perform(post("/monitoring/students/subscribe")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", AUTH_HEADER)
                            .content(objectMapper.writeValueAsString(subscribeRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldReturnAllMonitoringsForStudent() throws Exception {
            var myMonitoringDomain = Instancio.of(MonitoringDomain.class)
                    .set(field(MonitoringDomain::getId), 2L)
                    .set(field(MonitoringDomain::getName), "Student Monitoring")
                    .create();
            when(tokenGateway.extractSubjectFromAuthorization(anyString())).thenReturn("202300123456");
            when(monitoringGateway.findAllByStudentRegistration(anyString())).thenReturn(List.of(myMonitoringDomain));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/monitoring/students/me")
                            .header("Authorization", AUTH_HEADER))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(2))
                    .andExpect(jsonPath("$[0].name").value("Student Monitoring"));
        }

}
