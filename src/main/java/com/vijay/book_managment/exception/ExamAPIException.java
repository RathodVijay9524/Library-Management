package com.vijay.book_managment.exception;

import org.springframework.http.HttpStatus;

public class ExamAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ExamAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ExamAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
