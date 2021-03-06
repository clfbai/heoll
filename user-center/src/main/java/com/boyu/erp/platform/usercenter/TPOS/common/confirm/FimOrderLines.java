package com.boyu.erp.platform.usercenter.TPOS.common.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@XmlRootElement(name = "orderLines")
public class FimOrderLines {
    protected List<FirmOrderLine> orderLine;
}
