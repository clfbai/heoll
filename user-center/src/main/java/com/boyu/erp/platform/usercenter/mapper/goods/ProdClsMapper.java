package com.boyu.erp.platform.usercenter.mapper.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.mq.product.ProductMessage;
import com.boyu.erp.platform.usercenter.model.ProdClsdText;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdStkAdopte;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program:
 * @description: 商品品种属性mapper
 * @author: CLF
 * @create:
 */
public interface ProdClsMapper {

    int deleteKey(Long prodClsId);

    int insert(ProdClsWithBLOBs record);

    int insertSelective(ProdClsWithBLOBs record);

    ProdCls selectByKey(Long prodClsId);

    ProdCls findByCode(ProdCls record);

    int updateByPrimaryKeySelective(ProdClsWithBLOBs record);


    /**
     * 系统管理员
     */
    List<ProdClsVo> selectList(ProdClsModel prodClsWithBLOBs);

    /**
     * 普通管理员或用户（优化)
     */
    List<ProdClsVo> selectGetList(ProdClsModel prodClsWithBLOBs);

    /**
     * 查询商品品种 code 增量
     */
    List<ProdCls> getCode(ProdCls prodClsWithBLOBs);

    /**
     * 根据品牌Id集合查询品种集合
     *
     * @author HHe
     * @date 2019/9/19 11:21
     */
    List<Long> queryProdClsIdByBrandIdList(@Param("brandIdList") List<Long> brandIdList);

    /**
     * 根据分类Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 15:15
     */
    List<Long> queryProdClsIdByProdCatIdList(@Param("prodCatIdList") List<String> prodCatIdList);

    /**
     * 根据品牌Id集合和分类Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 15:54
     */
    List<Long> queryProdClsIdByProdCatIdListAndBrandIdList(@Param("prodCatIdList") List<String> prodCatIdList, @Param("brandIdList") List<Long> brandIdList);

    /**
     * 弹窗查询
     */
    ProdCls selectObject(CommonWindowModel model);

    /**
     * 功能描述: 查询商品品种id生成商品消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/24 17:15
     */
    ProductMessage selectProdClsId(ProdCls prodCls);

    /**
     * 功能描述: 修改审核状态
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/25 19:42
     */
    int updateAuditType(ProdCls cls);

    /**
     * 功能描述: 查询当前组织所有商品品种
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:28
     */
    List<ProdClsVo> selectUnitProdCls(ProdClsModel model);

    /**
     * 查询其他组织共享商品
     */
    List<ProdClsVo> selectUnitProdClsOther(ProdClsModel model);

    /**
     * 所有其他组织共享加当前组织商品
     */
    List<ProdClsVo> selectUnitProdClsAll(ProdClsModel model);


    /**
     * 查询指定组织共享或所有持有(管理)商品
     */
    List<ProdClsModel> selectShared(Long unitId);


    /**
     * 根据品种Id集合查询品种集合
     *
     * @author HHe
     * @date 2019/10/7 16:24
     */
    List<ProdCls> queryProdClsListByIds(@Param("prodClsIds") Set<Long> prodClsIds);

    /**
     * 供应商供货信息商品弹窗
     *
     * @param vo
     * @return
     */
    List<DtlProdVo> selectByVdrSu(DtlProdVo vo);

    /**
     * 供应商供货输入商品代码判断商品是否存在
     *
     * @param vo
     * @return
     */
    DtlProdVo getProdCode(DtlProdVo vo);

    /**
     * 功能描述: 查询商品能否禁用库存管理
     * (商品在库存表中是否有记录)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 16:45
     */
    List<ProdStkAdopte> selectProdClsStkAdopte(Long prodClsId);

    /**
     * 功能描述:  上传cwms平台商品明细集合查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/26 11:30
     */
    List<Map<String, Object>> selectCwmsUploading(ProdCls prodCls);


    ProdClsdText getOnes(Long prodClsId);


}