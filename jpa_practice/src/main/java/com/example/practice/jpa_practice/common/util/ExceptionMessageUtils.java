package com.example.practice.jpa_practice.common.util;

import com.example.practice.jpa_practice.common.web.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Properties;

@RequiredArgsConstructor
public class ExceptionMessageUtils {

    public static ExceptionResponse getMessage(String externalCode, String message) {

        String errorMessage = "";

        try (InputStream inputStream = ExceptionMessageUtils.class
                .getClassLoader().getResourceAsStream("message.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Properties props = new Properties();
            props.load(reader);
            errorMessage = props.getProperty(externalCode);

            if(StringUtils.isEmpty(errorMessage)) {

                errorMessage = message;
            }
        } catch (IOException e) {

            errorMessage = message;
        }

        return new ExceptionResponse(ResponseType.KNOWN_ERROR, errorMessage);
    }

    public static ExceptionResponse getMessage(ResponseType responseType) {

        String errorMessage = "";

        try (InputStream inputStream = ExceptionMessageUtils.class
                .getClassLoader().getResourceAsStream("message.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Properties props = new Properties();
            props.load(reader);
            errorMessage = props.getProperty(responseType.getCode());
        } catch (IOException e) {

            errorMessage = "[ERROR] " + responseType.getCode() + " ERROR_CODE_NOT_DEFINITION";
        }

        return new ExceptionResponse(responseType, errorMessage);
    }

    public static ExceptionResponse getMessage(ResponseType responseType, Object... args) {

        ExceptionResponse exceptionResponse = getMessage(responseType);
        exceptionResponse.setErrorMessage(MessageFormat.format(exceptionResponse.getErrorMessage(), args));

        return exceptionResponse;
    }

    @Data
    @AllArgsConstructor
    public static class ExceptionResponse {

        ResponseType responseType;
        String errorMessage;
    }
}
