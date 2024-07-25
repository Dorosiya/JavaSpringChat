package portfolio.chatApplication.common.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import portfolio.chatApplication.member.exception.DuplicationException;
import portfolio.chatApplication.member.exception.MemberNotFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ExResponseDto> DuplicationExHandler(DuplicationException e) {
        log.error("DuplicationException : {}", e.getMessage(), e);
        ExResponseDto exDto = new ExResponseDto("fail", e.getMessage());

        return new ResponseEntity<>(exDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExResponseDto> MemberNotFoundExHandler(MemberNotFoundException e) {
        log.error("MemberNotFoundException : {}", e.getMessage(), e);
        ExResponseDto exDto = new ExResponseDto("fail", e.getMessage());

        return new ResponseEntity<>(exDto, HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    static class ExResponseDto {
        private String code;
        private String message;
    }

}
