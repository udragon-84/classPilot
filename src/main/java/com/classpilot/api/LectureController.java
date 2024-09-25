package com.classpilot.api;

import com.classpilot.api.response.ClassPilotResponse;
import com.classpilot.service.lecture.LectureService;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/lecture")
@Tag(name = "수강 조회 Api", description = "월급쟁이부자들 수강 조회 Api 명세")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @Operation(summary = "수강 정보 조회 Api 정렬 기준 (최근 등록순:createdAt(default) | 신청자 많은 순:currentStudentCnt | 신청률 높은 순: enrollmentRate) 수강정보명 조회 속성: lectureName", description = "수강 정보 조회 Api")
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

}
