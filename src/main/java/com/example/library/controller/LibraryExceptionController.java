package com.example.library.controller;

import com.example.library.exception.LibraryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LibraryExceptionController {

    @ExceptionHandler(value = LibraryException.class)
    public ResponseEntity<Object> exception(LibraryException exception) {
        // making INTERNAL_SERVER_ERROR for all exceptions,
        // we can extend it to take the required status as input
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

