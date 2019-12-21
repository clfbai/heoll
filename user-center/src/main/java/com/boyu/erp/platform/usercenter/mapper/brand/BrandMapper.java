package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.brand.Brand;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.vo.BrandVo;
import com.boyu.erp.platform.usercenter.vo.system.BrandWindowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {

    //查询所有品牌
    public List<BrandVo> selectAll(Brand brand);

    //查询组织品牌
    public List<BrandVo> selectUnitAll(Brand brand);

    //用户查询品牌
    public List<BrandVo> selectUserAll(Brand brand);

    public Brand selectByPrimaryKey(Long brandId);

    public int updateByPrimaryKeySelective(Brand record);

    public int insertSelective(Brand record);


    List<Brand> userOperation(Long unitId);

    List<Brand> optionList();

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/19 16:03
     */
    List<BrandWindowVo> getBrandWindow(Brand brand);

     /**
      *
      * 功能描述: 通过品牌集合查询商品品种(只需要 prodClsId 和prodClsCode)
      *
      * @param:
      * @return:
      * @auther: CLF
      * @date: 2019/9/26 19:45
      */
    List<ProdClsModel> selectProdCls(@Param("list") List<Brand> list);
}