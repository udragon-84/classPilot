package com.classpilot.api;

import com.classpilot.api.response.ClassPilotResponse;
import com.classpilot.service.enrollment.EnrollmentService;
import com.classpilot.service.enrollment.dto.EnrollmentRegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/enrollment")
@Tag(name = "강의 신청 관리 Api", description = "월급쟁이부자들 강의 신청 관리 Api 명세")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * 강의 신청 등록 api
     * @param enrollmentRegisterDtoList 강의 신청 등록 정보
     * @return {@link ClassPilotResponse<Boolean>}
     */
    @Operation(summary = "학생 및 강사 강의 신청 등록 Api", description = "월급쟁이부자들 강의 신청 등록 Api")
    @PostMapping("/register")
    public ClassPilotResponse<Boolean> registerEnrollment(@Valid @RequestBody List<EnrollmentRegisterDto> enrollmentRegisterDtoList) {
        log.info("EnrollmentController.registerEnrollment enrollmentRegisterDtoList: {}", enrollmentRegisterDtoList);
        return new ClassPilotResponse<>(this.enrollmentService.registerEnrollment(enrollmentRegisterDtoList));
    }

}
