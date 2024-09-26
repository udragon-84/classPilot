package com.classpilot.api;
import com.classpilot.api.response.ClassPilotResponse;
import com.classpilot.service.enrollment.EnrollmentService;
import com.classpilot.service.enrollment.dto.EnrollmentRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<EnrollmentRegisterDto> enrollmentRegisterDtoList;

    @BeforeEach
    void setUp() {
        EnrollmentRegisterDto enrollmentRegisterDto = EnrollmentRegisterDto.builder()
                .lectureId(1L)
                .memberId(2L)
                .build();
        enrollmentRegisterDtoList = Arrays.asList(enrollmentRegisterDto);
    }

    @Test
    void testRegisterEnrollment() throws Exception {
        // Mock the service to return true when registerEnrollment is called
        Mockito.when(enrollmentService.registerEnrollment(any())).thenReturn(true);

        // Perform the POST request
        mockMvc.perform(post("/enrollment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentRegisterDtoList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new ClassPilotResponse<>(true))));

        // Verify the service was called
        Mockito.verify(enrollmentService, Mockito.times(1)).registerEnrollment(any());
    }

}
