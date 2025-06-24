package br.com.pj2.back.core.domain.enumerated;

import br.com.pj2.back.core.exception.BadRequestException;
import br.com.pj2.back.core.exception.StandardException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum MonitoringScheduleStatus {
    PENDING("Pendente"),
    APPROVED("Aprovado"),
    DENIED("Negado");

    private final String description;

    public static MonitoringScheduleStatus fromString(String status) {
        return Arrays.stream(MonitoringScheduleStatus.values())
            .filter(s -> s.name().equalsIgnoreCase(status))
            .findFirst()
            .orElseThrow(() ->
                    new BadRequestException(
                            ErrorCode.INVALID_ENUM_NAME,
                            List.of(StandardException.FieldError.of("status", "Os valores válidos são: " + getValidValues()))
                    )
            );
    }

    public static String getValidValues() {
        return Arrays.stream(MonitoringScheduleStatus.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
