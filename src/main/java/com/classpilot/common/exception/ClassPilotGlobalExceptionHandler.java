package com.classpilot.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.classpilot.api.response.ClassPilotResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ClassPilotGlobalExceptionHandler {

    /**
     * 파라메터로 넘어오는 값 Validation Exception 처리 핸들러
     * @param ex 필드 속성 에러 정보
     * @return {@link ResponseEntity<ClassPilotResponse>}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClassPilotResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // 각 필드의 에러 메시지를 수집
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ClassPilotResponse<Map<String, String>> response = new ClassPilotResponse<>(
                Boolean.FALSE,  // 응답이 실패했으므로 false
                HttpStatus.BAD_REQUEST,  // 상태 코드
                "Validation failed",  // 메시지
                errors  // 에러 정보
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 데이터 베이스 제약조건 Exception 처리 핸들러
     * @param ex 데이터베이스 제약조건 에러 정보
     * @return {@link ResponseEntity<ClassPilotResponse>}
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ClassPilotResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // 에러 메시지 생성
        String errorMessage = "Database integrity violation: " + ex.getMessage();
        log.error("Unique index or primary key violation Exception: {}", errorMessage);

        // ClassPilotResponse 객체 생성
        ClassPilotResponse<String> response = new ClassPilotResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 500
                "Database CONSTRAINT Error",  // 메시지
                "Unique index or primary key violation Exception"  // frontEnd 영역에 Database 정보가 나오면 안된다. 에러 세부 정보는 로그로 파악
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Member 회원관련 도메인 Exception 처리 핸들러
     * @param ex 데이터베이스 제약조건 에러 정보
     * @return {@link ResponseEntity<ClassPilotResponse>}
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ClassPilotResponse<String>> handleMemberException(DomainException ex) {
        ClassPilotResponse<String> response = new ClassPilotResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 500
                "Member Domain Error",  // 메시지
                ex.getMessage()  // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 그 외 나머지 Exception 처리
     * @param ex 데이터베이스 제약조건 에러 정보
     * @return {@link ResponseEntity<ClassPilotResponse>}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ClassPilotResponse<String>> handleException(Exception ex) {
        log.error("ClassPilotGlobalExceptionHandler.handleException", ex);
        ClassPilotResponse<String> response = new ClassPilotResponse<>(
                Boolean.FALSE,  // 실패 응답
                HttpStatus.INTERNAL_SERVER_ERROR,  // 상태 코드 500
                "ClassPilot Domain Error",  // 메시지
                ex.getMessage()  // 에러 세부 정보
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
