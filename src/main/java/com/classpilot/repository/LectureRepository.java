package com.classpilot.repository;

import com.classpilot.repository.custom.LectureRepositoryCustomer;
import com.classpilot.repository.entity.LectureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long>, LectureRepositoryCustomer {

    // TODO JPQL N + 1 문제가 있어서 nativeQuery로 변경
    @Query(" SELECT lec FROM LectureEntity lec " +
            " LEFT JOIN MemberEntity ins ON lec.instructorId = ins.memberId " + 
            " LEFT JOIN EnrollmentEntity enr ON lec.lectureId = enr.lectureEntity.lectureId " +
            " WHERE ins.memberType = 'INSTRUCTOR' " +
            " AND (:lectureName IS NULL OR lec.lectureName LIKE CONCAT(:lectureName, '%')) " +
            " GROUP BY lec.lectureId, lec.lectureName, lec.price, ins.name, lec.maxStudents")
    Page<LectureEntity> findLecturesWithEnrollmentData(@org.springframework.data.repository.query.Param("lectureName") String lectureName, Pageable pageable);
}
