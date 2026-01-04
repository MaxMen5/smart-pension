package ru.eltech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.eltech.dto.auth.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorDto> handleBusinessException(MyException ex) {
        ErrorDto error = new ErrorDto(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}