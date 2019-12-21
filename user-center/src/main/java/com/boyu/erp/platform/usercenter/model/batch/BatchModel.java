package com.boyu.erp.platform.usercenter.model.batch;

import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BatchModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 组织批次对象
     */
    private UnitBatch unitBatch;
    /**
     * 组织批次序号对象
     */
    private UnitBatchNum unitBatchNum;
    /**
     * 批次流水
     */
    private UnitBatchDetail unitBatchDetail;


}
