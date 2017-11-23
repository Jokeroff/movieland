package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(IllegalArgumentException e) {
        LOG.error("Error handled in GlobalExceptionHandler: " + e);
        String json = toJson(e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.add("JsonHeader", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity(json, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(JsonProcessingException e) {
        LOG.error("Error handled in GlobalExceptionHandler: " + e);
        String json = toJson(e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.add("JsonHeader", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity(json, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(SecurityException e) {
        LOG.error("Error handled in GlobalExceptionHandler: " + e);
        String json = toJson(e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.add("JsonHeader", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity(json, headers, HttpStatus.BAD_REQUEST);
    }
}
