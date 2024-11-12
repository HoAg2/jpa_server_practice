package com.example.practice.jpa_practice.common.web;

import com.example.practice.jpa_practice.common.exception.ErrorException;
import com.example.practice.jpa_practice.common.exception.WarningException;
import com.example.practice.jpa_practice.common.util.CommonResponseUtils;
import com.example.practice.jpa_practice.common.util.ExceptionMessageUtils;
import com.example.practice.jpa_practice.common.util.MessageUtils;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Hidden
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse exception(HttpServletRequest request, Exception e) {

        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null) {

            ip = request.getRemoteAddr();
        }

        log.error("INTERNAL_SERVER_ERROR", e);

        String exceptionMessage = MessageUtils.getExceptionMessage(e.getClass());

        if (exceptionMessage.equals("exception." + e.getClass().getSimpleName())) {

            exceptionMessage = e.getMessage();
        }

        return CommonResponseUtils.getResponse(ResponseType.FAILURE, exceptionMessage);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse exception(HttpServletRequest request, NoResourceFoundException e) {
        log.error("올바른 경로가 아닙니다.", e);

        return CommonResponseUtils.getResponse(ResponseType.NOT_FOUND_SERVER,
                ExceptionMessageUtils.getMessage(ResponseType.NOT_FOUND_SERVER, request.getRequestURI()).getErrorMessage()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse exception(HttpServletRequest request, DataIntegrityViolationException e) {
        log.error("잘못된 파라미터 값 입니다.", e);

        return CommonResponseUtils.getResponse(ResponseType.INVALID_FORMAT,
                ExceptionMessageUtils.getMessage(ResponseType.INVALID_FORMAT, request.getRequestURI()).getErrorMessage()
        );
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse exception(HttpServletRequest request, UnrecognizedPropertyException e) {
        log.error("잘못된 파라미터 값 입니다.", e);

        return CommonResponseUtils.getResponse(ResponseType.INVALID_FORMAT,
                ExceptionMessageUtils.getMessage(ResponseType.INVALID_FORMAT, request.getRequestURI()).getErrorMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse exception(HttpServletRequest request, IllegalArgumentException e) {

        log.error("잘못된 파라미터 값 입니다.", e);
        return CommonResponseUtils.getResponse(ResponseType.FAILURE, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse exception(HttpServletRequest request, ConstraintViolationException e) {

        log.warn("INTERNAL_SERVER_ERROR", e);

        for (var constraint : e.getConstraintViolations()) {

            return CommonResponseUtils.getResponse(ResponseType.FAILURE, constraint.getMessage());
        }

        return CommonResponseUtils.getResponse(ResponseType.FAILURE, MessageUtils.getWarningMessage("invalidArgument"));
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse exception(HttpServletRequest request, IllegalStateException e) {

        log.warn("INTERNAL_SERVER_ERROR", e);

        return CommonResponseUtils.getResponse(ResponseType.FAILURE, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResponse exception(HttpServletRequest request, AuthenticationException e) {

        log.warn("UNAUTHORIZED", e);

        return CommonResponseUtils.getResponse(ResponseType.FAILURE, MessageUtils.getExceptionMessage(AuthenticationException.class));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResponse exception(HttpServletRequest request, AccessDeniedException e) {

        log.warn("FORBIDDEN", e);

        return CommonResponseUtils.getResponse(ResponseType.FAILURE, MessageUtils.getExceptionMessage(UsernameNotFoundException.class));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse exception(HttpServletRequest request, MethodArgumentNotValidException e) {

        MDC.put("@err_code", ResponseType.FAILURE.getCode());

        log.warn("INTERNAL_SERVER_ERROR", e);

        for (var binding : e.getBindingResult().getFieldErrors()) {

            return CommonResponseUtils.getResponse(ResponseType.FAILURE, binding.getField() + " : " + binding.getDefaultMessage());
        }
        return CommonResponseUtils.getResponse(ResponseType.FAILURE, MessageUtils.getWarningMessage("invalidArgument"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse exception(HttpServletRequest request, MethodArgumentTypeMismatchException e) {

        MDC.put("@err_code", ResponseType.FAILURE.getCode());

        log.warn("BAD_REQUEST", e);
        return CommonResponseUtils.getResponse(ResponseType.FAILURE, MessageUtils.getWarningMessage("invalidArgument") + " : " + e.getMostSpecificCause());
    }

    @ExceptionHandler(WarningException.class)
    @ResponseBody
    protected CommonResponse exception(HttpServletRequest request, WarningException e, HttpServletResponse response) {

        response.setStatus(e.getHttpStatus().value());

        log.warn(e.getErrorMessage(), e);

        return CommonResponseUtils.getResponse(e.getErrorCodeType(), e.getErrorMessage());
    }

    @ExceptionHandler(ErrorException.class)
    @ResponseBody
    protected CommonResponse exception(HttpServletRequest request, ErrorException e, HttpServletResponse response) {

        if("/administrator/sse/subscribe".equals(request.getRequestURI())
                && List.of(ResponseType.INVALID_AUTHENTICATION, ResponseType.EXPIRED_JWT_TOKEN).contains(e.getErrorCodeType())) {

            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else if(ResponseType.INVALID_AUTHENTICATION.equals(e.getErrorCodeType())){

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {

            response.setStatus(e.getHttpStatus().value());
        }

        log.error(e.getErrorMessage(), e);

        return CommonResponseUtils.getResponse(e.getErrorCodeType(), e.getErrorMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse exception(HttpServletRequest request, ValidationException e) {

        WarningException warningException = (WarningException) e.getCause();

        log.warn("BAD_REQUEST", e);
        return CommonResponseUtils.getResponse(warningException.getErrorCodeType(), warningException.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> exception(HttpServletRequest request, HttpMediaTypeNotAcceptableException e) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body("Requested media type is not acceptable");
    }
}

