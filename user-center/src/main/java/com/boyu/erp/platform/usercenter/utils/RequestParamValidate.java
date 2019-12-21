package com.boyu.erp.platform.usercenter.utils;

public enum RequestParamValidate {
    Add("add"), Delete("delete");
    private final String value;


    private RequestParamValidate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String isRes(String name) {
        if (StringUtils.isNotEmpty(name)) {
            for (RequestParamValidate temp : RequestParamValidate.values()) {
                if (temp.getValue().equals(name)) {
                    return temp.getValue();
                }
            }
        }
        return  null;


    }

    public static void main(String[] args) {
        boolean b = RequestParamValidate.Add.getValue().equals("s");
        System.out.println(isRes("add"));
    }
}
