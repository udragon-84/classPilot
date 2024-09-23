package com.classpilot.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 활성화
public class BaseEntity {

    @CreatedDate
    @Column(name = "CREATEDAT", updatable = false)
    private LocalDateTime createdAt;  // 등록일

    @CreatedBy
    @Column(name = "CREATEDBY", updatable = false)
    private String createdBy;  // 등록자

    @LastModifiedDate
    @Column(name="MODIFIEDAT")
    private LocalDateTime modifiedAt;  // 수정일

    @LastModifiedBy
    @Column(name="MODIFIEDBY")
    private String modifiedBy;  // 수정자

}
