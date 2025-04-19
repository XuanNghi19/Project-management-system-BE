package com.dmm.projectManagementSystem.utils.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMessageResponse {
    @AliasFor("value")
    String message() default "";

    @AliasFor("message")
    String value() default "";
}
