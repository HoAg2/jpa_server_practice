package com.example.practice.jpa_practice.common.exception;

import com.example.practice.jpa_practice.common.web.ResponseType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class CustomException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "";

    /**
     * Http 상태 코드
     */
    protected final HttpStatus httpStatus;
    /**
     * 에러 코드 타입
     */
    protected final ResponseType errorCodeType;
    /**
     * 에러 메시지
     */
    private final String errorMessage;

    private final transient List<Object> args;

    private final Object data;

    protected CustomException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCodeType = ResponseType.UNKNOWN;
        this.errorMessage = DEFAULT_ERROR_MESSAGE;
        this.args = new ArrayList<>();
        this.data = null;
    }

    protected CustomException(ResponseType responseType) {
        super();

        String errorMessage = "";
        try {
            Properties props = new Properties();
            InputStream inputStream = getClass()
                    .getClassLoader().getResourceAsStream("message.properties");
            props.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            errorMessage = props.getProperty(responseType.getCode());
        } catch (IOException e) {
            errorMessage = "[ERROR] " + responseType.getCode() + "ERROR_CODE_NOT_DEFINITION";
            e.printStackTrace();
        }

        this.httpStatus = responseType.getHttpStatus();
        this.errorCodeType = responseType;
        this.errorMessage = errorMessage;
        this.args = new ArrayList<>();
        this.data = null;
    }

    protected CustomException(ResponseType responseType, String errorMessage) {
        super(errorMessage);
        this.httpStatus = responseType.getHttpStatus();
        this.errorCodeType = responseType;
        this.errorMessage = errorMessage;
        this.args = new ArrayList<>();
        this.data = null;
    }

    protected CustomException(ResponseType responseType, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.httpStatus = responseType.getHttpStatus();
        this.errorCodeType = responseType;
        this.errorMessage = errorMessage;
        this.args = new ArrayList<>();
        this.data = null;
    }

    protected CustomException(ResponseType responseType, String errorMessage, Object data) {
        super(errorMessage);
        this.httpStatus = responseType.getHttpStatus();
        this.errorCodeType = responseType;
        this.errorMessage = errorMessage;
        this.args = new ArrayList<>();
        this.data = data;
    }
}

