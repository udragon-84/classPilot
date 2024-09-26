package com.classpilot.repository;

import com.classpilot.repository.custom.LectureRepositoryCustomer;
import com.classpilot.repository.entity.LectureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long>, LectureRepositoryCustomer {

    // TODO JPQL N + 1 문제가 있어서 nativeQuery로 변경 -> LectureRepositoryCustomer
    @Query(" SELECT lec FROM LectureEntity lec " +
            " LEFT JOIN MemberEntity ins ON lec.instructorId = ins.memberId " + 
            " LEFT JOIN EnrollmentEntity enr ON lec.lectureId = enr.lectureEntity.lectureId " +
            " WHERE ins.memberType = 'INSTRUCTOR' " +
            " AND (:lectureName IS NULL OR lec.lectureName LIKE CONCAT(:lectureName, '%')) " +
            " GROUP BY lec.lectureId, lec.lectureName, lec.price, ins.name, lec.maxStudents")
    Page<LectureEntity> findLecturesWithEnrollmentData(@org.springframework.data.repository.query.Param("lectureName") String lectureName, Pageable pageable);

    // 동시성 처리 이슈로 인하여 현재 수강인원 체크
    @Modifying
    @Query("UPDATE LectureEntity l SET l.currentStudentCnt = l.currentStudentCnt + 1 WHERE l.lectureId = :lectureId AND l.currentStudentCnt < l.maxStudents")
    int incrementStudentCountIfNotFull(@Param("lectureId") Long lectureId);
}
