package com.cinemaDetails.exception;

//Helps to send the message to the client with custom message
public class DetailsNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public DetailsNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s called %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
