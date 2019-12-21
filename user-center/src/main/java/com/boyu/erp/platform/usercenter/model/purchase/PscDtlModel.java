package com.boyu.erp.platform.usercenter.model.purchase;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 修改采购协议自定义属性(前台传入)
 * @author: wz
 * @create: 2019-7-9 14:07
 */
@Data
@ToString
public class PscDtlModel extends BaseData implements Serializable {

    /**
     * 采购商id
     */
    private Long vendeeId;
    /**
     * 供应商id
     */
    private Long venderId;
    /**
     * 购销合同类别
     */
    private String pscType;
    /**
     * 协议控制方
     */
    private String  psaCtlr;
    /**
     * 选择的商品
     */
    private List<DtlProdVo> vo;

    public PscDtlModel(){

    }

    public PscDtlModel(Long vendeeId,Long venderId,String  psaCtlr){
        this.vendeeId = vendeeId;
        this.venderId = venderId;
        this.psaCtlr = psaCtlr;
    }

}
