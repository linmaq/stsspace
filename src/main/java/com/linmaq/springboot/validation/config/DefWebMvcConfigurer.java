package com.linmaq.springboot.validation.config;

import com.linmaq.springboot.validation.valiation.PayloadValidationArgProcess;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author marin
 */
@Component
public class DefWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PayloadValidationArgProcess());
    }
}