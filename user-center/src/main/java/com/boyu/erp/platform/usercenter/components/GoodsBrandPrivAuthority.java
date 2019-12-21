package com.boyu.erp.platform.usercenter.components;

import java.lang.annotation.*;

/**
 * @program: boyu-platform
 * @description: 品牌操作权限
 * @author: liu yan
 * @create: 2019-05-22 14:41
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface GoodsBrandPrivAuthority {
    String name() default "";

}
