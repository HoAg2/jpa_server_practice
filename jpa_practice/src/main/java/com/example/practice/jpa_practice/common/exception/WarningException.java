package com.example.practice.jpa_practice.common.exception;

import com.example.practice.jpa_practice.common.util.ExceptionMessageUtils;
import com.example.practice.jpa_practice.common.web.ResponseType;

public class WarningException extends CustomException {

    public WarningException(ResponseType errorCodeType) {
        super(errorCodeType);
    }

    public WarningException(ExceptionMessageUtils.ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getResponseType(), exceptionResponse.getErrorMessage());
    }
}
