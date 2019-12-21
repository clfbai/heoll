package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_rwd_pun   员工奖惩记录
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplRwdPun extends EmpKey implements Serializable {
    /**
     * 奖惩日期
     */
    private Date rwdPunDate;
    /**
     * 奖惩日期String 类型
     */
    private String rwdPunDateCp;
    /**
     * 奖惩类别
     */
    private String rwdPunType;

    /**
     * 奖惩金额
     */
    private Float rwdPunVal;

    /**
     * 奖惩原因
     */
    private String rwdPunDesc;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;


}