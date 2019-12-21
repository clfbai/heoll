package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttBrand;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCat;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCls;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * 盘点表vo
 * @author HHe
 * @date 2019/9/10 20:10
 */
@Data
public class SttVO extends Stt implements Serializable {

    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库名称
     */
    private String warehCp;
    /**
     * 会计组织编号
     */
    private String fsclUnitCode;
    /**
     * 会计组织名称
     */
    private String fsclUnitCp;
    /**
     * 操作员编号
     */
    private String oprNum;
    /**
     * 操作员名称
     */
    private String oprCp;
    /**
     * 审核人编号
     */
    private String chkrNum;
    /**
     * 审核人名称
     */
    private String chkrCp;
    /**
     * 记账人编号
     */
    private String acckNum;
    /**
     * 记账人名称
     */
    private String acckCp;
    /**
     * 货位管理中文
     */
    private String locAdoptedCp;
    /**
     * 配码库存管理中文
     */
    private String astAdoptedCp;
    /**
     * 装箱库存管理中文
     */
    private String bstAdoptedCp;
    /**
     * 盘点存储范围中文
     */
    private String sttAreaScpCp;
    /**
     * 盘点商品范围中文
     */
    private String sttProdScpCp;
    /**
     * 正式盘点中文
     */
    private String isFrmlCp;
    /**
     * 已生效中文
     */
    private String effectiveCp;
    /**
     * 进度中文
     */
    private String progressCp;
    /**
     * 挂起中文
     */
    private String suspendedCp;
    /**
     * 撤销中文
     */
    private String cancelledCp;
    /**
     * 盘点表品牌集合
     */
    private List<SttBrand> sttBrandList;

    /**
     * 盘点表商品分类集合
     */
    private List<SttProdCat> sttProdCatList;

    /**
     * 盘点表商品品种集合
     */
    private List<SttProdCls> sttProdClsList;
}
