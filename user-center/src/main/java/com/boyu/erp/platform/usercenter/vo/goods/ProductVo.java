package com.boyu.erp.platform.usercenter.vo.goods;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 商品数据模型
 * @author: clf
 * @create: 2019-06-15 10:55
 */
@Data
@ToString
@NoArgsConstructor
public class ProductVo extends BaseData {

    private Long unitId;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 版型
     */
    /**
     * 规格名称
     */
    private String specName;

    private String season;

    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 修改后商品代码
     */
    private String updateProdCode;


    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 颜色ID
     */
    private Long colorId;

    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 颜色代码
     */
    private String colorCode;
    /**
     * 颜色标注
     */
    private String colorCmt;

    /**
     * 规格ID
     */
    private Long specId;
    /**
     * 规格码
     */
    private String specCode;

    /**
     * 规格号
     */
    private String specNum;
    /**
     * 规格号
     */
    private String specGrpId;

    /**
     * 规格标注
     */
    private String specCmt;

    /**
     * 版型
     */
    private String edition;

    /**
     * 版型标注
     */
    private String editionCmt;
    /**
     * 版型（取 sys_code_dtl 罩杯版型 序号值的)
     */
    private String description;
    /**
     * 配比
     */
    private Long proportion;

    /**
     * 店内码
     */
    private String innerBc;

    /**
     * 国际码
     */
    private String intlBc;


    /**
     * 修改后店内码
     */
    private String updateinnerBc;

    /**
     * 修改后国际码
     */
    private String updateintlBc;

    /**
     * 最大序号
     */
    private Long maxSn;

    /**
     * 明细属性1
     */
    private String pdDtlProp1;

    /**
     * 明细属性2
     */
    private String pdDtlProp2;

    /**
     * 明细描述
     */
    private String pdDtlDesc;

    /**
     * 已删除
     */
    private String deleted;

    /**
     * 是否可用
     */
    private Byte isDel;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 商品分类Id
     */
    private String prodCatId;
    /**
     * 商品明细状态
     */
    private String skuStatus;

    private List<ProductVo> addList = new ArrayList<>();

    //批量导入编号
    private Integer number;

}
