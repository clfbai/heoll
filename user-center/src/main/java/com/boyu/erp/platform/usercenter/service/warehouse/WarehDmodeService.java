package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnVO;

import java.util.List;
import java.util.Map;

public interface WarehDmodeService {

    List<OptionVo> queryWDListById(Long warehId) throws ServiceException;

    /**
     * 根据仓库id和出库方式查询对应stb中相应的属性
     *
     * @param warehId
     * @param delivMode
     * @return
     */
    GdnVO getStbDelivModeMess(Long warehId, String delivMode);

    /**
     * 根据仓库id查询出库方式
     *
     * @param warehId
     * @return
     */
    List<Map<String, String>> getdelivModeByWarehId(Long warehId);

    /**
     * 根据仓库编号查询出库方式
     *
     * @author HHe
     * @date 2019/8/23 12:10
     */
    List<Map<String, String>> loadDelivModeByWarehCode(Long warehId);

    /**
     * 根据出库方式和仓库code查询出库方式
     *
     * @author HHe
     * @date 2019/8/23 16:35
     */
    WarehDmode queryAppointrcvMess(Long warehId, String delivMode);

    /**
     * 判断出库方式和数据类型是否对应
     *
     * @author HHe
     * @date 2019/8/27 9:39
     */
    int judgeModeMapDocType(String delivMode, String srcDocType);

    /**
     * 根据仓库Id和出库方式查询出库方式对象
     *
     * @author HHe
     * @date 2019/8/31 10:12
     */
    WarehDmode queryDmodeByWarehIdMode(Long warehId, String delivMode);

    /**
     * 查询原始单据类别下拉
     *
     * @author HHe
     * @date 2019/11/15 15:54
     */
    List<OperationVo> queryDocPullDown(String delivMode);

    /**
     * 查询固定单价
     *
     * @param warehDmode 仓库Id、出库方式
     * @return 固定单价：T、F
     * @author HHe
     * @date 2019/11/29 14:31
     */
    String queryFixedUnitPriceByObj(WarehDmode warehDmode);

    /**
     * 功能描述: 增加默认入库方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 10:25
     */
    int addDefaultWarehDmode(Long warehId);

}
