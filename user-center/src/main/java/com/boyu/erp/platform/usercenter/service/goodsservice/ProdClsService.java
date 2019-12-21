package com.boyu.erp.platform.usercenter.service.goodsservice;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * @program: boyu-platform
 * @description: 商品品种接口
 * @author: liu yan
 * @create: 2019-06-10 10:56
 */
public interface ProdClsService extends BaseService {


    int instrProdCls(ProdClsVo prodClsVo, SysUser user) throws Exception;

    int updateByProdCls(ProdCls cls);

    int updateProdCls(ProdClsVo prodClsVo, SysUser user);

    int deleteprodCls(ProdClsVo prodClsVo, SysUser us);

    ProdCls findByProdCls(ProdCls p);


    /**
     * 系统管理员查看
     */
    PageInfo<ProdClsVo> getProdClsList(Integer page, Integer size, ProdClsModel prodClsWithBLOBs, SysUser user) throws Exception;

    /**
     * 根据id查询品种
     *
     * @author HHe
     * @date 2019/8/27 14:05
     */
    ProdCls queryProdClsById(ProdCls prodCls2Id);

    /**
     * 根据品牌Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 11:20
     */
    List<Long> queryProdClsIdByBrandIdList(List<Long> brandIdList);

    /**
     * 根据分类Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 15:13
     */
    List<Long> queryProdClsIdByProdCatIdList(List<String> prodCatIdList);

    /**
     * 根据品牌Id集合和分类Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 15:54
     */
    List<Long> queryProdClsIdByProdCatIdListAndBrandIdList(List<String> prodCatIdList, List<Long> brandIdList);

    /**
     * 功能描述: 查询当前组织商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:24
     */
    PageInfo<ProdClsVo> getUnitProdClsList(Integer page, Integer size, ProdClsModel model, SysUser sysUser) throws Exception;

    /**
     * 功能描述: 查询其他组织共享商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:35
     */
    PageInfo<ProdClsVo> getOtherUnitProdClsList(Integer page, Integer size, ProdClsModel model, SysUser sysUser) throws Exception;


    /**
     * 查询商品品种能否审核
     * true 能
     */
    boolean isAuditType(ProdClsModel model);

    /**
     * 查询商品品种能否反审
     * true 能
     */
    boolean getNotAuditType(ProdClsModel m);

    /**
     * 功能描述: 单纯修改商品品种表
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 20:45
     */
    int updateProductCls(ProdClsWithBLOBs cls, SysUser user);

    /**
     * 功能描述: 查询商品品种按钮
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/29 16:12
     */
    List<OperationVo> initButtons(ProdCls model);

    /**
     * 根据品种Id集合查询品种集合
     *
     * @author HHe
     * @date 2019/10/7 16:22
     */
    List<ProdCls> queryProdClsListByIds(Set<Long> prodClsIds);

    /**
     * 功能描述: 查询商品品种能否禁用库存管理
     *
     * @param prodClsId
     * @return true (能)
     * @auther: CLF
     * @date: 2019/11/12 16:41
     */
    Boolean selectProdClsStkAdopte(Long prodClsId);

}
