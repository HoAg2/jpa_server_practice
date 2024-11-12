package com.example.practice.jpa_practice.common.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseType {

    SUCCESS(HttpStatus.OK, "S0000"),
    FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "F0000"),

    NO_CONTENT(HttpStatus.OK, "BH0204"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BH0400"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "BH0401"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "BH0403"),
    NOT_FOUND_SERVER(HttpStatus.NOT_FOUND, "BH0404"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "BH0405"),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "BH0502"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "BH0503"),

    // COMMON
    NOT_FOUND(HttpStatus.OK, "BC1001"),
    INVALID_ARGUMENT(HttpStatus.OK, "BC1002"),
    ALREADY_EXISTS(HttpStatus.OK, "BC1003"),
    REQUEST_PARAM(HttpStatus.OK, "BC1004"),
    REQUEST_HEADER(HttpStatus.OK, "BC1005"),
    INVALID_ACCESS(HttpStatus.OK, "BC1006"),
    INVALID_AUTHENTICATION(HttpStatus.OK, "BC1007"),
    INVALID_AUTHORIZATION(HttpStatus.OK, "BC1008"),
    EXPIRED_JWT_TOKEN(HttpStatus.OK, "BC1009"),
    EXCEPTION(HttpStatus.OK, "BC1010"),
    RUNTIME(HttpStatus.OK, "BC1011"),
    CLIENT_ABORT(HttpStatus.OK, "BC1012"),
    INVALID_FORMAT(HttpStatus.OK, "BC1013"),
    FILE_SYSTEM(HttpStatus.OK, "BC1014"),
    FAILURE_SAVE_ALL_EXCEL_SHEET(HttpStatus.OK, "BC1015"),
    DOWNLOAD_REASON_TOO_SHORT(HttpStatus.OK, "BC1016"),
    FAILURE_CREATING_EXCEL_FILE(HttpStatus.OK, "BC1017"),
    CAN_NOT_GET_LOCK(HttpStatus.OK, "BC1018"),
    OPTIMISTIC_LOCK_FAILED(HttpStatus.OK, "BC1019"),

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BLDA000001"),
    KNOWN_ERROR(HttpStatus.OK, "BLDA000002"),
    DRP_NOT_FOUND(HttpStatus.OK, "BLDA000003"),
    DRP_ALREADY_SOLD_DEVICE(HttpStatus.OK, "BLDA000004"),
    DRP_SERVER_DOWNED(HttpStatus.OK, "BLDA000005"),

    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "E9999");

    private final HttpStatus httpStatus;
    private final String code;

    ResponseType(HttpStatus httpStatus, String code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
