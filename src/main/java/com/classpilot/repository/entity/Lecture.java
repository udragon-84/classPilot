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
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    private String lectureName;
    private int maxStudents;
    private int price;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member instructor;
}
