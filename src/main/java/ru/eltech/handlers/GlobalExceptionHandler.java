package ru.eltech.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.eltech.dto.ErrorResponse;
import ru.eltech.exception.MyException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(MyException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}