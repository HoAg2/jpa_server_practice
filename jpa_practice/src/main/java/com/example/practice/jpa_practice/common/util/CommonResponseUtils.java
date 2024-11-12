package com.example.practice.jpa_practice.common.util;


import com.example.practice.jpa_practice.common.web.CommonResponse;
import com.example.practice.jpa_practice.common.web.ResponseType;

import static com.example.practice.jpa_practice.common.util.MessageUtils.getResponseMessage;

public class CommonResponseUtils {

    public static <T> CommonResponse getSuccessResponse() {
        return getResponse(ResponseType.SUCCESS, getResponseMessage(ResponseType.SUCCESS), null);
    }

    public static <T> CommonResponse getSuccessResponse(T data) {
        return getResponse(ResponseType.SUCCESS, getResponseMessage(ResponseType.SUCCESS), data);
    }

    public static <T> CommonResponse getFailureResponse() {
        return getResponse(ResponseType.FAILURE, getResponseMessage(ResponseType.FAILURE), null);
    }

    public static <T> CommonResponse getFailureResponse(T data) {
        return getResponse(ResponseType.FAILURE, getResponseMessage(ResponseType.FAILURE), data);
    }

    public static <T> CommonResponse<T> getResponse(ResponseType code, String message) {
        return getResponse(code, message, null);
    }

    public static <T> CommonResponse<T> getResponse(ResponseType code, String message, T data) {
        return CommonResponse.<T>builder().type(code).code(code.getCode()).message(message).data(data).build();
    }
}
