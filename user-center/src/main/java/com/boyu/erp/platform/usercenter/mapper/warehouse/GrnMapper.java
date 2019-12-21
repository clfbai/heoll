package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Grn;
import com.boyu.erp.platform.usercenter.entity.warehouse.Money;
import com.boyu.erp.platform.usercenter.model.wareh.GrnModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnBaseDtlVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnGoodsDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;

import java.util.List;

public interface GrnMapper {
    int deleteByPrimaryKey(Grn key);

    int insertGrn(Grn grn);


    Grn selectByPrimaryKey(Grn key);


    int updateGrn(Grn grn);

    List<GrnVo> getGrnAll(GrnModel record);


    /**
     * 功能描述: 入库单明细查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 14:53
     */
    List<GrnGoodsDtl> getGoods(Grn grn);

    /**
     * 功能描述: 查询入库单基本明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 15:32
     */
    GrnBaseDtlVo getStbBaseDtl(Grn grn);
    /**
     *
     * 功能描述:  通过入库单编号和组织Id 查询入库单
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/1 16:04
     */
    Grn getGrnById(Grn grn);


     Money getGrnMoney(GrnModel grnModel);
}