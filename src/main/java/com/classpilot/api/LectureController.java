package com.classpilot.api;

import com.classpilot.api.response.ClassPilotResponse;
import com.classpilot.service.lecture.LectureService;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureRegisterDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import com.classpilot.service.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/lecture")
@Tag(name = "강의 조회 및 등록 Api", description = "월급쟁이부자들 강의 조회 및 등록 Api 명세")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @Operation(summary = "강의 정보 조회 Api 정렬 기준 (최근 등록순:createdAt(default) | 신청자 많은 순:currentStudentCnt | 신청률 높은 순: enrollmentRate) 수강정보명 조회 속성: lectureName", description = "강의 정보 조회 Api")
    @GetMapping(value = "/search")
    public ClassPilotResponse<Page<LectureDto>> getSearchLectures(
                @Parameter(description = "페이지 번호 (기본값: 1)", name = "page")
                @RequestParam(name = "page",required = false, defaultValue = "1") int page,
                @Parameter(description = "페이지 크기 (기본값: 20)", name = "size")
                @RequestParam(name="size", required = false, defaultValue = "20") int size,
                @Parameter(description = "강의 이름(필수 아님)", name = "lectureName")
                @RequestParam(name="lectureName", required = false) String lectureName,
                @Parameter(description = "정렬 기준 (createdAt(default), currentStudentCnt, enrollmentRate)", name = "sortBy")
                @RequestParam(name="sortBy", required = false) String sortBy
            ) {
        LectureSearchCriteriaDto lectureSearchCriteriaDto = new LectureSearchCriteriaDto();
        lectureSearchCriteriaDto.setPage(page);
        lectureSearchCriteriaDto.setSize(size);
        lectureSearchCriteriaDto.setLectureName(lectureName);
        lectureSearchCriteriaDto.setSortBy(sortBy);
        log.info("LectureController.getLectures lectureSearchCriteriaDto: {}", lectureSearchCriteriaDto);
        return new ClassPilotResponse<>(this.lectureService.getLectures(lectureSearchCriteriaDto));
    }

    /**
     * 강의 등록 api
     * @param lectureDto 강의등록 정보
     * @return {@link ClassPilotResponse<Long>}
     */
    @Operation(summary = "강의 등록 Api", description = "월급쟁이부자들 강의 등록 Api")
    @PostMapping("/register")
    public ClassPilotResponse<LectureDto> registerLecture(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "강의 정보 등록 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LectureRegisterDto.class), examples = @ExampleObject(
                            value = "{\"memberId\": 1, \"lectureName\": \"너나위의 내집마련 중급반\", \"maxStudents\": 20, \"price\": 200000 }")))
            @Valid @RequestBody LectureRegisterDto lectureDto) {
        log.info("LectureController.registerLecture lectureDto: {}", lectureDto);
        return new ClassPilotResponse<>(this.lectureService.registerLecture(lectureDto));
    }

}
