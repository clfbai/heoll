package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BrandWindowVo implements Serializable {
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 持有组织Id
     */
    private String holdUnitId;
    /**
     * 持序号
     */
    private String seqNum;
    /**
     * 品牌描述
     */
    private String description;
    /**
     * 品牌状态
     */
    private String brandStatus;
    /**
     * 更新时间
     */
    private String updTime;
    /**
     * 品牌Id
     */
    private Long brandId;
    /**
     * 负责人ID
     */
    private Long manId;
    /**
     * LOGO
     */
    private String logo;
    /**
     * 操作人Id
     */
    private Long oprId;
    /**
     * 持有组织代码
     */
    private String holdUnitCode;
    /**
     * 持有组织名称
     */
    private String holdUnitName;
    /**
     * 负责人代码
     */
    private String manCode;
    /**
     * 负责人名称
     */
    private String manName;
    /**
     * 品牌状态中文名称
     */
    private String brandStatusCp;
    /**
     * 操作人代码
     */
    private String oprCode;
    /**
     * 操作人名称
     */
    private String oprName;
}
