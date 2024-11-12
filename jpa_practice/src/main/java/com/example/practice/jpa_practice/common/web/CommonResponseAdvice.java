package com.example.practice.jpa_practice.common.web;

import com.example.practice.jpa_practice.common.util.CommonResponseUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Field;

@RestControllerAdvice(basePackages = "com.example.practice")
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return !returnType.getParameterType().equals(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof CommonResponse) {

            return body;
        } else {

            return CommonResponseUtils.getSuccessResponse(body);
        }
    }

    /**
     * PATH, BODY 파라미터 String을 반환 한다.
     * @param request
     * @return
     */
    private String getRequestParameter(ServerHttpRequest request){

        final Integer limitSize = 1000;

        String requestBody = "";
        try {

            requestBody = new String(request.getBody().readAllBytes());
        } catch (IOException ignored){ }


        /**
         * X-FORWARDED-URI는 Client에서 세팅
         */
        String parameter = String.format("x-forwarded-uri : %s, path : %s, body : %s", this.getHeaderFromRequest(request, "X-FORWARDED-URI"), request.getURI().getQuery(), requestBody);
        if(parameter.length() > limitSize) {
            parameter = parameter.substring(0, limitSize);
        }

        return parameter;
    }

    private String getHeaderFromRequest(ServerHttpRequest request, String header){
        return request.getHeaders().get(header).isEmpty() ? "" : request.getHeaders().get(header).get(0);
    }

    private String getFieldByObject(Object object, String fieldName){
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals(fieldName)) {
                    return field.get(object).toString();
                }
            }

            return "null";
        } catch (IllegalAccessException e){

            return "null";
        }
    }

}
