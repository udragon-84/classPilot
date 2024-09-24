package com.classpilot.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class LectureRepositoryCustomerImpl implements LectureRepositoryCustomer {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Object[]> findLecturesWithDynamicSorting(String lectureName, String sortField, String sortDirection, Pageable pageable) {
        String queryStr = "SELECT lec.lectureId, lec.lectureName, lec.price, ins.name, lec.maxStudents, " +
                "COUNT(enr.enrollmentId) AS currentStudentCnt, " +
                "TRUNCATE((COUNT(enr.enrollmentId) / lec.maxStudents) * 100, 3) AS enrollmentRate, " +
                "MAX(lec.createdAt) AS createdAt " +
                "FROM lecture lec " +
                "LEFT OUTER JOIN member ins ON lec.instructorId = ins.memberId " +
                "LEFT OUTER JOIN enrollment enr ON lec.lectureId = enr.lectureId " +
                "WHERE ins.memberType = 'INSTRUCTOR' " +
                "AND (? IS NULL OR lec.lectureName LIKE CONCAT(?, '%')) " +
                "GROUP BY lec.lectureId, lec.lectureName, lec.price, ins.name, lec.maxStudents " +
                "ORDER BY " + sortField + " " + sortDirection;

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(1, lectureName);
        query.setParameter(2, lectureName);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> resultList = query.getResultList();

        String countQueryStr = "SELECT COUNT(*) FROM lecture lec " +
                "LEFT OUTER JOIN member ins ON lec.instructorId = ins.memberId " +
                "WHERE ins.memberType = 'INSTRUCTOR' " +
                "AND (? IS NULL OR lec.lectureName LIKE CONCAT(?, '%'))";

        Query countQuery = entityManager.createNativeQuery(countQueryStr);
        countQuery.setParameter(1, lectureName);
        countQuery.setParameter(2, lectureName);
        long totalCount = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(resultList, pageable, totalCount);
    }


}
