package com.practice.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.practice.demo.exception.EntityNotFoundException;


@ControllerAdvice
public class CommonExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandlerController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
    }
}
