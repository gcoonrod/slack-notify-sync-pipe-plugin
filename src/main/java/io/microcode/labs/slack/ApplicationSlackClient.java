/*
 * Copyright 2019 Ping Identity Corporation
 * All Rights Reserved
 */
package io.microcode.labs.slack;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BindingAnnotation
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationSlackClient {
  SlackClientType value() default SlackClientType.BASIC;
}
