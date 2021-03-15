package com.yoti.roboticHoover.model;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    private Date timestamp;
    private String exceptionCode;
    private String message;
    private List<String> errors;

    public ExceptionResponse(Exception ex, List<String> errors) {
        this(new Date(), ex.getClass().getName(), ex.getMessage(), errors);
    }

    public ExceptionResponse(Date timestamp, String exceptionCode, String message, List<String> errors) {
        super();
        this.timestamp = timestamp;
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
