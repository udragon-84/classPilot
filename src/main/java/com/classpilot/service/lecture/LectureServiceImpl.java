package com.classpilot.service.lecture;

import com.classpilot.repository.LectureRepository;
import com.classpilot.service.lecture.convert.LectureConverter;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<LectureDto> getLectures(LectureSearchCriteriaDto searchCriteria) {
        // Sort 객체에서 필드와 방향을 가져옴
        Sort sort = searchCriteria.getSort();
        String sortField = "createdAt";  // 기본값 (최근 등록순)
        String sortDirection = "DESC";   // 기본 정렬 방향

        // Sort 필드와 방향을 동적으로 설정
        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next(); // 첫 번째 정렬 기준 가져오기
            sortField = order.getProperty();           // 정렬할 필드
            sortDirection = order.getDirection().name(); // 정렬 방향 (ASC or DESC)
        }

        Pageable pageable = searchCriteria.getPageable();
        Page<Object[]> lectureEntities = lectureRepository
                .findLecturesWithDynamicSorting(searchCriteria.getLectureName(), sortField, sortDirection, pageable);

        return lectureEntities.map(LectureConverter::toDomain);
    }

}
