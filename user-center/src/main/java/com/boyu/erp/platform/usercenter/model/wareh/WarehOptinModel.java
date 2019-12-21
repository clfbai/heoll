package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 类描述:  组织弹窗查询模型
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/19 10:54
 */
@Data
@NoArgsConstructor
public class WarehOptinModel implements Serializable {

    /**
     * 仓库ID
     */
    private Long warehId;
    /**
     * 组织ID
     */
    private Long unitId;
    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 组织状态
     */
    private String unitStatus;

    @NotBlank(message = "组织类型为空")
    private String unitType;

}
