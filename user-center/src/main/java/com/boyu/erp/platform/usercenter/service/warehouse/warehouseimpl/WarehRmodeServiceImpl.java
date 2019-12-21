package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRmodeMapper;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRmodeMode;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRmodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 仓库入库方式服务
 * @author: ck
 * @create: 2019-06-20 15:36
 */
@Slf4j
@Service
@Transactional
public class WarehRmodeServiceImpl implements WarehRmodeService {

    @Autowired
    private WarehRmodeMapper warehRmodeMapper;

    @Autowired
    private WarehMapper warehMapper;
    @Autowired
    private SysParameterMapper parameterMapper;

    /**
     * 仓库入库方式
     *
     * @param warehRmode
     * @return
     * @throws ServiceException
     */
    @Override
    public List<WarehRmode> selectWarehRomde(WarehRmode warehRmode) throws ServiceException {
        return warehRmodeMapper.selectWarehRmode(warehRmode);
    }

    @Override
    public WarehRmode selectWarehRmode(Long warehId, String rvcRmode) throws ServiceException {
        if (warehId == null || StringUtils.isBlank(rvcRmode))
            throw new ServiceException("403", "检查仓库Id、入库方式不能为空");
        WarehRmode key = new WarehRmode(warehId, rvcRmode);
        return warehRmodeMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateWarehRomde(WarehRmodeMode warehRmodeMode) throws ServiceException {
        return warehRmodeMapper.updateWarehRmodeMode(warehRmodeMode);
    }

    @Override
    public int insertWarehRomde(WarehRmode warehRmode) throws ServiceException {
        return warehRmodeMapper.insertSelective(warehRmode);
    }

    @Override
    public int deleteWarehRomde(WarehRmode warehRmode) throws ServiceException {
        return warehRmodeMapper.deleteByPrimaryKey(warehRmode);
    }

    /**
     * 功能描述: 变更操作查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/22 17:06
     */
    @Override
    public Map<String, String> changeFunctionList(Wareh wareh) throws ServiceException {
        Wareh warehs = warehMapper.selectByWarehId(wareh.getWarehId());
        if (warehs == null) {
            throw new ServiceException("403", "请选择仓库");
        }
        Map<String, String> map = new HashMap<>();
        //库存管理
        map.put("stkAdopted", warehs.getStkAdopted());
        //唯一码管理
        map.put("uidAdopted", warehs.getUidAdopted());
        //装箱库存管理
        map.put("bstAdopted", warehs.getBstAdopted());
        //货位管理
        map.put("locAdopted", warehs.getLocAdopted());
        return map;
    }

    @Override
    public int changeFunctionUpdate(Wareh warehRmode) {

        return warehMapper.updateByWareh(warehRmode);
    }

    /**
     * 功能描述: 添加仓库时默认添加入库方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 10:14
     */
    @Override
    public void addDefaultWarehRmode(Long warehId) {
        String paraId = "WarehRmodeRcvMode";
        String val = "PURC,TRA,DIPU,SLRT,PRES,";
        List<WarehRmode> list = warehRmodeMapper.selectWareh(warehId);
        SysParameter ps = parameterMapper.findById(paraId);
        if (ps == null) {
            ps = new SysParameter(paraId, "入库方式初始化", val);
            parameterMapper.insertSelective(ps);
        } else {
            val = ps.getParmVal().trim();
            if (val.indexOf(",") <= 0) {
                throw new ServiceException("403", "仓库入库方式默认值设置有误请以 ， 分割");
            }
        }
        if (CollectionUtils.isEmpty(list)) {
            List<WarehRmode> rmodes = new ArrayList<>();
            for (String s : val.split(",")) {
                WarehRmode warehRmode = new WarehRmodeMode();
                warehRmode.setWarehId(warehId);
                warehRmode.setRcvMode(s);
                setWarehRmode(warehRmode);
                rmodes.add(warehRmode);
            }
            warehRmodeMapper.addRmodeList(rmodes);
        }
    }


    public void setWarehRmode(WarehRmode warehRmode) {
        warehRmode.setAutoExec("F");
        warehRmode.setBoxReqd("T");
        warehRmode.setBoxSchd("F");
        warehRmode.setAcptReqd("F");
        warehRmode.setPaReqd("F");
        warehRmode.setDiffMtrd("F");
        warehRmode.setDiffCtrl("N");
        warehRmode.setMnlRwd("F");
        warehRmode.setInstStl("F");
        warehRmode.setFixedUnitPrice("T");
        warehRmode.setDelivUnitReqd("F");
        warehRmode.setDelivWarehReqd("F");
    }
}


