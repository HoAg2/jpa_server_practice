package com.example.practice.jpa_practice.common.util;

import com.example.practice.jpa_practice.common.web.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;

import static java.lang.String.format;

@RequiredArgsConstructor
public class MessageUtils {
    private static MessageSource messageSource;

    public static String getMessage(String code, Object... args) {
        if (messageSource == null) {
            return format("Can't initialize messageSource: code: %s, args: %s", code, Arrays.toString(args));
        } else {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        }
    }

    public static String getMessage(MessageSourceResolvable resolvable) {
        return MessageUtils.messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }

    public static String getResponseMessage(ResponseType code) {
        return getResponseMessage(code, null);
    }

    public static String getResponseMessage(ResponseType code, Object... args) {
        return getMessage("response." + code.getCode(), args);
    }

    public static String getExceptionMessage(Class<? extends Throwable> exceptionClass) {
        return getExceptionMessage(exceptionClass, null);
    }

    public static String getExceptionMessage(Class<? extends Throwable> exceptionClass, Object... args) {
        var code = "exception." + exceptionClass.getSimpleName();
        var message = getMessage(code, args);
        if (message.equals(code) && !exceptionClass.getSimpleName().equals("Throwable")) {
            return getExceptionMessage((Class<? extends Throwable>) exceptionClass.getSuperclass(), args);
        }
        return message;
    }

    public static String getWarningMessage(String code, Object... args) {
        return getMessage("warning." + code, args);
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

}

