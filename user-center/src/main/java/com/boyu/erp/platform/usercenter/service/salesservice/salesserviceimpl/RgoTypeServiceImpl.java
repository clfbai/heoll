package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sales.RgoTypeMapper;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoTypeService;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 退销合同类别接口实现
 * @author: ck
 * @create: 2019-06-20 15:36
 */
@Slf4j
@Service
@Transactional
public class RgoTypeServiceImpl implements RgoTypeService {

    @Autowired
    private RgoTypeMapper rgoTypeMapper;

    public int deleteByRgoType(RgoType rgoType) throws ServiceException {
        return rgoTypeMapper.deleteByRgoType(rgoType);
    }

    public int insert(RgoType rgoType) throws ServiceException {
        verificationKey(rgoType);
        return rgoTypeMapper.insert(rgoType);
    }

    public int updateByRgoType(RgoType rgoType) throws ServiceException {
        return rgoTypeMapper.updateByRgoType(rgoType);
    }

    public PageInfo<RgoType> getRgoTypeList(Integer page, Integer size, RgoType rgoType) throws ServiceException {
        PageHelper.startPage(page, size);
        List<RgoType> list = rgoTypeMapper.getRgoTypeList(rgoType);
        PageInfo<RgoType> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 下拉框
     * @return
     */
    @Override
    public List<PurKeyValue> optionGet() {
        return rgoTypeMapper.optionGet();
    }

    /**
     * 选择调配单类别后查询
     * @param rgoType
     * @return
     */
    @Override
    public RgoType selectByRgoAuto(String rgoType) {
        return rgoTypeMapper.selectByRgoType(rgoType);
    }

    /**
     * 验证是否会主键
     */
    public void verificationKey(RgoType record) {
        List<RgoType> _recordList = rgoTypeMapper.getRgoTypeList(record);
        if (StringUtils.isNotBlank(record.getRgoType())
                && getLambda(record.getRgoType(), _recordList.stream().map(RgoType::getRgoType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "调配单类别已存在");
        }
    }

    /**
     * Lambda 过滤集合判断 字符是否存在  需要不停进行上下文切换 适合并发
     */
    public boolean getLambda(String s, List<Object> list) {
        boolean b = list.stream().anyMatch(p -> p.equals(s));
        return b;
    }
}
