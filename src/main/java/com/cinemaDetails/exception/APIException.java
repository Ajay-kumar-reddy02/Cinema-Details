package com.cinemaDetails.exception;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public APIException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
