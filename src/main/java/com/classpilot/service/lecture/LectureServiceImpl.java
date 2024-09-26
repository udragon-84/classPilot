package com.classpilot.service.lecture;

import com.classpilot.common.exception.DomainException;
import com.classpilot.repository.LectureRepository;
import com.classpilot.service.lecture.convert.LectureConverter;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureRegisterDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import com.classpilot.service.member.MemberService;
import com.classpilot.service.member.dto.MemberDto;
import com.classpilot.service.member.dto.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private MemberService memberService;

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

    @Override
    @Transactional(readOnly = false)
    public LectureDto registerLecture(LectureRegisterDto lectureDto) {
        MemberDto memberDto = this.memberService.findMemberById(lectureDto.getMemberId());
        Optional.ofNullable(memberDto)
                .ifPresent(dto -> {
                    if (MemberType.INSTRUCTOR != dto.getMemberType()) {
                        throw new DomainException("강의등록은 강사로 등록된 회원만 등록가능합니다.");
                    }
                });

        return Optional.of(this.lectureRepository.save(LectureConverter.toEntity(lectureDto)))
                .map(dto -> LectureConverter.toDomain(dto, memberDto.getName()))
                .orElseThrow(() -> new DomainException("강의 등록에 실패 했습니다."));
    }

    @Override
    public LectureDto findLectureById(Long id) {
        return this.lectureRepository.findById(id)
                .map(entity -> LectureConverter.toDomain(entity, null))
                .orElse(null);
    }

    @Override
    public int incrementStudentCountIfNotFull(Long lectureId) {
        return this.lectureRepository.incrementStudentCountIfNotFull(lectureId);
    }

}
