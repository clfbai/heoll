package com.boyu.erp.platform.usercenter.components;

import java.lang.annotation.*;

/**
 * @program: boyu-platform_text
 * @description: 操作权限注解
 * @author: clf
 * @create: 2019-07-03 11:37
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface OperationAuthority {
    String name() default "";

}
