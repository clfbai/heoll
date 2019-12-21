package com.boyu.erp.platform.usercenter.utils;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class OrderIDGenerator {

	private static final String PREFIX="Order_";
	
	/**
	 * 生成订单号
	 * @return
	 */
	public static String getOrderNumber() {
		String time = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		String randomString = RandomStringUtils.randomNumeric(8);
		return PREFIX+time + randomString;
	}
}