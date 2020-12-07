package com.givts.app.exception;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class FileSizeExceptionAdvice {

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleFileSizeExceeded(MaxUploadSizeExceededException ex) {
        DecimalFormat df = new DecimalFormat("#.00");
        Map<String, Object> error = new HashMap<>();
        SizeLimitExceededException castedEx = (SizeLimitExceededException) ex.getCause().getCause();
        error.put("message", "File too big");
        double permittedSize = castedEx.getPermittedSize() / 1024.0 / 1024.0;
        double actualSize = castedEx.getActualSize() / 1024.0 / 1024.0;
        error.put("explanation", "The maximum file size is "
                + df.format(permittedSize) + "MB"
                + ". Current file size is "
                + df.format(actualSize) + "MB");
        error.put("currentSize", df.format(actualSize));
        error.put("permittedSize", df.format(permittedSize));
        return ResponseEntity.status(413).body(error);
    }
}
