package com.boyu.erp.platform.usercenter.vo.system.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述:
 *
 * @Description: 共用返回别名
 * @auther: CLF
 * @date: 2019/9/2 12:25
 */
@Data
@NoArgsConstructor
public class CommonCp implements Serializable {
    /**
     * 是否共享中文名称
     */
    private String sharedCp;
    /**
     * 可征募中文名称
     */
    private String recruitableCp;
    /**
     * 组织状态中文名称
     */
    private String unitStatusCp;
    /**
     * 部门状态中文名称
     */
    private String deptStatusCp;
    /**
     * 联络人姓名(联系人姓名)
     */
    private String lmName;
    /**
     * 联络人代码(联系人代码)
     */
    private String lmCode;
}
