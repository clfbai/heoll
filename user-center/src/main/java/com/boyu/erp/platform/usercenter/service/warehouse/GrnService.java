package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.TPOS.common.confirm.WarehSingConfirm;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Grn;
import com.boyu.erp.platform.usercenter.entity.warehouse.Money;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.model.wareh.GrnModel;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnBaseDtlVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnGoodsDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 入库单接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface GrnService {


    Grn selectGrn(Grn grn);

    PageInfo<GrnVo> getGrnList(Integer page, Integer size, GrnModel grnVo) throws ServiceException;

    int update(GrnVo grnVo) throws ServiceException;

    int delete(Grn grn) throws ServiceException;

    /**
     * 功能描述:  入库单 商品明细查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 15:22
     */
    List<GrnGoodsDtl> getStbGoodsDtl(Grn grn) throws ServiceException;

    /**
     * 功能描述:  入库单 基本明细查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 15:22
     */
    @Deprecated
    GrnBaseDtlVo getStbBaseDtl(Grn grn) throws ServiceException;

    /**
     * 功能描述: 手动填写入库单(可以选择关联源单据信息)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/1 12:16
     */
    int addGrnDtl(GrnDtlModel model, SysUser user) throws ServiceException;


    Grn getGrnId(Grn grnNum);

    /**
     * 功能描述: 入库操作
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 15:40
     */
    int warehouSing(Long unitId, String grnNum, SysUser user) throws Exception;

    /**
     * 入库任务添加入库单
     */
    int addGrn(GrnDtlModel model, SysUser user) throws ServiceException;

    /**
     * 判断入库单能否修改
     */
    Boolean isUpdateGrn(Grn grn);

    /**
     * 入库单按钮初始化
     */
    List<OperationVo> initButtons(Grn model);

    /**
     * 入库单改变状态
     */
    int changeStatuss(Grn model);

    /**
     * 功能描述: 取消入库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 19:30
     */
    int warehouReverse(Grn model, SysUser user) throws Exception;

    /**
     * C-WMS 入库单确认接口
     */
    void warehSingConfirm(WarehSingConfirm warehSingConfirm) throws Exception;

    Money selectMoney(GrnModel grnModel);
}
