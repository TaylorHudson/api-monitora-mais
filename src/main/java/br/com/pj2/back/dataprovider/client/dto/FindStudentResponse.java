package br.com.pj2.back.dataprovider.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FindStudentResponse {
    private int count;
    private List<UserResponse> results;

    @Builder
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserResponse {
        @JsonProperty("uuid")
        private String id;
        @JsonProperty("nome")
        private String name;
        @JsonProperty("matricula")
        private String registration;
        @JsonProperty("situacao")
        private String situation;
        @JsonProperty("curso")
        private CourseResponse course;

    }

    @Builder
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CourseResponse {
        @JsonProperty("nome")
        private String name;
    }
}
