package com.linmaq.springboot.valid.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @Class : ValidatorConfig
 * @author: marin
 * @date : 22:20 2021/9/12
 */

@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "false")
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        /**设置validator模式为快速失败返回*/
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }
    //     @Autowired
    //    private MessageSource messageSource;
    //@Bean
    //public Validator validator(){
    //    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    //    factoryBean.setMessageInterpolator(new MessageInterpolatorFactory().getObject());
    //    factoryBean.setValidationMessageSource(messageSource);
    //    return factoryBean;
    //}
}