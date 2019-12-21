package com.boyu.erp.platform.usercenter.vo.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UgVo extends SysUg {
    private String updTimeCp;
    /**
     * 操作员名称
     */
    private String oprName;
    /**
     * 操作员代码
     */
    private String oprCode;

    /**
     * 分组类别中文名称
     */
    private String ugTypeCp;

    /**
     * 组织类别中文名称
     */
    private String unitTypeCp;
}
