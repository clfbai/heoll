package com.boyu.erp.platform.usercenter.TPOS.common.goods.listgoods;

import com.boyu.erp.platform.usercenter.TPOS.common.goods.Item;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@Data
@XmlRootElement(name = "items")
public class Items {
    private List<Item> item;
}
