package com.cinemaDetails.payload;

import lombok.Getter;

import java.util.Date;

//Helps to send the client required info when an exception occurs
@Getter
public class ErrorDetails {
    private Date date;
    private String message;
    private String httpUrl;

    public ErrorDetails(Date date, String message, String httpUrl) {
        this.date = date;
        this.message = message;
        this.httpUrl = httpUrl;
    }
}
