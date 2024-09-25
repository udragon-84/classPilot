package com.classpilot.service.lecture.convert;
import com.classpilot.service.lecture.dto.LectureDto;

public class LectureConverter {
    public static LectureDto toDomain(Object[] row) {
        return LectureDto.builder()
                .lectureId((Long)row[0])
                .lectureName((String)row[1])
                .price((Integer)row[2])
                .instructorName((String)row[3])
                .maxStudents((Integer)row[4])
                .currentStudentCnt(((Long)row[5]).intValue())
                .enrollmentRate((Double)row[6])
                .build();
    }

}
