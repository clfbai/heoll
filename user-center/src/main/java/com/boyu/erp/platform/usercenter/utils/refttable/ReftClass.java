package com.boyu.erp.platform.usercenter.utils.refttable;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReftClass {
    public void setObject(Object textReft) throws Exception {
        Class c = textReft.getClass();
        Field[] field = c.getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field g : field) {
            list.add(g.getName());
        }
        for (Field g : field) {
            g.setAccessible(true);
            for (String s : list) {
                if (g.getName().equals(s) && g.get(textReft) == null) {
                    if (g.getType().equals(Integer.class)) {
                        g.set(textReft, 0);
                    }
                    if (g.getType().equals(Long.class)) {
                        g.set(textReft, 0L);
                    }
                    if (g.getType().equals(String.class)) {
                        g.set(textReft, "");
                    }
                    if (g.getType().equals(Float.class)) {
                        g.set(textReft, 0f);
                    }
                    if (g.getType().equals(Date.class)) {
                        g.set(textReft, java.sql.Date.valueOf("1970-01-01"));
                    }
                    if (g.getType().equals("Timestamp")) {
                        g.set(textReft, Timestamp.valueOf("1970-01-01 00:00:00"));
                    }
                    if (g.getType().equals("BigDecimal")) {
                        g.set(textReft, new BigDecimal(0));
                    }
                }
            }
        }
    }
}
