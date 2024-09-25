package com.classpilot.service.member;

import com.classpilot.service.member.dto.MemberDto;
import java.util.List;

public interface MemberService {
    Long registerMember(MemberDto memberDto);
    List<MemberDto> findAllMembers();
    MemberDto findMemberById(Long id);
}
