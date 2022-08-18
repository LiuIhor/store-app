package com.example.finalprojectstoreapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class DetailException {
    private final String massage;
    private final HttpStatus httpStatus;
    private final ZonedDateTime time;
}