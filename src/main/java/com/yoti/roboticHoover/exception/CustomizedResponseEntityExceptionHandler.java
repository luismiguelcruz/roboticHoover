package com.yoti.roboticHoover.exception;

import com.yoti.roboticHoover.model.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Overriding handleException to return specific messages for all the exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request)  {
        final List<String> errors = new ArrayList() {{
            add(request.getDescription(false));
        }};

        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getClass().getName(), ex.getMessage(), errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    //Controlling the valid arguments exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        final List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()))
                .collect(Collectors.toList());

        ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()))
                .collect(Collectors.toList());

        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getClass().getName(),
                ex.getLocalizedMessage(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }
}
