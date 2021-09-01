package com.linmaq.springboot.resttemplate.exception.handler;

import com.linmaq.springboot.resttemplate.exception.design.DownStreamClientException;
import com.linmaq.springboot.resttemplate.exception.design.DownStreamServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * @Class : RestTemplateErrorHandling
 * @author: marin
 * @date : 10:39 PM 8/4/2021
 */
public class RestTemplateErrorHandling extends DefaultResponseErrorHandler {
    ///**
    // * 1. get HttpStatus from response
    // * 2. get error message details from response
    // *  refer the source code:
    // *  protected byte[] getResponseBody(ClientHttpResponse response) {
    // *         try {
    // *             return FileCopyUtils.copyToByteArray(response.getBody());
    // *         } catch (IOException ex) {
    // *             // ignore
    // *         }
    // *         return new byte[0];
    // *     }
    // * 3. throw different Exception within different error message
    // *  refer the source code:
    // *  protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
    // *       ...
    // *         byte[] body = getResponseBody(response);
    // *         ...
    // *         String message = getErrorMessage(statusCode.value(), statusText, body, charset);
    // *
    // *         switch (statusCode.series()) {
    // *             case CLIENT_ERROR:
    // *                 throw HttpClientErrorException.create(message, statusCode, statusText, headers, body, charset);
    // *             case SERVER_ERROR:
    // *                 throw HttpServerErrorException.create(message, statusCode, statusText, headers, body, charset);
    // *             default:
    // *                 throw new UnknownHttpStatusCodeException(message, statusCode.value(), statusText, headers, body, charset);
    // *         }
    // *     }
    // *
    // * @param response
    // * @throws IOException
    // */

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        byte[] bytes = FileCopyUtils.copyToByteArray(response.getBody());
        String bodyInfos = new String(bytes);

        switch (statusCode.series()) {
            case CLIENT_ERROR:
                throw new DownStreamClientException(bodyInfos);
            case SERVER_ERROR:
                throw new DownStreamServerException(bodyInfos);
            default:
                return;
        }
    }
}
