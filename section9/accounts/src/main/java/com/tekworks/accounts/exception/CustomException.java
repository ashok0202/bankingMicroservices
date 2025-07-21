package com.tekworks.accounts.exception;


import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private String errorCode;
    private int status;



    public CustomException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status.value();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return super.getMessage();
    }

}