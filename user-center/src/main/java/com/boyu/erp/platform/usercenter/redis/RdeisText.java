package com.boyu.erp.platform.usercenter.redis;

import com.boyu.erp.platform.usercenter.redis.redisutils.RedisUtils;

import javax.annotation.Resource;

/**
 * @program: boyu-platform
 * @description: 测试Redis类
 * @author: clf
 * @create: 2019-06-09 11:52
 */

public class RdeisText {
    @Resource
    private RedisUtils rdeisUtils;

    public void mainssString() {

        System.out.println(rdeisUtils.get("k1"));
    }


}
