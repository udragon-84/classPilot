package com.classpilot.service.enrollment;

import com.classpilot.service.enrollment.dto.EnrollmentRegisterDto;

import java.util.List;

public interface EnrollmentService {
    Boolean registerEnrollment(List<EnrollmentRegisterDto> enrollmentRegisterDtoList);
}
