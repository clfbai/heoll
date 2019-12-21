package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysCode;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.CostRmode;
import com.boyu.erp.platform.usercenter.mapper.warehouse.CostRmodeMapper;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeService;
import com.boyu.erp.platform.usercenter.service.warehouse.CostRmodeService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CostRmodeServiceImpl implements CostRmodeService {
    @Autowired
    private CostRmodeMapper costRmodeMapper;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private SysCodeService codeService;

    @Override
    public int addCostRmode(CostRmode costRmode) {
        return costRmodeMapper.insert(costRmode);
    }

    @Override
    public int deleteCostRmode(CostRmode costRmode) {
        return costRmodeMapper.deleteByPrimaryKey(costRmode);
    }

    @Override
    public List<CostRmode> selectUnitCostRmode(Long unitId) {
        return costRmodeMapper.selectUnitId(unitId);
    }

    @Override
    public CostRmode selectCostRmode(CostRmode costRmode) {
        return costRmodeMapper.selectCostRmode(costRmode);
    }

    /**
     * 功能描述: 核算成本 相减 对应的入库方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/14 16:57
     */
    @Override
    public List<String> costRomdeFalse() {
        SysCode code = new SysCode();
        code.setCodeType("warehSingFalse");
        SysCode c = codeService.getSysCode(code);
        if (c == null) {
            code.setDescription("入库成本取反");
            code.setIsDel(CommonFainl.BTYPESTATUS);
            codeService.insertSysCode(code);
        }
        if (CollectionUtils.isEmpty(codeDtlService.optionGet("warehSingFalse"))) {
            SysCodeDtl sysCodeDtl = new SysCodeDtl();
            SysCodeDtl codeDtl = new SysCodeDtl();
            sysCodeDtl.setCodeType("warehSingFalse");
            sysCodeDtl.setCode("RTRT");
            sysCodeDtl.setIsDel(CommonFainl.BTYPESTATUS);
            BeanUtils.copyProperties(sysCodeDtl, codeDtl);
            codeDtl.setCode("SLRT");
            ArrayList<String> strings = new ArrayList<>();
            strings.add("SLRT");
            strings.add("RTRT");
            return strings;
        } else {
            return codeDtlService.optionGet("warehSingFalse").stream().map(s -> s.getOptionValue()).collect(Collectors.toList());
        }
    }
}
