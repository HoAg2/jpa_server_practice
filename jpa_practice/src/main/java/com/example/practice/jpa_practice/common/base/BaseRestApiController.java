package com.example.practice.jpa_practice.common.base;

import com.example.practice.jpa_practice.common.util.CommonResponseUtils;
import com.example.practice.jpa_practice.common.web.CommonResponse;
import com.example.practice.jpa_practice.common.web.ResponseType;

public abstract class BaseRestApiController extends CustomModelMapper {

    protected <T> CommonResponse success(T data) {
        return CommonResponseUtils.getSuccessResponse(data);
    }

    protected <T> CommonResponse failure(T data) {
        return CommonResponseUtils.getFailureResponse(data);
    }

    protected CommonResponse result(ResponseType code, String message) {
        return CommonResponseUtils.getResponse(code, message);
    }

    protected <T> CommonResponse result(ResponseType code, String message, T data) {
        return CommonResponseUtils.getResponse(code, message, data);
    }
}

