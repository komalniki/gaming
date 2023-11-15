package com.game.config;

import com.game.constants.AppConstants;
import com.game.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice(basePackages = AppConstants.CONTROLLER_BASE_PACKAGE)
public class Advice {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Response<Object>> handleException(Exception ex) {
        log.error("Exception Occurred: {}", ex);
        return new ResponseEntity<>(Response.failureResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> error = new HashMap<>();
        List<ObjectError> allErrors = result.getAllErrors();
        for (ObjectError allError : allErrors) {
            if (allError instanceof FieldError) {
                FieldError fieldError = (FieldError) allError;
                error.put(fieldError.getCode(), fieldError.getDefaultMessage());
            } else {
                error.put(allError.getCode(), allError.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(Response.failureResponseWithBody(error), HttpStatus.BAD_REQUEST);
    }
}