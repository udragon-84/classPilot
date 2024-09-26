package com.classpilot.service.enrollment;

import com.classpilot.common.exception.DomainException;
import com.classpilot.repository.EnrollmentRepository;
import com.classpilot.service.enrollment.convert.EnrollmentConverter;
import com.classpilot.service.enrollment.dto.EnrollmentRegisterDto;
import com.classpilot.service.lecture.LectureService;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.member.MemberService;
import com.classpilot.service.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public Boolean registerEnrollment(List<EnrollmentRegisterDto> enrollmentRegisterDtoList) {
        enrollmentRegisterDtoList.forEach(enrollmentRegisterDto -> {
            LectureDto lectureDto = this.lectureService.findLectureById(enrollmentRegisterDto.getLectureId());
            MemberDto memberDto = this.memberService.findMemberById(enrollmentRegisterDto.getMemberId());
            this.registerEnrollmentValidation(memberDto, lectureDto, enrollmentRegisterDto);
            this.enrollmentRepository.save(EnrollmentConverter.toEntity(enrollmentRegisterDto));
        });
        return true;
    }

    private void registerEnrollmentValidation(MemberDto memberDto, LectureDto lectureDto, EnrollmentRegisterDto enrollmentRegisterDto) {
        if (Objects.isNull(memberDto)) throw new DomainException(String.format("%d 회원Id는 없는 Id 입니다.", enrollmentRegisterDto.getMemberId()));
        if (Objects.isNull(lectureDto)) throw new DomainException(String.format("%d 강의Id는 없는 Id 입니다.", enrollmentRegisterDto.getLectureId()));
        if (this.enrollmentRepository.existsByMemberIdAndLectureId(memberDto.getMemberId(), lectureDto.getLectureId()))
            throw new DomainException(String.format("회원Id %d는 이미 강의Id %d에 등록되었습니다.", enrollmentRegisterDto.getMemberId(), enrollmentRegisterDto.getLectureId()));
        // 동시성 처리로 인하여 Lecture 테이블에서 현재 수강신청한 인원수를 업데이트 한후 수량 체크
        if (this.lectureService.incrementStudentCountIfNotFull(enrollmentRegisterDto.getLectureId()) == 0)
            throw new DomainException("수강 인원이 초과되었습니다.");
    }

}
