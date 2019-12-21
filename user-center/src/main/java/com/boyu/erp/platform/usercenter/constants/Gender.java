package com.boyu.erp.platform.usercenter.constants;

/**
 * 性别
 */
public enum Gender {

	UNKNOW(0, "未知"), MAN(1, "先生"), WOMAN(2, "女士");

	private int value;
	
	private String name;

	private Gender(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}