package com.boyu.erp.platform.usercenter.TPOS.common.goods;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "item")
public class Item {
    /**
     * 商品编码,必填
     */
    private String itemId;
    /**
     * 仓储系统商品编码条件必填, 条件为商品同步接口, 出参itemId不为空
     */
    private String itemCode;
    /**
     * 商品名称,必填
     */
    private String itemName;

    private String shortName;

    private String englishName;
    /**
     * 条形码可多个，用分号;隔开，必填
     */
    private String barCode;

    private String skuProperty;

    private String stockUnit;

    private Double length;

    private Double width;

    private Double height;

    private Double volume;

    private Double grossWeight;

    private Double netWeight;

    private String color;

    private String size;

    private String prodClsId;
    private String title;

    private String categoryId;

    private String categoryName;

    private String pricingCategory;

    private Integer safetyStock;
    /**
     * 商品类型
     * (ZC=正常商品, FX=分销商品, ZH=组合商品, ZP=赠品, BC=包材, HC=耗材, FL=辅料, XN=虚拟品,
     * FS=附属品, CC=残次品, OTHER=其它)  必填,(只传英文编码)
     */
    private String itemType;

    private Double tagPrice;

    private Double retailPrice;

    private String costPrice;

    private Double purchasePrice;

    private String seasonCode;

    private String seasonName;

    private Double brandCode;
    private String brandName;
    private String isSNMgmt;
    private String productDate;
    private String expireDate;
    private String isShelfLifeMgmt;

    private Integer shelfLife;

    private Integer rejectLifecycle;

    private Integer lockupLifecycle;

    private Integer adventLifecycle;

    private String isBatchMgmt;
    private String packCode;
    private String pcs;
    private String batchCode;
    private String batchRemark;

    private String originAddress;
    private String approvalNumber;
    private String isFragile;
    private String isHazardous;
    private String remark;
    private String createTime;
    private String updateTime;
    private String isValid;
    private String isSku;
    private String packageMaterial;
    private ItemExtendProps extendProps;


}
