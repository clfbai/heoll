package com.boyu.erp.platform.usercenter.components;

import java.lang.annotation.*;

/**
 * @program: boyu-platform
 * @description: 日志注解
 * @author: clf
 * @create: 2019-06-03 14:39
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String name() default "";
}
