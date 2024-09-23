package com.classpilot.service.member.convert;
import com.classpilot.repository.entity.MemberEntity;
import com.classpilot.service.member.dto.MemberDto;

public class MemberConverter {

    public static MemberEntity toEntity(MemberDto memberDto) {
       MemberEntity memberEntity = MemberEntity.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .phoneNumber(memberDto.getPhoneNumber())
                .password(memberDto.getPassword())
                .memberType(memberDto.getMemberType())
                .build();
        memberEntity.setCreatedBy(memberDto.getName());
        memberEntity.setModifiedBy(memberDto.getName());
        return memberEntity;
    }

    public static MemberDto toDomain(MemberEntity memberEntity) {
        return MemberDto.builder()
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .phoneNumber(memberEntity.getPhoneNumber())
                .password(memberEntity.getPassword())
                .memberType(memberEntity.getMemberType())
                .build();
    }
}
