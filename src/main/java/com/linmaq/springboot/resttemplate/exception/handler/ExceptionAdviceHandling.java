package com.linmaq.springboot.resttemplate.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.linmaq.springboot.resttemplate.exception.design.DownStreamClientException;
import com.linmaq.springboot.resttemplate.exception.design.DownStreamServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class : ExceptionAdviceHandling
 * @author: marin
 * @date : 10:38 PM 8/4/2021
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandling {

    @ExceptionHandler(DownStreamClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object downStreamClientException() {
        return "downStreamClientException";
    }

    @ExceptionHandler(DownStreamServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object downStreamServerException() {
        return "downStreamServerException";
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, MismatchedInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object exception(MethodArgumentNotValidException ex) {
        log.debug("MethodArgumentNotValidException");
        return handlerNotValidException(ex);
    }

    private ResponseEntity<Map<String, Object>> handlerNotValidException(Exception ex) {
        log.debug("begin resolve argument exception");
        BindingResult result;
        if (ex instanceof BindException) {
            BindException exception = (BindException) ex;
            result = exception.getBindingResult();
        } else {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            result = exception.getBindingResult();
        }
        Map<String, Object> maps;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
                maps.put(error.getField(), error.getDefaultMessage());
            });
        } else {
            maps = Collections.EMPTY_MAP;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(maps);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.debug("HttpMessageNotReadableException");
        Map<String, Object> errorMsg = null;
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof JsonMappingException) {
            errorMsg = getDataBindError(ex);
        }
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg.put("errorMessage", "参数解析失败");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
    }

    private Map<String, Object> getDataBindError(HttpMessageNotReadableException e) {
        Throwable rootCause = e.getRootCause();
        List<JsonMappingException.Reference> path = ((JsonMappingException) rootCause).getPath();
        if (CollectionUtils.isEmpty(path)) {
            return null;
        }
        Map<String, Object> errors = new HashMap<>();
        for (JsonMappingException.Reference reference : path) {
            errors.put("errorField", reference.getFieldName());
            errors.put("errorMessage", e.getRootCause().getMessage().substring(0, e.getRootCause().getMessage().indexOf("at [Source:")).replace("\n ", "").trim());
        }
        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object exception(Exception ex) {
        log.error(ex.getClass().getName());
        return ex.getMessage();
    }
}
