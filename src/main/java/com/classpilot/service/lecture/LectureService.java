package com.classpilot.service.lecture;

import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import org.springframework.data.domain.Page;

public interface LectureService {
    Page<LectureDto> getLectures(LectureSearchCriteriaDto searchCriteria);
}
