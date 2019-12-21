package com.boyu.erp.platform.usercenter.entity.warehouse;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * batch_ref_num (批次编号表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class BatchRefNum extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 增量，默认是1
     */
    private Long stepSize;

    /**
     * 最大值
     */
    private Long maxNum;

    /**
     * 批次增量位数
     */
    private String batchNum;

    /**
     * 增量填充字符
     */
    private String batchFill;


}