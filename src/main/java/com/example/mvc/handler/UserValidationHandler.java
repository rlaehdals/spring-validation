package com.example.mvc.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.example.mvc")
@RequiredArgsConstructor
public class UserValidationHandler extends ResponseEntityExceptionHandler {

    private final TypeMismatchConvertor typeMismatchConvertor;


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream()
                .forEach(a -> {
                    String fieldName = ((FieldError)a).getField();
                    String message = a.getDefaultMessage();
                    map.put(fieldName,message);
                });
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        TypeMismatchConvertor.Result convert = typeMismatchConvertor.convert(ex.getMessage());
        return new ResponseEntity<>(convert,HttpStatus.BAD_REQUEST);
    }
}
