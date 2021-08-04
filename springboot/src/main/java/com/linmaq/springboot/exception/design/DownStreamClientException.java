package com.linmaq.springboot.exception.design;

/**
 * @Class : DownStreamClientException
 * @author: marin
 * @date : 11:02 PM 8/4/2021
 */
public class DownStreamClientException extends RuntimeException {
    public DownStreamClientException(String message) {
        super(message);
    }
}
