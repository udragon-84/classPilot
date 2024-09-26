package com.classpilot.service.enrollment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "강의 신청 등록 Domain", name = "EnrollmentRegisterDto")
public class EnrollmentRegisterDto {

    @Schema(description = "회원 ID", example = "1")
    @NotNull(message = "회원 Id는 필수 입력 정보입니다.")
    private Long memberId;

    @Schema(description = "강의 ID", example = "1")
    @NotNull(message = "강의 Id는 필수 입력 정보입니다.")
    private Long lectureId;

}
