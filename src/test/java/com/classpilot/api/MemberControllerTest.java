package com.classpilot.api;

import com.classpilot.service.member.MemberService;
import com.classpilot.service.member.dto.MemberDto;
import com.classpilot.service.member.dto.MemberType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        // 초기화 처리 필요 시 작성
        this.memberDto = new MemberDto();
        memberDto.setName("유창근");
        memberDto.setPassword("Yck1234");
        memberDto.setMemberType(MemberType.STUDENT);
        memberDto.setEmail("youzang7@gmail.com");
        memberDto.setPhoneNumber("010-7318-5308");
    }

    @Test
    void testFindAllMembers() throws Exception {

        when(this.memberService.findAllMembers()).thenReturn(List.of(memberDto));

        mockMvc.perform(get("/members/search/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("유창근"))
                .andExpect(jsonPath("$.data.size()").value(1));
    }


    @Test
    void testRegisterMember() throws Exception {
        // 회원 등록 API를 모킹하여 1L 반환
        when(this.memberService.registerMember(any(MemberDto.class))).thenReturn(1L);

        // JSON 요청 본문 생성
        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(memberJson))  // JSON 본문 추가
                .andExpect(status().isOk())  // 응답 상태 코드 200 확인
                .andExpect(jsonPath("$.result").value(true))  // result 필드가 true인지 확인
                .andExpect(jsonPath("$.data").value(1L))  // 응답 데이터로 회원 ID가 1인지 확인
                .andExpect(jsonPath("$.message").value("success"))  // 메시지 확인
                .andDo(print());  // 결과 출력
    }

    @Test
    void testRegisterMember_InvalidPassword_ShouldReturnBadRequest() throws Exception {
        // 잘못된 비밀번호를 가진 memberDto를 JSON으로 변환
        this.memberDto.setPassword("123");
        String invalidMemberJson = objectMapper.writeValueAsString(memberDto);

        // POST 요청으로 회원 등록 시도
        mockMvc.perform(post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidMemberJson))  // 잘못된 비밀번호 포함된 JSON 전송
                .andExpect(status().isBadRequest())  // 400 Bad Request 상태 코드 확인
                .andExpect(jsonPath("$.result").value(false))  // 유효성 검사 실패로 result는 false여야 함
                .andExpect(jsonPath("$.message").value("Validation failed"))  // 전역 오류 메시지 확인
                .andExpect(jsonPath("$.data.password").value("비밀번호 형식을 올바르게 입력하여 주십시요 ex(최소 6자 이상 10자 이하, 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합이 필요합니다.)"));  // 비밀번호 오류 메시지 확인
    }

}
