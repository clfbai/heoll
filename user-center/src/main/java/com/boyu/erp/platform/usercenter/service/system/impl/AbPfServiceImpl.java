package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.basic.AbPf;
import com.boyu.erp.platform.usercenter.entity.system.FsclPerd;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.AbPfMapper;
import com.boyu.erp.platform.usercenter.service.system.AbPfService;
import com.boyu.erp.platform.usercenter.service.system.FsclPerdService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 账套控制表服务
 *
 * @author HHe
 * @date 2019/10/21 16:20
 */
@Service
public class AbPfServiceImpl implements AbPfService {
    @Autowired
    private AbPfMapper abPfMapper;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
    private FsclPerdService fsclPerdService;


    //是否允许会计日期晚于自然日期
    private final static String ADVANCED_FISCAL_DATE_ALLOWED = "ADVANCED_FISCAL_DATE_ALLOWED";
    //会计归档日期
    private final static String ARCHIVE_DATE = "ARCHIVE_DATE";

    /**
     * 记账前判断账套和会计日期合法性
     *
     * @author HHe
     * @date 2019/10/21 16:20
     */
    @Override
    public AbPf precheck(Date fsclDate, Long fsclUnitId) throws ParseException {
//        boolean b = false;
        SysParameter advancedFiscalDateAllowed = sysParameterService.findByParameter(ADVANCED_FISCAL_DATE_ALLOWED);
        if ("F".equals(advancedFiscalDateAllowed.getParmVal()) && fsclDate != null && fsclDate.getTime() > System.currentTimeMillis()) {
            throw new ServiceException("201", "会计日期无效");
        }
        SysParameter archiveDate = sysParameterService.findByParameter(ARCHIVE_DATE);
        if (fsclDate != null && archiveDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date gdDate = sdf.parse(archiveDate.getParmVal());
            if (fsclDate.getTime() < gdDate.getTime()) {
                throw new ServiceException("201", "会计日期无效");
            }
        }
        //查询对应账套获取时间段
        AbPf abPf = abPfMapper.selectByPrimaryKey(fsclUnitId);
        if (abPf == null) {
            throw new ServiceException("201", "该组织没有账套");
        }
        if (DelivUtil.equalsHave(abPf.getAbStatus().toUpperCase(), "C", "O")) {
            throw new ServiceException("201", "账套状态无效");
        }
        if (fsclDate != null) {
            FsclPerd fsclPerd = fsclPerdService.queryTimeQByPerd(abPf.getCurFsclYear(), abPf.getCurFsclPerd());
            if ("N".equals(abPf.getAbStatus().toUpperCase())) {
                if (fsclDate.getTime() < fsclPerd.getFromDate().getTime()) {
                    throw new ServiceException("201", "无效的会计时间");
                }
            } else if ("L".equals(abPf.getAbStatus().toUpperCase())) {
                if (fsclDate.getTime() <= fsclPerd.getToDate().getTime()) {
                    throw new ServiceException("201", "无效的会计时间");
                }
            }
        }
        return abPf;
    }
}
