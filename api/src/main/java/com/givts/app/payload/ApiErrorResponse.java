package com.givts.app.payload;

import java.time.LocalDate;
import java.util.List;

public class ApiErrorResponse {

    private LocalDate timestamp;
    private List<ApiError> errors;

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }
}
