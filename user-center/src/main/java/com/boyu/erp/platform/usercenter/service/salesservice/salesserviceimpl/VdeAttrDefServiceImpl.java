package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sales.VdeAttrDefMapper;
import com.boyu.erp.platform.usercenter.service.salesservice.VdeAttrDefService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 采购商属性定义接口实现类
 * @author: ck
 * @create: 2019-06-21 10:14
 */
@Slf4j
@Service
@Transactional
public class VdeAttrDefServiceImpl implements VdeAttrDefService {

    @Autowired
    private VdeAttrDefMapper vdeAttrDefMapper;
    @Autowired
    private SysCodeDtlService codeDtlService;


    @Override
    public int deleteByAttrType(VdeAttrDef record) throws ServiceException {
        return vdeAttrDefMapper.deleteByAttrType(record);
    }

    @Override
    public int insert(VdeAttrDef record) throws ServiceException {
        verificationKey(record);
        return vdeAttrDefMapper.insert(record);
    }

    @Override
    public int updateByAttrType(VdeAttrDef record) throws ServiceException {
        return vdeAttrDefMapper.updateByAttrType(record);
    }

    @Override
    public PageInfo<VdeAttrDef> getAllVdeAttrDefList(Integer page, Integer size, VdeAttrDef record) {
        PageHelper.startPage(page, size);
        List<VdeAttrDef> list = vdeAttrDefMapper.getAllVdeAttrDefList(record);
        PageInfo<VdeAttrDef> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdAttrDefVo> findByAll() {
        List<ProdAttrDefVo> prodAttrDefVos = vdeAttrDefMapper.getAll();
        List<ProdAttrDefVo> vos = new ArrayList<>();
        for (ProdAttrDefVo vo : prodAttrDefVos) {
            SysCodeDtl dtl = new SysCodeDtl();
            dtl.setCodeType(vo.getEdtFml());
            vo.setCodeDtls(codeDtlService.getAll(dtl));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 验证是否会主键
     */
    public void verificationKey(VdeAttrDef record) {
        List<VdeAttrDef> _recordList = vdeAttrDefMapper.getAllVdeAttrDefList(new VdeAttrDef());
        if (StringUtils.isNotBlank(record.getAttrType())
                && getLambda(record.getAttrType(), _recordList.stream().map(VdeAttrDef::getAttrType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "属性类别已存在");
        }
    }

    /**
     * Lambda 过滤集合判断 字符是否存在  需要不停进行上下文切换 适合并发
     */
    public boolean getLambda(String s, List<Object> list) {
        System.out.println("     " + s);
        boolean b = list.stream().anyMatch(p -> p.equals(s));
        System.out.println(b);
        return b;
    }
}
