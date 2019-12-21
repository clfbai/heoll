package com.boyu.erp.platform.usercenter.components;

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public  @interface FieldAuthority {

    String name() default "";

}