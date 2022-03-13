package com.getir.readingIsGood.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private String httpStatus;
    private String message;
    private LocalDateTime dateTime;

    public ExceptionResponse() {

    }
}
