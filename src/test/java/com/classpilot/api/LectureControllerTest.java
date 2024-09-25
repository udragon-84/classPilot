package com.classpilot.api;

import com.classpilot.service.lecture.LectureService;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureRegisterDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<LectureDto> lectureDtoList;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        // 초기화 처리 필요 시 작성
        lectureDtoList = Arrays.asList(
                LectureDto.builder()
                .lectureId(1L)
                .lectureName("너나위의 내집마련 기초반")
                .price(1000)
                .instructorName("John Doe")
                .maxStudents(50)
                .currentStudentCnt(4)
                .enrollmentRate(8.0)
                .build(),
                LectureDto.builder()
                        .lectureId(2L)
                        .lectureName("신도시투자 기초반")
                        .price(1500)
                        .instructorName("Jane Smith")
                        .maxStudents(40)
                        .currentStudentCnt(0)
                        .enrollmentRate(0.0)
                        .build()
                );
    }

    @Test
    public void testGetSearchLectures() throws Exception {
        // Arrange: Prepare mock response
        Page<LectureDto> page = new PageImpl<>(lectureDtoList, PageRequest.of(1, 20), 2);

        // Mock the service method
        when(lectureService.getLectures(any(LectureSearchCriteriaDto.class))).thenReturn(page);

        mockMvc.perform(get("/lecture/search")
                        .param("page", "1")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].lectureId").value(1))
                .andExpect(jsonPath("$.data.content[0].lectureName").value("너나위의 내집마련 기초반"))
                .andExpect(jsonPath("$.data.content[1].lectureId").value(2))
                .andExpect(jsonPath("$.data.content[1].lectureName").value("신도시투자 기초반"))
                .andExpect(jsonPath("$.data.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.data.pageable.pageSize").value(20));
    }

    @Test
    public void testRegisterLecture() throws Exception {
        // Arrange: 준비 작업 (Mock LectureDto 생성)
        LectureRegisterDto lectureRegisterDto = new LectureRegisterDto();
        lectureRegisterDto.setMemberId(1L);
        lectureRegisterDto.setLectureName("너나위의 내집마련 중급반");
        lectureRegisterDto.setMaxStudents(20);
        lectureRegisterDto.setPrice(200000);

        LectureDto mockLectureDto = LectureDto.builder()
                .lectureId(1L)
                .lectureName("너나위의 내집마련 중급반")
                .price(200000)
                .instructorName("John Doe")
                .maxStudents(20)
                .currentStudentCnt(0)
                .enrollmentRate(0)
                .build();

        // Mock the service method
        when(lectureService.registerLecture(any(LectureRegisterDto.class))).thenReturn(mockLectureDto);

        // Act: MockMvc를 사용해 Post 요청을 보내고 검증
        mockMvc.perform(post("/lecture/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lectureRegisterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.data.lectureId").value(1))
                .andExpect(jsonPath("$.data.lectureName").value("너나위의 내집마련 중급반"))
                .andExpect(jsonPath("$.data.price").value(200000))
                .andExpect(jsonPath("$.data.instructorName").value("John Doe"))
                .andExpect(jsonPath("$.data.maxStudents").value(20))
                .andExpect(jsonPath("$.data.currentStudentCnt").value(0))
                .andExpect(jsonPath("$.data.enrollmentRate").value(0));
    }

}
