package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.config.PrivConfig.TablePriv;
import com.boyu.erp.platform.usercenter.redis.redisutils.RedisUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 表字段权限加载到redis工具类
 * @author: clf
 * @create: 2019-06-13 15:27
 */
@Slf4j
@Component
public class TablePrivRedisUtils {

    @Autowired
    private RedisUtils redisUtils;

    public void PrivReis() throws Exception {
        if (!redisUtils.hasKey(CommonFainl.TABLE_PRIV)) {
            boolean bo = redisUtils.hmset(CommonFainl.TABLE_PRIV, TablePriv.getInstance().init());
            if (bo) {
                log.info("成功摄入 redis");
            }
        }
        Map<Object, Object> ms = getPrivTable();

        if (TablePriv.TablePrivFiledsSize() != ms.size()) {
            redisUtils.hmset(CommonFainl.TABLE_PRIV, TablePriv.getInstance().init());
        }

        for (Object o : getPrivTable().keySet()) {
            log.info("需要过滤表：" + ms.get(o));
        }
    }

    public Map<Object, Object> getPrivTable() {
        return redisUtils.hmget(CommonFainl.TABLE_PRIV);
    }

}
