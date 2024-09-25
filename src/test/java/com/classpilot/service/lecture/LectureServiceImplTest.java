package com.classpilot.service.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.classpilot.repository.LectureRepository;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureSearchCriteriaDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

public class LectureServiceImplTest {

    @InjectMocks
    private LectureServiceImpl lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLectures() {
        Object[] lecture1 = {1L, "강의1", 1000, "너나위의 내집마련 기초반", 50, 4L, 8.0};
        Object[] lecture2 = {2L, "강의2", 1500, "신도시투자 기초반", 40, 2L, 5.0};
        List<Object[]> mockLectureList = Arrays.asList(lecture1, lecture2);
        Page<Object[]> mockPage = new PageImpl<>(mockLectureList, PageRequest.of(0, 20), 2);

        when(lectureRepository.findLecturesWithDynamicSorting(any(), any(), any(), any())).thenReturn(mockPage);

        LectureSearchCriteriaDto searchCriteria = new LectureSearchCriteriaDto();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        searchCriteria.setLectureName("강의");
        searchCriteria.setSortBy("createdAt");

        Page<LectureDto> result = lectureService.getLectures(searchCriteria);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("강의1", result.getContent().get(0).getLectureName());
        assertEquals(1000, result.getContent().get(0).getPrice());
        assertEquals(8.0, result.getContent().get(0).getEnrollmentRate());
        assertEquals("강의2", result.getContent().get(1).getLectureName());
        assertEquals(1500, result.getContent().get(1).getPrice());
        assertEquals(5.0, result.getContent().get(1).getEnrollmentRate());
    }
}
