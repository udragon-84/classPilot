package com.classpilot.service.member;
import com.classpilot.common.exception.MemberException;
import com.classpilot.repository.MemberRepository;
import com.classpilot.repository.entity.MemberEntity;
import com.classpilot.service.member.convert.MemberConverter;
import com.classpilot.service.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = false)
    public Long registerMember(MemberDto memberDto) {
        this.registerMemberValidation(memberDto);
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        MemberEntity memberEntity = this.memberRepository.save(MemberConverter.toEntity(memberDto));
        log.info("MemberServiceImpl.registerMember memberId: {}", memberEntity.getMemberId());
        return memberEntity.getMemberId();
    }

    private void registerMemberValidation(MemberDto paramMemberDto) {
        this.validateDuplicateField(() -> this.memberRepository.findByEmail(paramMemberDto.getEmail()),
                String.format("%s 해당 이메일은 이미 등록된 이메일 입니다.", paramMemberDto.getEmail()));

        this.validateDuplicateField(() -> this.memberRepository.findByPhoneNumber(paramMemberDto.getPhoneNumber()),
                String.format("%s 해당 전화번호는 이미 등록된 전화번호 입니다.", paramMemberDto.getPhoneNumber()));
    }

    private void validateDuplicateField(Supplier<Optional<MemberEntity>> findMemberFunction, String errorMessage) {
        findMemberFunction.get().ifPresent(memberEntity -> {
            throw new MemberException(errorMessage);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDto> findAllMembers() {
        return this.memberRepository.findAll()
                .stream()
                .map(MemberConverter::toDomain)
                .collect(Collectors.toList());
    }
}
