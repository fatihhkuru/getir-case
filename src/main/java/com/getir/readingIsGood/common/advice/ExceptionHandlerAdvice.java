package com.getir.readingIsGood.common.advice;

import com.getir.readingIsGood.common.exception.ExceptionCode;
import com.getir.readingIsGood.common.exception.ReadingIsGoodException;
import com.getir.readingIsGood.common.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    //TODO burada login girişi olamaz ise ne olur için tekrar bir exceptionResponse tanımı yapılması gerekeiblir.

    @ExceptionHandler(value = {ReadingIsGoodException.class})
    public ResponseEntity<ExceptionResponse> handleException(ReadingIsGoodException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setHttpStatus(e.getExceptionCode().getHttpStatus().toString());
        exceptionResponse.setMessage(e.getMessage() + e.getExceptionCode().getMessage());
        exceptionResponse.setDateTime(LocalDateTime.now());
        HttpStatus httpStatus = e.getExceptionCode().getHttpStatus();
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getBindingResult().getFieldError().getField() + " " +e.getBindingResult().getFieldError().getDefaultMessage());
        exceptionResponse.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
        exceptionResponse.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionResponse> handleException(ExceptionCode e){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
        exceptionResponse.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
