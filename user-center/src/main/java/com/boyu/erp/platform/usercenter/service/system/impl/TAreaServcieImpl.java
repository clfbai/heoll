package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.TArea;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.system.TAreaMapper;
import com.boyu.erp.platform.usercenter.service.system.TAreaServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述:  地区服务
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/19 15:26
 */
@Slf4j
@Service
@Transactional
public class TAreaServcieImpl implements TAreaServcie {
    @Autowired
    private TAreaMapper tAreaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TArea> getTArea(TArea tArea) throws Exception{
        if(tArea.getLevel()==null){
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"请求参数不合法,查询级别ID为空");
        }
        if (tArea.getLevel() == 1) {
            tArea.setParentId(null);
        }
        return tAreaMapper.getTArtea(tArea);
    }
}
