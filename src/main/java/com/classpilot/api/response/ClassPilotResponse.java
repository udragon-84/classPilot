package com.classpilot.api.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
@Schema(description = "월급쟁이부자들 수강신청 정보 응답 객체", name = "ClassPilotResponse<T>")
public class ClassPilotResponse<T> {

    @Schema(description = "응답 성공 여부", example = "true")
    private final Boolean result;

    @Schema(description = "응답 HttpStatus", example = "Ok")
    private final HttpStatus httpStatus;

    @Schema(description = "응답 메시지", example = "success")
    private final String message;

    @Schema(description = "응답 객체 정보 (T Type Parameter)", example = "json Object")
    private final T data;

    public ClassPilotResponse(T data) {
        this(Boolean.TRUE, HttpStatus.OK, "success", data);
    }
}
