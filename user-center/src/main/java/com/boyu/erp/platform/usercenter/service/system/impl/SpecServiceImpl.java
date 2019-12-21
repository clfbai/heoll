package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.SpecMapper;
import com.boyu.erp.platform.usercenter.service.system.SpecService;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

/**
 * 规格的实现类
 */
@Service
@Transactional
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Spec> selectAll(Integer pageNum, Integer pageSize, Spec spec)  throws ServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<Spec> specs = specMapper.selectAll(spec);
        PageInfo<Spec> pageInfo = new PageInfo<>(specs);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Spec getSpecById(Spec spec) throws ServiceException {
        return specMapper.selectByPrimaryKey(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spec> getListSpecBySpecGrpId(String specGrpId) throws ServiceException {

        if(StringUtils.isBlank(specGrpId))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"请求参数不合法,规格组ID为空");
        }
        List<Spec> listSpec=this.specMapper.getListSpecBySpecGrpId(specGrpId);
        return listSpec;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Spec> getOptionsList(Spec spec) throws ServiceException {
        List<Spec> specList=this.specMapper.selectAll(spec);
        return specList;
    }

    @Override
    public int insertSpec(Spec spec) throws ServiceException {
        return specMapper.insertSpec(spec);
    }


    @Override
    public int updateSpec(Spec spec) throws ServiceException{
        return specMapper.updateSpec(spec);
    }

    @Override
    public int deleteSpec(Spec spec) throws ServiceException{
        return specMapper.deleteSpec(spec);
    }

    @Override
    public List<Spec> getAll() {
        return specMapper.getAll();
    }
    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 20:19
     */
    @Override
    public List<Spec> getSpecList(List<ProductVo> list) {
        return specMapper.getSpecList(list);
    }
    /**
     * 根据规格Id集合查询规格集合
     * @author HHe
     * @date 2019/10/7 17:22
     */
    @Override
    public List<Spec> querySpecListByIds(Set<Long> specIds) {
        return specMapper.querySpecListByIds(specIds);
    }
}