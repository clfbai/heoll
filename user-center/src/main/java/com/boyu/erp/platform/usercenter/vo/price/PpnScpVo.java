package com.boyu.erp.platform.usercenter.vo.price;

import com.boyu.erp.platform.usercenter.entity.purchase.PpnScp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname PpnScpVo
 * @Description TODO
 * @Date 2019/8/26 14:31
 * @Created by wz
 */
@Data
public class PpnScpVo extends PpnScp implements Serializable {
    /**
     * 编号
     */
    private String venderNum;
    /**
     * 名称
     */
    private String venderName;

    /**
     * 批量
     */
    private List<PpnScpVo> list;

    public PpnScpVo(Long unitId, String ppnNum) {
        super();
        super.setUnitId(unitId);
        super.setPpnNum(ppnNum);
    }
}
