package com.classpilot.api;

import com.classpilot.api.response.ClassPilotResponse;
import com.classpilot.service.lecture.LectureService;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
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

    @GetMapping("/search")
    public ClassPilotResponse<Page<LectureDto>> getSearchLectures(@ModelAttribute LectureSearchCriteriaDto lectureSearchCriteriaDto) {
        log.info("LectureController.getLectures lectureSearchCriteriaDto: {}", lectureSearchCriteriaDto);
        return new ClassPilotResponse<>(this.lectureService.getLectures(lectureSearchCriteriaDto));
    }

}
