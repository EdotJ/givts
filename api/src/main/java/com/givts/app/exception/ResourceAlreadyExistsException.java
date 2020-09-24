package com.givts.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceAlreadyExistsException extends RuntimeException {
    private String resourceName;
    private String fieldName;

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("A %s already exists with %s %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
