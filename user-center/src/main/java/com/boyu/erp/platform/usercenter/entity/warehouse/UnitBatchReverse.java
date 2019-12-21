package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * unit_batch_reverse
 *
 * @author
 */
@Data
public class UnitBatchReverse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 批次Id
     */
    private String batchId;

    /**
     * 组织Id(仓库所属组织Id)
     */
    private Long unitId;

    /**
     * 商品Id
     */
    private Long prodId;

    /**
     * 采购商Id(顾客购买为缺省 -1)
     */
    private Integer num;

    /**
     * 异动数量
     */
    private Integer moveQty;

    /**
     * 单据编号()
     */
    private String docNum;

    /**
     * 单据类型(R 入库单、D出库单..等等)
     */
    private String docType;

    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 创建时间
     */
    private String isDel;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UnitBatchReverse other = (UnitBatchReverse) that;
        return (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
                && (this.getUnitId() == null ? other.getUnitId() == null : this.getUnitId().equals(other.getUnitId()))
                && (this.getProdId() == null ? other.getProdId() == null : this.getProdId().equals(other.getProdId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getUnitId() == null) ? 0 : getUnitId().hashCode());
        result = prime * result + ((getProdId() == null) ? 0 : getProdId().hashCode());
        return result;
    }


}