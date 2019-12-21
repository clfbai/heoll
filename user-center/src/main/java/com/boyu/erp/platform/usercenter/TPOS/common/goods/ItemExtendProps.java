package com.boyu.erp.platform.usercenter.TPOS.common.goods;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "extendProps")
public class ItemExtendProps {

    private Integer isNew;
    /**
     * 每箱包装率，int
     */
    private Integer boxMinPackageRate;

    private Integer boxLength;

    private Integer boxWidth;

    private Integer boxHeight;

    private Integer boxVolume;
}
