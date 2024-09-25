package com.classpilot.service.member;

import com.classpilot.common.exception.DomainException;
import com.classpilot.repository.MemberRepository;
import com.classpilot.repository.entity.MemberEntity;
import com.classpilot.service.member.convert.MemberConverter;
import com.classpilot.service.member.dto.MemberDto;
import com.classpilot.service.member.dto.MemberType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        // MemberDto 초기화
        memberDto = MemberDto.builder()
                .name("유창근")
                .email("youzang7@gmail.com")
                .phoneNumber("010-5555-7777")
                .password("TestPassword123")
                .memberType(MemberType.STUDENT)
                .build();
    }

    // 회원 등록 성공 테스트
    @Test
    void testRegisterMember_Success() {
        // Mock 설정: 이메일 또는 전화번호 중복 없음
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("EncodedPassword");

        // MemberEntity에 ID 설정
        MemberEntity savedMemberEntity = MemberConverter.toEntity(memberDto);
        savedMemberEntity.setMemberId(1L);  // memberId를 명시적으로 설정

        when(memberRepository.save(any(MemberEntity.class))).thenReturn(savedMemberEntity);

        // 회원 등록 수행
        Long memberId = memberService.registerMember(memberDto);

        // 검증
        assertNotNull(memberId);  // memberId가 null이 아닌지 검증
        assertEquals(1L, memberId);  // 반환된 memberId가 1인지 검증
        verify(memberRepository, times(1)).save(any(MemberEntity.class)); // 한 번 저장되었는지 확인
    }

    // 중복된 이메일로 인한 회원 등록 실패 테스트
    @Test
    void testRegisterMember_DuplicateEmail() {
        // Mock 설정: 이메일 중복 존재
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(MemberConverter.toEntity(memberDto)));

        // 회원 등록 수행 시 예외 발생
        DomainException exception = assertThrows(DomainException.class, () -> {
            memberService.registerMember(memberDto);
        });

        // 예외 메시지 검증
        assertEquals("youzang7@gmail.com 해당 이메일은 이미 등록된 이메일 입니다.", exception.getMessage());
        verify(memberRepository, never()).save(any(MemberEntity.class)); // 저장되지 않음
    }

    // 중복된 전화번호로 인한 회원 등록 실패 테스트
    @Test
    void testRegisterMember_DuplicatePhoneNumber() {
        // Mock 설정: 전화번호 중복 존재
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(memberRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(MemberConverter.toEntity(memberDto)));

        // 회원 등록 수행 시 예외 발생
        DomainException exception = assertThrows(DomainException.class, () -> {
            memberService.registerMember(memberDto);
        });

        // 예외 메시지 검증
        assertEquals("010-5555-7777 해당 전화번호는 이미 등록된 전화번호 입니다.", exception.getMessage());
        verify(memberRepository, never()).save(any(MemberEntity.class)); // 저장되지 않음
    }

    // 모든 회원 조회 테스트
    @Test
    void testFindAllMembers() {
        // Mock 설정: 데이터베이스에 회원 한 명 존재
        when(memberRepository.findAll()).thenReturn(List.of(MemberConverter.toEntity(memberDto)));

        // 모든 회원 조회 수행
        List<MemberDto> members = memberService.findAllMembers();

        // 검증
        assertEquals(1, members.size()); // 회원 수 검증
        assertEquals("유창근", members.get(0).getName()); // 회원 정보 검증
        verify(memberRepository, times(1)).findAll(); // 조회 메서드 한 번 호출 확인
    }

}
