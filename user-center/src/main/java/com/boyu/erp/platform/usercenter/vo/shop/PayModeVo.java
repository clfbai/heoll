package com.boyu.erp.platform.usercenter.vo.shop;

import com.boyu.erp.platform.usercenter.entity.shop.PayMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PayModeVo extends PayMode implements Serializable {
    /**
     * 超额许可中文名称
     */
    private String exPmtdCp;

    /**
     * 是否找零中文名称
     */
    private String giveChgCp;

    /**
     * 是否定额中文名称
     */
    private String fixedCp;


}
