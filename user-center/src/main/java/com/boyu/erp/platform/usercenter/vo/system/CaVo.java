package com.boyu.erp.platform.usercenter.vo.system;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname CaVo
 * @Description TODO
 * @Date 2019/9/28 9:58
 * @Created by wz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaVo extends Ca implements Serializable {

    /**
     * 往来组织编号
     */
    private String caUnitNum;
    /**
     * 往来组织名称
     */
    private String caUnitCp;
    /**
     * 中转编号
     */
    private String endUnitNum;
    /**
     * 中转名称
     */
    private String endUnitCp;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员姓名
     */
    private String oprCp;

    /**
     * 往来账户类别别名
     */
    private String caTypeCp;

    /**
     * 余额方向别名
     */
    private String balDirCp;

    /**
     * 冲抵方式别名
     */
    private String bloModeCp;

    /**
     * 状态别名
     */
    private String caStatusCp;
}
