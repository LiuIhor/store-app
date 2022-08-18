package com.example.finalprojectstoreapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final String ZONE_ID = "UTC";

    @ExceptionHandler(value = CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DetailException handleCustomException(CustomException exception) {
        return new DetailException(exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID)));
    }

    @ExceptionHandler(value = AuthenticationFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DetailException handleAuthenticationFailException(AuthenticationFailException exception) {
        return new DetailException(exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID)));
    }

    @ExceptionHandler(value = ProductNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DetailException handleProductNotExistException(ProductNotExistException exception) {
        return new DetailException(exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID)));
    }
}
