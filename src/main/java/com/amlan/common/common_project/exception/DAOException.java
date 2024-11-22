package com.amlan.common.common_project.exception;

public class DAOException extends RuntimeException{

    private final String errorType;

    public DAOException(String errorType, String errorMessage, Throwable cause){
        super(errorMessage,cause);
        this.errorType=errorType;
    }

    public String getErrorType(){
        return errorType;
    }
}
