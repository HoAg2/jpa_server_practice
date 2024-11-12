package com.example.practice.jpa_practice.common.util;

import com.example.practice.jpa_practice.common.exception.ErrorException;
import com.example.practice.jpa_practice.common.web.ResponseType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {

    private static final ZoneId DEFAULT_TIMEZONE = ZoneId.of("Asia/Seoul");

    public static ZoneId getDefaultTimezone() {

        return DEFAULT_TIMEZONE;
    }

    /**
     * 주어진 패턴의 날짜 문자열을 Instant로 변환합니다.
     * @param dateTimeString 날짜 문자열 (예: "20240515122300")
     * @param pattern 패턴 문자열 (예: "yyyyMMddHHmmss")
     * @return 변환된 Instant 객체
     */
    public static Instant convertPatternedStringToInstant(String dateTimeString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
            ZonedDateTime zonedDateTime = localDateTime.atZone(DEFAULT_TIMEZONE);
            return zonedDateTime.toInstant();
        } catch (DateTimeParseException e) {

            throw new ErrorException(ExceptionMessageUtils.getMessage(ResponseType.INVALID_FORMAT), e);
        }
    }

    /**
     * 주어진 패턴의 날짜 문자열을 Instant로 변환합니다. (DB에서 불러온 경우)
     * @param dateTimeString 날짜 문자열 (예: "20240515122300")
     * @param pattern 패턴 문자열 (예: "yyyyMMddHHmmss")
     * @return 변환된 Instant 객체
     */
    public static Instant convertStringToInstantWithPatternByDB(String dateTimeString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
            return zonedDateTime.toInstant();
        } catch (DateTimeParseException e) {

            throw new ErrorException(ExceptionMessageUtils.getMessage(ResponseType.INVALID_FORMAT), e);
        }
    }

    /**
     * 주어진 패턴으로 Instant를 문자열로 변환합니다.
     * @param instant 변환할 Instant 객체
     * @param pattern 패턴 문자열 (예: "yyyyMMddHHmmss")
     * @return 변환된 날짜 문자열
     */
    public static String convertInstantToStringWithPattern(Instant instant, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(DEFAULT_TIMEZONE);
        return formatter.format(instant);
    }

    /**
     * Instant를 KST 시간 문자열로 변환합니다.
     * @param instant 변환할 Instant 객체
     * @return KST 시간 문자열 (예: "2024-05-15T11:18:00")
     */
    public static String convertInstantToKSTString(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(DEFAULT_TIMEZONE);
        return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
