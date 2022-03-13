package com.getir.readingIsGood.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    //Book Excepitons
    BOOK_ALREADY_EXIST(" Book ALready Exists", HttpStatus.CONFLICT),
    BOOK_NOT_FOUND(" Book not found", HttpStatus.NOT_FOUND),
    STOCK_MUST_BE_GREATER_THAN_ZERO(" Stock must be greater than zero", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
