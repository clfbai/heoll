package com.boyu.erp.platform.usercenter.service.common.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:
 *
 * @Description: 按钮初始服务
 * @auther: CLF
 * @date: 2019/9/29 11:48
 */
@Slf4j
@Component
@Transactional
public class ButtonCommonService {
    //按钮 代码codeType
    public static final String BILL_FUNCTION = "BILL_FUNCTION";
    @Autowired
    private SysParameterService parameterService;
    @Autowired
    private SysCodeDtlService codeDtlService;

    /**
     * 功能描述:  初始化模块按钮
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/29 11:17
     */
    public List<PurKeyValue> initButton(String parameter) throws ServiceException {
        List<PurKeyValue> returnList = new ArrayList<>();
        SysParameter pas = verifyParameter(parameter);
        //规定按钮参数值已  ; 隔开
        String[] buttons = pas.getParmVal().split(";");
        List<PurKeyValue> list = getCode(BILL_FUNCTION);
        //数组转集合
        // List<String> ls = Arrays.asList(buttons);
        for (String s : buttons) {
            for (PurKeyValue keyValue : list) {
                if (keyValue.getOptionValue().equalsIgnoreCase(s)) {
                    returnList.add(keyValue);
                }
            }
        }
        return returnList;
    }

    /**
     * 功能描述: 检查代码是否存在
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/29 11:41
     */
    private List<PurKeyValue> getCode(String billFunction) {
        List<PurKeyValue> list = codeDtlService.optionGet(BILL_FUNCTION);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("403", "检查代码:" + billFunction + "是否在数据库存在");
        }
        return list;
    }


    /**
     * 功能描述: 检查参数是否存在和参数设置是否正确
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/29 11:26
     */
    private SysParameter verifyParameter(String parameter) throws ServiceException {
        SysParameter pas = parameterService.findByParameter(parameter);
        if (pas == null) {
            throw new ServiceException("403", "检查参数Id:" + parameter + "是否在数据库存在");
        }
        if (pas.getParmVal().indexOf(";") < 0) {
            throw new ServiceException("409", "检查参数Id:" + parameter + "对应值是否设置正确");
        }
        return pas;
    }
}
