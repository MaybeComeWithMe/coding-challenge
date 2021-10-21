package com.ubitricity.codingchallenge.controller.advice;

import com.ubitricity.codingchallenge.controller.advice.model.ErrorInfo;
import com.ubitricity.codingchallenge.exception.EntityNotFoundException;
import com.ubitricity.codingchallenge.exception.EntityValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class Handler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorInfo handleEntityNotFoundException(final EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ErrorInfo(NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(EntityValidationException.class)
    public ErrorInfo handleEntityValidationException(final EntityValidationException e) {
        log.error(e.getMessage(), e);
        return new ErrorInfo(CONFLICT.value(), e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorInfo runtimeError(final RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorInfo(INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
