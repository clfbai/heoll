package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述: 入库单明细数据模型
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/31 10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDtlModel implements Serializable {

    private WarehRcvTask warehRcvTask;

    private Grn grn;

    private Stb stb;

    //入库单明细
    private List<StbDtl> stbDtls = new ArrayList<>();

    private List<GrnDtlModel> list = new ArrayList<>();

}
