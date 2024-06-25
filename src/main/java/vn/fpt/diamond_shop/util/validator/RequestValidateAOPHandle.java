package vn.fpt.diamond_shop.util.validator;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import vn.fpt.diamond_shop.util.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;


/**
 * The type Request validate aop handle.
 */
//@Aspect
//@Component
//@PropertySource(
//        //update bundle dir
//        value = "classpath:rsrc/main/resources/message.properties",
//        encoding = "UTF-8",
//        name = "messageCode"
//)

@Slf4j
public class RequestValidateAOPHandle {
    @Autowired
    private Environment env;

    //mapping with assigned bundle name in PropertySource annotation
    private static final String BUNDLE_NAME = "messageCode";

    @Around("execution(* *(..)) && @annotation(validateAnnotation)")
    public Object validateAnnotation(
            ProceedingJoinPoint point, ServiceValidate validateAnnotation)
            throws Throwable {

        Optional<Object> findingBindingResulField = Stream.of(point.getArgs())
                .filter(BindingResult.class::isInstance)
                .findFirst();

        if (!findingBindingResulField.isPresent()) {
            if (point.getSignature() instanceof MethodSignature) {
                log.warn("Method {} does not have BindingResult parameter", point.getSignature().getName());
            }
            return point.proceed();
        }

        BindingResult bindingResult = (BindingResult) findingBindingResulField.get();
        org.springframework.core.env.PropertySource<?> dfsErrorSource =
                ((AbstractEnvironment) env).getPropertySources().get(BUNDLE_NAME);
        Properties props = new Properties();
        if (dfsErrorSource != null) {
            props = (Properties) dfsErrorSource.getSource();
        }
        if (bindingResult.hasErrors()) {
            List<InvalidProperties> invalidProperties = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String message = fieldError.getDefaultMessage();
                if (NumberUtils.isDigits(message)) {
                    message = props.getProperty(message);
                }
                invalidProperties.add(new InvalidProperties(fieldError.getField(), message));
            }
            return BaseResponse.responseError(invalidProperties);
        } else {
            return point.proceed();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvalidProperties {
        private String property;
        private String message;
    }
}
