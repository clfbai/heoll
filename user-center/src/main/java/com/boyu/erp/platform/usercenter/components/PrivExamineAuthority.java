package com.boyu.erp.platform.usercenter.components;

import java.lang.annotation.*;

/**
 * @Description: 权限检查切面(所有操作权限 、 和跨组织操作权限检查)
 * @auther: CLF
 * @date: 2019/8/26 10:46
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface PrivExamineAuthority {

    String privId() default "";
}
