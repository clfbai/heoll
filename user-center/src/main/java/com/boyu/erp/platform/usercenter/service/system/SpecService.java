package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * 规格
 */
public interface SpecService {

    public PageInfo<Spec> selectAll(Integer pageNum, Integer pageSize, Spec spec) throws ServiceException;

    /**
     * 根据规格组ID获取规格列表
     *
     * @param specGrpId
     * @return
     * @throws ServiceException
     */
    public List<Spec> getListSpecBySpecGrpId(String specGrpId) throws ServiceException;

    /**
     * 获取下拉框选择规格
     *
     * @param spec
     * @return
     * @throws ServiceException
     */
    public List<Spec> getOptionsList(Spec spec) throws ServiceException;

    public Spec getSpecById(Spec spec) throws ServiceException;

    public int insertSpec(Spec spec) throws ServiceException;

    public int updateSpec(Spec spec) throws ServiceException;

    public int deleteSpec(Spec spec) throws ServiceException;

    List<Spec> getAll();

    /**
     * 功能描述:  批量查询规格
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 20:19
     */
    List<Spec> getSpecList(List<ProductVo> list);
    /**
     * 根据规格Id集合查询规格集合
     * @author HHe
     * @date 2019/10/7 17:21
     */
    List<Spec> querySpecListByIds(Set<Long> specIds);
}
