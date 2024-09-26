package com.classpilot.service.lecture;

import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureRegisterDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface LectureService {
    Page<LectureDto> getLectures(LectureSearchCriteriaDto searchCriteria);
    LectureDto registerLecture(LectureRegisterDto lectureDto);
    LectureDto findLectureById(Long id);
    int incrementStudentCountIfNotFull(Long lectureId);
}
