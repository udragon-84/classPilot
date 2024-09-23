package com.classpilot.api;

import com.classpilot.api.response.ClassPilotResponse;
import com.classpilot.service.member.MemberService;
import com.classpilot.service.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@Tag(name = "회원등록 관리 Api", description = "월급쟁이부자들 회원등록 관리 Api 명세")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 전체회원 조회
     * @return {@link ClassPilotResponse<List<MemberDto>>}
     */
    @Operation(summary = "전체 회원 조회 Api", description = "전체 회원 조회 Api")
    @GetMapping(value = "/all")
    public ClassPilotResponse<List<MemberDto>> findAllMembers() {
        return new ClassPilotResponse<>(this.memberService.findAllMembers());
    }

    /**
     * 회원 등록 api
     * @param memberDto 회원정보
     * @return {@link ClassPilotResponse<Long>}
     */
    @Operation(summary = "회원 등록 Api", description = "월급쟁이부자들 회원 등록 Api")
    @PostMapping("/register")
    public ClassPilotResponse<Long> registerMember(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회원 정보 등록 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MemberDto.class), examples = @ExampleObject(
                            value = "{ \"name\": \"유창근\", \"phoneNumber\": \"010-1234-5678\", \"email\": \"youzang7@gmail.com\", \"password\": \"**********\", \"memberType\": \"STUDENT\" }")))
            @Valid @RequestBody MemberDto memberDto) {
        return new ClassPilotResponse<>(this.memberService.registerMember(memberDto));
    }

}
