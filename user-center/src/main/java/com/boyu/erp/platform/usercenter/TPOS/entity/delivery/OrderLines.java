package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

import java.util.List;

@Data
public class OrderLines {
    private List<OrderLine> orderLine;
}
