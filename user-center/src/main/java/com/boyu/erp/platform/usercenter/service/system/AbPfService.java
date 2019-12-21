package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.basic.AbPf;

import java.text.ParseException;
import java.util.Date;
/**
 * 账套控制表服务
 * @author HHe
 * @date 2019/10/21 16:20
 */
public interface AbPfService {
    /**
     * 记账前判断账套和会计日期合法性
     * @author HHe
     * @date 2019/10/21 16:19
     */
    AbPf precheck(Date fsclDate, Long fsclUnitId) throws ParseException;
}
