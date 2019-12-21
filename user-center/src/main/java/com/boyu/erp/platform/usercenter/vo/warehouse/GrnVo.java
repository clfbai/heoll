package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.alibaba.fastjson.annotation.JSONField;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class GrnVo extends Stb implements Serializable {


    /**
     * 基准日期
     */
    private Integer baseInst;

    /**
     * 成本组名称
     */
    private String costGrpName;

    /**
     * 唯一吗管理
     */
    private String uidAdopted;


    /**
     * 组织ID
     */
    @NotNull(message = "组织id不能为空")
    private Long unitId;

    /**
     * 入库单编号
     */
    @NotBlank(message = "入库单编号不能为空")
    private String grnNum;

    /**
     * 入库方式
     */
    private String rcvMode;

    /**
     * 会计入库方式
     */
    private String fsclRcvMode;

    /**
     * 发货方ID
     */
    private Long delivUnitId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 发货会计组织ID
     */
    private Long delivFsclUnitId;

    /**
     * 始发方ID
     */
    private Long stUnitId;

    /**
     * 始发仓库ID
     */
    private Long stWarehId;

    /**
     * 启用验收
     */
    private String acptReqd;

    /**
     * 启用分储
     */
    private String paReqd;

    /**
     * 收货人ID
     */
    private Long tkovrId;

    /**
     * 收货开始时间
     */
    private Date tkovStTime;

    /**
     * 收货结束时间
     */
    private Date tkovFinTime;

    /**
     * 接收人ID
     */
    private Long rcvrId;

    /**
     * 收货时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date rcvTime;

    /**
     * 存放人ID
     */
    private Long storerId;

    /**
     * 存放开始时间
     */
    private Date strStTime;

    /**
     * 存放结束时间
     */
    private Date strFinTime;

    /**
     * 进度
     */
    private String progress;

    /**
     * 收货仓编号(仓库编号)
     */
    private String warehCode;

    /**
     * 收货仓名称 (仓库名称)
     */
    private String warehName;

    /**
     * 会计组织代码
     */
    private String fsclUnitCode;

    /**
     * 会计组织名称
     */
    private String fsclUnitName;

    /**
     * 逻辑仓库编号
     */
    private String lgcWarehCode;

    /**
     * 逻辑仓库名称
     */
    private String lgcWarehName;

    /**
     * 货位编号
     */
    private String locName;

    /**
     * 操作员编号
     */
    private String oprCode;

    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 中转方编号
     */
    private String tranUnitCode;

    /**
     * 中转方名称
     */
    private String tranUnitName;

    /**
     * 发货方代码
     */
    private String delivUnitCode;

    /**
     * 发货方名称
     */
    private String delivUnitName;

    /**
     * 发货仓编号(发货仓库代码)
     */
    private String delivWarehCode;

    /**
     * 发货仓名称(发货仓库名称)
     */
    private String delivWarehName;

    /**
     * 发货会计组织代码
     */
    private String delivFsclUnitCode;

    /**
     * 发货会计组织名称
     */
    private String delivFsclUnitName;

    /**
     * 始发方代码
     */
    private String stUnitCode;

    /**
     * 始发方名称
     */
    private String stUnitName;

    /**
     * 始发仓编号
     */
    private String stWarehCode;

    /**
     * 始发仓名称
     */
    private String stWarehName;

    /**
     * 收货人编号
     */
    private String tkovrCode;

    /**
     * 收货人姓名
     */
    private String tkovrName;

    /**
     * 接收人编号
     */
    private String rcvrCode;

    /**
     * 接收人姓名
     */
    private String rcvrName;

    /**
     * 存放人编号
     */
    private String storerCode;

    /**
     * 存放人姓名
     */
    private String storerName;

    /**
     * 单据日期form
     */
    private Date docDateFrom;

    /**
     * 单据日期to
     */
    private Date docDateTo;

    /**
     * 会计日期form
     */
    private Date fsclDateFrom;

    /**
     * 会计日期to
     */
    private Date fsclDateTo;


    /**
     * 唯一吗管理中文名
     */
    private String uidAdoptedCp;
    /**
     * 约定会计日期中文名称
     */
    private String fsclDateAptdCp;
    /**
     * 货位管理中文名称
     */
    private String locAdoptedCp;
    /**
     * 桥接方式中文
     */
    private String brdgModeCp;
    /**
     * 启用配码中文
     */
    private String bxiEnabledCp;
    /**
     * 启用装箱中文
     */
    private String boxReqdCp;
    /**
     * 及时结算中文
     */
    private String instStlCp;
    /**
     * 已生效中文
     */
    private String effectiveCp;
    /**
     * 挂起中文
     */
    private String suspendedCp;
    /**
     * 撤销中文
     */
    private String cancelledCp;

    /**
     * 入库方式中文
     */
    private String rcvModeCp;
    /**
     * 会计入库方式中文
     */
    private String fsclRcvModeCp;

    /**
     * 启用验收中文
     */
    private String acptReqdCp;
    /**
     * 启用分储中文
     */
    private String paReqdCp;
    /**
     * 预定装箱中文
     */
    private String boxSchdCp;
    /**
     * 进度中文
     */
    private String progressCp;


}
