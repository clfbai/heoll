package com.boyu.erp.platform.usercenter.components;

import java.lang.annotation.*;

/**
 * @program: boyu-platform_text
 * @description: 修改字段权限注解
 * @author: clf
 * @create: 2019-06-24 18:44
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateTableFile {

    String name() default "";
}
