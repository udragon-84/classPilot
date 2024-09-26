package com.classpilot.service.enrollment;

import com.classpilot.common.exception.DomainException;
import com.classpilot.repository.EnrollmentRepository;
import com.classpilot.service.enrollment.convert.EnrollmentConverter;
import com.classpilot.service.enrollment.dto.EnrollmentRegisterDto;
import com.classpilot.service.lecture.LectureService;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.member.MemberService;
import com.classpilot.service.member.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnrollmentServiceImplTest {

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService; // 실제 테스트할 서비스

    @Mock
    private MemberService memberService;

    @Mock
    private LectureService lectureService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    private List<EnrollmentRegisterDto> enrollmentRegisterDtoList;
    private LectureDto lectureDto;
    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 샘플 데이터 생성
        EnrollmentRegisterDto enrollmentRegisterDto = EnrollmentRegisterDto.builder()
                .lectureId(1L)
                .memberId(2L)
                .build();

        enrollmentRegisterDtoList = Arrays.asList(enrollmentRegisterDto);

        lectureDto = LectureDto.builder()
                .lectureId(1L)
                .lectureName("Sample Lecture")
                .currentStudentCnt(5)
                .maxStudents(10)
                .build();

        memberDto = MemberDto.builder()
                .memberId(2L)
                .build();
    }

    @Test
    void testRegisterEnrollment_Success() {
        // Mocking 의존성들
        when(lectureService.findLectureById(anyLong())).thenReturn(lectureDto);
        when(memberService.findMemberById(anyLong())).thenReturn(memberDto);
        when(enrollmentRepository.existsByMemberIdAndLectureId(anyLong(), anyLong())).thenReturn(false);
        when(lectureService.incrementStudentCountIfNotFull(anyLong())).thenReturn(1);

        // 테스트 실행
        enrollmentService.registerEnrollment(enrollmentRegisterDtoList);

        // 확인: EnrollmentRepository의 save 메서드가 한 번 호출되었는지 확인
        verify(enrollmentRepository, times(1)).save(any());
    }

    @Test
    void testRegisterEnrollment_AlreadyEnrolled() {
        // 이미 등록된 경우에 대한 예외 처리
        when(lectureService.findLectureById(anyLong())).thenReturn(lectureDto);
        when(memberService.findMemberById(anyLong())).thenReturn(memberDto);
        when(enrollmentRepository.existsByMemberIdAndLectureId(anyLong(), anyLong())).thenReturn(true);

        // 이미 등록된 경우 예외 발생 여부 확인
        assertThrows(DomainException.class, () -> {
            enrollmentService.registerEnrollment(enrollmentRegisterDtoList);
        });

        // save 메서드가 호출되지 않았는지 확인
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void testRegisterEnrollment_EnrollmentFull() {
        // 수강 정원이 가득 찬 경우에 대한 예외 처리
        when(lectureService.findLectureById(anyLong())).thenReturn(lectureDto);
        when(memberService.findMemberById(anyLong())).thenReturn(memberDto);
        when(enrollmentRepository.existsByMemberIdAndLectureId(anyLong(), anyLong())).thenReturn(false);
        when(lectureService.incrementStudentCountIfNotFull(anyLong())).thenReturn(0); // 정원이 가득 찬 경우

        // 수강 정원 초과 시 예외 발생 여부 확인
        assertThrows(DomainException.class, () -> {
            enrollmentService.registerEnrollment(enrollmentRegisterDtoList);
        });

        // save 메서드가 호출되지 않았는지 확인
        verify(enrollmentRepository, never()).save(any());
    }

}