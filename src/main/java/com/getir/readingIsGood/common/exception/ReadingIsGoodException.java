package com.getir.readingIsGood.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadingIsGoodException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public ReadingIsGoodException(String message){
        super(message);
    }

    public ReadingIsGoodException(String message, Throwable cause){
        super(message, cause);
    }

    public ReadingIsGoodException(String message, ExceptionCode exceptionCode){
        super(message);
        this.exceptionCode = exceptionCode;
    }



}
