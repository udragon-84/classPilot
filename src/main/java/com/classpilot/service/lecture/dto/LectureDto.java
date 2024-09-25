package com.classpilot.service.lecture.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "수강 조회 Domain", name = "LectureDto")
public class LectureDto {

    @Schema(description = "강의 ID", example = "1")
    private long lectureId;

    @Schema(description = "강의명", example = "너나위 내집마련 기초반")
    private String lectureName;

    @Schema(description = "강의수강료", example = "100000")
    private int price;

    @Schema(description = "강사명", example = "너나위")
    private String instructorName;

    @Schema(description = "강의 수강 인원수", example = "10")
    private int maxStudents;

    @Schema(description = "현재 수강 인원수", example = "8")
    private int currentStudentCnt;

    @Schema(description = "강의 신청율", example = "4.537")
    private double enrollmentRate;
}
