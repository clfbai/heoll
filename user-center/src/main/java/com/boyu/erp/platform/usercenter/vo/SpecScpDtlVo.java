package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

/**
 * @Classname SpecScpDtlVo
 * @Description TODO
 * @Date 2019/5/9 16:01
 * @Created by js
 */
@Data
public class SpecScpDtlVo {

    /**
     * 规格范围ID
     */
    private String specScpId;

    /**
     * 规格ID
     */
    private Long specId;

    /**
     * 规格代码
     */
    private String specCode;

    /**
     * 规格编号
     */
    private String specNum;

    /**
     * 规格名称
     */
    private String specName;
}
