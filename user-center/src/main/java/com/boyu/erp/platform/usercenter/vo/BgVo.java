package com.boyu.erp.platform.usercenter.vo;

import com.boyu.erp.platform.usercenter.entity.brand.BgDtl;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname BgVo
 * @Description TODO
 * @Date 2019/5/7 10:41
 * @Created by js
 * 品牌分组vo
 */
@Data
public class BgVo {

    /**
     * 品牌分组ID
     */
    private String bgId;

    /**
     * 品牌分组名称
     */
    private String bgName;

    /**
     * 操作员ID
     */
    private Long oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 添加的bgdtl
     */
    private List<BgDtl> addBgDtl=new ArrayList<>();
    /**
     * 删除的bgdtl
     */
    private List<BgDtl> deleteBgDtl=new ArrayList<>();


}
