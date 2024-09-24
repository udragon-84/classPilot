package com.classpilot.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureRepositoryCustomer {
    Page<Object[]> findLecturesWithDynamicSorting(String lectureName, String sortField, String sortDirection, Pageable pageable);
}
