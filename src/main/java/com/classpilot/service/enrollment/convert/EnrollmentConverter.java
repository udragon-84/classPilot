package com.classpilot.service.enrollment.convert;

import com.classpilot.repository.entity.EnrollmentEntity;
import com.classpilot.service.enrollment.dto.EnrollmentRegisterDto;

public class EnrollmentConverter {

    public static EnrollmentEntity toEntity(EnrollmentRegisterDto registerDto) {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .memberId(registerDto.getMemberId())
                .lectureId(registerDto.getLectureId())
                .build();
        enrollmentEntity.setCreatedBy(String.valueOf(registerDto.getMemberId()));
        enrollmentEntity.setModifiedBy(String.valueOf(registerDto.getMemberId()));
        return enrollmentEntity;
    }
}
