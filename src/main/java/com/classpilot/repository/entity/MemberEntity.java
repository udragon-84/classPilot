package com.classpilot.repository.entity;

import com.classpilot.service.member.dto.MemberType;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 (JPA 용)
@AllArgsConstructor // 모든 필드를 포함하는 생성자 (Builder 사용 시 필요)
@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEMBERID")
    private Long memberId;

    @Column(name="NAME")
    private String name;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PHONENUMBER")
    private String phoneNumber;

    @Column(name="PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="MEMBERTYPE")
    private MemberType memberType;
}
