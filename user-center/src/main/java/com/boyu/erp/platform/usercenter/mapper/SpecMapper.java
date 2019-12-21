package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 规格
 */
public interface SpecMapper {

    public List<Spec> selectAll(Spec spec);

    public List<Spec> getListSpecBySpecGrpId(String specGrpId);

    public Spec selectByPrimaryKey(Spec spec);

    public int insertSpec(Spec spec);

    public int updateSpec(Spec spec);

    public int deleteSpec(Spec spec);

    List<Spec> getAll();
    /**
     *
     * 功能描述: 批量查询规格号
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 20:19
     */
    List<Spec> getSpecList(@Param("list") List<ProductVo> list);
    /**
     * 根据规格Id集合查询规格集合
     * @author HHe
     * @date 2019/10/7 17:23
     */
    List<Spec> querySpecListByIds(@Param("specIds") Set<Long> specIds);
}