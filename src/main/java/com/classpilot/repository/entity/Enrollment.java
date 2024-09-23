package com.classpilot.repository.entity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 (JPA 용)
@AllArgsConstructor // 모든 필드를 포함하는 생성자 (Builder 사용 시 필요)
@Entity
public class Enrollment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    // Member 엔티티와 다대일 관계
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    // Lecture 엔티티와 다대일 관계
    @ManyToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    private int currentStudents;
    private int price;
    private int maxStudents;
}
