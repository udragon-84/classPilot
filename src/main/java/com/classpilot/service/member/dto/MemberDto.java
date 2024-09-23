package com.classpilot.service.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 정보 Domain", name = "MemberDto")
public class MemberDto {

    @Schema(description = "회원 이름", example = "유창근")
    @NotNull(message = "이름은 필수 입력 정보 입니다.")
    private String name;

    @Schema(description = "회원 이메일", example = "youzang7@gmail.com")
    @NotNull(message = "이메일은 필수 입력 정보 입니다.")
    @Email(message = "이메일 형식을 올바르게 입력하여 주십시요.")
    private String email;

    @Schema(description = "회원 핸드폰 번호", example = "010-7318-6666")
    @NotNull(message = "핸드폰 번호는 필수 입력 정보 입니다.")
    @Pattern(
            regexp = "^(010)(\\d{3,4})(\\d{4})$|^(010)-(\\d{3,4})-(\\d{4})$",
            message = "핸드폰번호 형식을 올바르게 입력하여 주십시요. ex(010-0000-0000, 01000000000, 010-000-0000, 0100000000)"
    )
    private String phoneNumber;

    @Schema(description = "회원 비밀번호", example = "*********")
    @NotNull(message = "비밀번호는 필수 입력 정보 입니다.")
    @Pattern(
            regexp = "^(?=(.*[A-Z].*)|(?=.*[a-z].*))(?=.*[0-9])(?=.*[A-Za-z])[A-Za-z0-9]{6,10}$",
            message = "비밀번호 형식을 올바르게 입력하여 주십시요 ex(최소 6자 이상 10자 이하, 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합이 필요합니다.)"
    )
    private String password;

    @Schema(description = "회원 유형", example = "STUDENT Or INSTRUCTOR")
    @NotNull(message = "회원 유형은 필수 입력 정보 입니다(STUDENT, INSTRUCTOR).")
    private MemberType memberType;
}
