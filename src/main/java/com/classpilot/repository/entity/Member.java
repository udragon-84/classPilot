package com.classpilot.repository.entity;

import com.classpilot.domain.MemberType;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 (JPA 용)
@AllArgsConstructor // 모든 필드를 포함하는 생성자 (Builder 사용 시 필요)
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;
}
