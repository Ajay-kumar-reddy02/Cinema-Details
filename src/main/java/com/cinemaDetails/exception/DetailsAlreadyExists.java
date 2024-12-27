package com.cinemaDetails.exception;

public class DetailsAlreadyExists extends RuntimeException{
    private String message;
    public DetailsAlreadyExists(String message){
        super(message);
    }
}
