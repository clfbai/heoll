package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDmode;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDmodeMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDmodeService;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WarehDmodeserviceImpl implements WarehDmodeService {
    @Autowired
    private WarehDmodeMapper warehDmodeMapper;
    @Autowired
    private SysParameterMapper parameterMapper;

    @Override
    public List<OptionVo> queryWDListById(Long warehId) throws ServiceException {
        List<OptionVo> warehDmodes = warehDmodeMapper.queryWDListById(warehId);
        if (warehDmodes.size() == 0) {
            throw new ServiceException("201", "仓库无出库方式");
        }
        return warehDmodes;
    }

    /**
     * 根据仓库id和出库方式查询对应stb中相应的属性
     *
     * @param warehId
     * @param delivMode
     * @return
     */
    @Override
    public GdnVO getStbDelivModeMess(Long warehId, String delivMode) {
        return warehDmodeMapper.getStbDelivModeMess(warehId, delivMode);
    }

    /**
     * 根据仓库id查询出库方式
     *
     * @param warehId
     * @return
     */
    @Override
    public List<Map<String, String>> getdelivModeByWarehId(Long warehId) {
        return warehDmodeMapper.getdelivModeByWarehId(warehId);
    }

    /**
     * 根据仓库编号查询出库方式
     *
     * @author HHe
     * @date 2019/8/23 12:10
     */
    @Override
    public List<Map<String, String>> loadDelivModeByWarehCode(Long warehId) {
        return warehDmodeMapper.loadDelivModeByWarehCode(warehId);
    }

    /**
     * 根据出库方式和仓库code查询出库方式
     *
     * @author HHe
     * @date 2019/8/23 16:35
     */
    @Override
    public WarehDmode queryAppointrcvMess(Long warehId, String delivMode) {
        return warehDmodeMapper.queryAppointrcvMess(warehId, delivMode);
    }


    /**
     * 判断出库方式和数据类型是否对应
     *
     * @author HHe
     * @date 2019/8/27 9:39
     */
    @Override
    public int judgeModeMapDocType(String delivMode, String srcDocType) {
        return warehDmodeMapper.judgeModeMapDocType(delivMode, srcDocType);
    }

    /**
     * 根据仓库Id和出库方式查询出库方式对象
     *
     * @author HHe
     * @date 2019/8/31 10:12
     */
    @Override
    public WarehDmode queryDmodeByWarehIdMode(Long warehId, String delivMode) {
        return warehDmodeMapper.queryDmodeByWarehIdMode(warehId, delivMode);
    }

    /**
     * 查询原始单据类别下拉
     *
     * @author HHe
     * @date 2019/11/15 15:54
     */
    @Transactional(readOnly = true)
    @Override
    public List<OperationVo> queryDocPullDown(String delivMode) {
        return warehDmodeMapper.queryDocPullDown(delivMode);
    }

    /**
     * 查询固定单价
     *
     * @param warehDmode 仓库Id、出库方式
     * @return 固定单价：T、F
     * @author HHe
     * @date 2019/11/29 14:31
     */
    @Override
    public String queryFixedUnitPriceByObj(WarehDmode warehDmode) {
        return warehDmodeMapper.queryFixedUnitPriceByObj(warehDmode);
    }

    /**
     * 功能描述: 增加默认入库方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 10:25
     */
    @Override
    public int addDefaultWarehDmode(Long warehId) {
        String paraId = "WarehDmodeRcvMode";
        String val = "SELL,TRA,PURT,ATAP,";
        List<WarehRmode> list = warehDmodeMapper.selectWareh(warehId);
        SysParameter ps = parameterMapper.findById(paraId);
        if (ps == null) {
            ps = new SysParameter(paraId, "出库方式初始化", val);
            parameterMapper.insertSelective(ps);
        } else {
            val = ps.getParmVal().trim();
            if (val.indexOf(",") <= 0) {
                throw new ServiceException("403", "仓库入库方式默认值设置有误请以 ， 分割");
            }
        }
        if (CollectionUtils.isEmpty(list)) {
            List<WarehDmode> rmodes = new ArrayList<>();
            for (String s : val.split(",")) {
                WarehDmode warehDmode = new WarehDmode();
                warehDmode.setWarehId(warehId);
                warehDmode.setDelivMode(s);
                warehDmode.setAutoExec("F");
                warehDmode.setBoxReqd("F");
                warehDmode.setBoxSchd("F");
                warehDmode.setPickReqd("F");
                warehDmode.setRckReqd("F");
                warehDmode.setDiffMtrd("F");
                warehDmode.setDiffCtrl("N");
                warehDmode.setMnlRwd("F");
                warehDmode.setInstStl("F");
                warehDmode.setFixedUnitPrice("T");
                warehDmode.setRcvUnitReqd("F");
                warehDmode.setRcvWarehReqd("F");
                rmodes.add(warehDmode);
            }
            warehDmodeMapper.addRmodeList(rmodes);
        }
        return 0;
    }
}
