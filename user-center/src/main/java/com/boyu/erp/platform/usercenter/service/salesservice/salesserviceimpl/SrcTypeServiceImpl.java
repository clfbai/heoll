package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
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
public class SrcTypeServiceImpl implements SrcTypeService {

    @Autowired
    private SrcTypeMapper srcTypeMapper;
    @Autowired
    private RtcTypeMapper rtcTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;

    @Override
    public int deleteBySrcType(SrcType srcType, SysUser user) throws ServiceException {
        //修改参数状态
        parameterMapper.updateByType(srcType.getSrcType()+ ParameterString.CREAT_TABLE_FILEDS,srcType.getSrcType()+ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");
        return srcTypeMapper.deleteBySrcType(srcType);
    }

    @Override
    public int insert(SrcType SrcType, SysUser user) throws Exception {
        verificationKey(SrcType);
        verificationVice(SrcType);
        RtcType rtcType = rtcTypeMapper.selectByPrimaryKey(SrcType.getRtcType());
        typeUtils.insertParameter(SrcType.getSrcType(),SrcType,rtcType,user,"src_type;vendee_id;");
        return srcTypeMapper.insert(SrcType);
    }

    @Override
    public int updateBySrcType(SrcType SrcType, SysUser user) throws Exception {
        verificationVice(SrcType);
        RtcType rtcType = rtcTypeMapper.selectByPrimaryKey(SrcType.getRtcType());
        typeUtils.insertParameter(SrcType.getSrcType(),SrcType,rtcType,user,"src_type;vendee_id;");
        return srcTypeMapper.updateBySrcType(SrcType);
    }

    @Override
    public PageInfo<SrcType> getSrcTypeList(Integer page, Integer size, SrcType SrcType) throws ServiceException {
        PageHelper.startPage(page, size);
        List<SrcType> list = srcTypeMapper.getSrcTypeList(SrcType);
        PageInfo<SrcType> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 验证退销合同类别是否重复
     */
    public void verificationKey(SrcType srcType) {
        List<SrcType> _recordList = srcTypeMapper.listBySrcType(srcType.getSrcType(),"");
        if (StringUtils.isNotBlank(srcType.getSrcType())
                && getLambda(srcType.getSrcType(), _recordList.stream().map(SrcType::getSrcType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "退销合同类别已存在");
        }
    }

    /**
     * 验证退货合同类别是否重复
     */
    public void verificationVice(SrcType srcType) {
        List<SrcType> _recordList = srcTypeMapper.listByRtcType(srcType.getSrcType(),srcType.getRtcType());
        if (PurchaseController.getLambda(srcType.getRtcType(), _recordList.stream().map(SrcType::getRtcType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "退货合同类别已存在");
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

    /**
     * 获取退销合同类别下拉
     */
    @Override
    public List<SrcType> optionGet() {
        return srcTypeMapper.getSrcTypeList(new SrcType());
    }

    @Override
    public List<PurKeyValue> purGet() {
        return srcTypeMapper.optionGet();
    }

    /**
     * 自动填充数据获取
     * @param srcType
     * @return
     */
    @Override
    public RtcAutoVo selectByRtcAuto(String srcType){
        return srcTypeMapper.selectByRtcAuto(srcType);
    }
}
