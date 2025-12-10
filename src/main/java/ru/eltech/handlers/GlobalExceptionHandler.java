package ru.eltech.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.eltech.dto.ErrorDto;
import ru.eltech.exception.MyException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorDto> handleBusinessException(MyException ex) {
        ErrorDto error = new ErrorDto(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}