package com.linmaq.springboot.exception.design;

/**
 * @Class : DownStreamServerException
 * @author: marin
 * @date : 11:02 PM 8/4/2021
 */
public class DownStreamServerException extends RuntimeException {

    public DownStreamServerException(String message) {
        super(message);
    }
}
