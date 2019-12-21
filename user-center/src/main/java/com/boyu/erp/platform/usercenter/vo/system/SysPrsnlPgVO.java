package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用来更替连表中文
 */
@Data
@NoArgsConstructor
public class SysPrsnlPgVO implements Serializable {
    /**
     * 人员id
     */
    private Long prsnlId;
    /**
     * 人员编号
     */
    private String prsnlCode;
    /**
     * 人员名
     */
    private String fullName;
}
