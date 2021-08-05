package com.linmaq.springboot.resttemplate.exception.handler;

import com.linmaq.springboot.resttemplate.exception.design.DownStreamClientException;
import com.linmaq.springboot.resttemplate.exception.design.DownStreamServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Class : ExceptionAdviceHandling
 * @author: marin
 * @date : 10:38 PM 8/4/2021
 */
@RestControllerAdvice
public class ExceptionAdviceHandling {

    @ExceptionHandler(DownStreamClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object downStreamClientException() {
        return null;
    }

    @ExceptionHandler(DownStreamServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object downStreamServerException() {
        return null;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object exception() {
        return null;
    }
}
