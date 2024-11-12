package com.example.practice.jpa_practice.common.web;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
@ToString(exclude = {"data"})
@EqualsAndHashCode(of = {"code", "message"})
public class CommonResponse<T> {
    ResponseType type;
    String code;
    String message;
    T data;

    public CommonResponse(T t) {
        type = ResponseType.SUCCESS;
        message = ResponseType.SUCCESS.name();
        code = ResponseType.SUCCESS.getCode();
        data = t;
    }

    public CommonResponse(ResponseType type, String message) {
        this.type = type;
        this.message = message;
        code = type.getCode();
        data = null;
    }

    public CommonResponse(ResponseType type, String message, T t) {
        this.type = type;
        this.message = message;
        code = type.getCode();
        data = t;
    }

    public static CommonResponse success() {
        return new CommonResponse<>(ResponseType.SUCCESS, ResponseType.SUCCESS.name());
    }

    public static CommonResponse unknown() {
        return new CommonResponse<>(ResponseType.UNKNOWN, ResponseType.UNKNOWN.name());
    }

    public static <T> CommonResponse<T> of(T t) {
        return new CommonResponse<>(t);
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

