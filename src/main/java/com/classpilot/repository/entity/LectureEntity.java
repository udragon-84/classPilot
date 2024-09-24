package com.classpilot.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 (JPA 용)
@AllArgsConstructor // 모든 필드를 포함하는 생성자 (Builder 사용 시 필요)
@Entity
@Table(name = "lecture")
public class LectureEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LECTUREID")
    private Long lectureId;

    @Column(name="LECTURENAME")
    private String lectureName;

    @Column(name="MAXSTUDENTS")
    private int maxStudents;

    @Column(name="PRICE")
    private int price;

    @Column(name = "INSTRUCTORID")
    private Long instructorId;

    @ManyToOne
    @JoinColumn(name = "INSTRUCTORID", insertable = false, updatable = false)
    private MemberEntity instructor;

    @OneToMany(mappedBy = "lectureEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrollmentEntity> enrollmentEntityList;
}
