package com.example.practice.jpa_practice.common.exception;

import com.example.practice.jpa_practice.common.util.ExceptionMessageUtils;
import com.example.practice.jpa_practice.common.web.ResponseType;

public class ErrorException extends CustomException {

    public ErrorException(ResponseType errorCodeType) {
        super(errorCodeType);
    }

    public ErrorException(ExceptionMessageUtils.ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getResponseType(), exceptionResponse.getErrorMessage());
    }

    public ErrorException(ExceptionMessageUtils.ExceptionResponse exceptionResponse, Throwable cause) {
        super(exceptionResponse.getResponseType(), exceptionResponse.getErrorMessage(), cause);
    }
}
