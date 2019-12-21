package com.boyu.erp.platform.usercenter.text;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TextMain {
    public static void main(String[] args) throws Exception {


        TextReft textReft = new TextReft();
      textReft.setColorCode(123f);
           Class c=textReft.getClass();
        Field[] field = c.getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field g : field) {

            list.add(g.getName());
        }
        for(Field g : field){
            g.setAccessible(true);
            for(String s:list){
                if(g.getName().equals(s)&&g.get(textReft)==null){
                   if(g.getType().equals(Integer.class)){
                       g.set(textReft,0);
                   }
                    if(g.getType().equals(Long.class)){
                        g.set(textReft,0L);
                    }
                    if(g.getType().equals(String.class)){
                        g.set(textReft,"");
                    }
                    if(g.getType().equals(Float.class)){
                        g.set(textReft,0f);
                    }
                    if(g.getType().equals(Date.class)){
                        g.set(textReft,"");
                    }

                }
            }
        }
        System.out.println(textReft);


        BigDecimal qs = BigDecimal.valueOf(0.00);

        BigDecimal q = BigDecimal.valueOf(0);

         System.out.println(q.compareTo(qs)<=0);

    }



}
