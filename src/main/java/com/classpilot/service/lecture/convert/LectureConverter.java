package com.classpilot.service.lecture.convert;
import com.classpilot.repository.entity.LectureEntity;
import com.classpilot.service.lecture.dto.LectureDto;
import com.classpilot.service.lecture.dto.LectureRegisterDto;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

public class LectureConverter {
    public static LectureDto toDomain(Object[] row) {
        return LectureDto.builder()
                .lectureId((Long)row[0])
                .lectureName((String)row[1])
                .price((Integer)row[2])
                .instructorName((String)row[3])
                .maxStudents((Integer)row[4])
                .currentStudentCnt((Integer)row[5])
                .enrollmentRate((Double)row[6])
                .build();
    }

    public static LectureEntity toEntity(LectureRegisterDto lectureDto) {
        LectureEntity lectureEntity = LectureEntity.builder()
                .lectureName(lectureDto.getLectureName())
                .maxStudents(lectureDto.getMaxStudents())
                .price(lectureDto.getPrice())
                .instructorId(lectureDto.getMemberId())
                .build();

        lectureEntity.setCreatedBy(String.valueOf(lectureDto.getMemberId()));
        lectureEntity.setModifiedBy(String.valueOf(lectureDto.getMemberId()));
        return lectureEntity;
    }

    public static LectureDto toDomain(LectureEntity entity, String instructorName) {
        return LectureDto.builder()
                .lectureId(entity.getLectureId())
                .lectureName(entity.getLectureName())
                .price(entity.getPrice())
                .instructorName(Objects.isNull(entity.getInstructor()) ? instructorName : entity.getInstructor().getName())
                .maxStudents(entity.getMaxStudents())
                .currentStudentCnt(CollectionUtils.isEmpty(entity.getEnrollmentEntityList()) ? 0 : entity.getEnrollmentEntityList().size())
                .build();
    }
}
