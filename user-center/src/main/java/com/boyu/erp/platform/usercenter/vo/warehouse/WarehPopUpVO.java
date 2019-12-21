package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
/**
 * 弹框仓库vo
 * @author HHe
 * @date 2019/9/15 15:15
 */
@Data
public class WarehPopUpVO extends Wareh implements Serializable {
    private static final long serialVersionUID = 1789456132L;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 存放对应Id字段的code、name Map
     */
    private Map<String,String> codeNameMap;
}
