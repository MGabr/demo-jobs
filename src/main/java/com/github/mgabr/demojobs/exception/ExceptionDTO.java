package com.github.mgabr.demojobs.exception;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ExceptionDTO {
    HttpStatus status;
    String message;
}
