package com.example.practice.jpa_practice.common.util;

import com.example.practice.jpa_practice.common.exception.ErrorException;
import com.example.practice.jpa_practice.common.web.ResponseType;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ContentInfoUtils {

    public static String findMimeType(InputStream inputStream) throws IOException {

        ContentInfo contentInfo = new ContentInfoUtil().findMatch(inputStream);

        if (contentInfo == null) {

            throw new ErrorException(ExceptionMessageUtils.getMessage(ResponseType.INVALID_FORMAT));
//            throw new IllegalArgumentException("Unknown mimeType is not supported");
        }
        return contentInfo.getMimeType();
    }

    public static String findMimeType(MultipartFile file) throws IOException {

        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(convFile);
        Path path = Paths.get(convFile.getAbsolutePath());
        return Files.probeContentType(path);

    }


}