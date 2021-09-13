package com.linmaq.springboot.validation.annotation;

import java.lang.annotation.*;

/**
 * @Class : ValidationPayload
 * @author: marin
 * @date : 21:33 2021/9/13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ValidationPayload {
    boolean required() default true;
}
