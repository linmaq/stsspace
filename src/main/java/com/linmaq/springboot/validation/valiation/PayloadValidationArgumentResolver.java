package com.linmaq.springboot.validation.valiation;

import com.linmaq.springboot.validation.annotation.ValidationPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Class : PayloadValidationArgProcess
 * implements HandlerMethodArgumentResolver
 * @author: marin
 * @date : 21:34 2021/9/13
 */
@Slf4j
public class PayloadValidationArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ValidationPayload.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String body = getRequestBody(webRequest);
        String jsonBody = String.valueOf(webRequest.getAttribute(JSON_REQUEST_BODY, NativeWebRequest.SCOPE_REQUEST));
        log.info(jsonBody);
        return body;
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (jsonBody == null) {
            try {
                String body = IOUtils.toString(servletRequest.getInputStream());
                servletRequest.setAttribute(JSON_REQUEST_BODY, body);
                return body;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
