package com.classpilot.service.lecture.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "강의 등록 Domain", name = "LectureRegisterDto")
public class LectureRegisterDto {

    @Schema(description = "강사 memberId(member 테이블)", example = "1")
    @NotNull(message = "강사 Id는 필수 입력 정보입니다.")
    private Long memberId;

    @Schema(description = "강의명", example = "너나위 내집마련 기초반")
    @NotNull(message = "강의명 필수 입력 정보입니다.")
    private String lectureName;

    @Schema(description = "강의수강료", example = "100000")
    @NotNull(message = "강의수강료는 필수 입력 정보입니다.")
    private Integer price;

    @Schema(description = "강의 수강 인원수", example = "10")
    @NotNull(message = "강의 수강 인원수는 필수 입력 정보입니다.")
    @Min(value = 1, message = "강의 수강 인원수는 1명 이상이어야 합니다.")
    private Integer maxStudents;
}
