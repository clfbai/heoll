package com.boyu.erp.platform.usercenter.utils.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname JsonObject
 * @Description 将集合中为null的数据字段转换成“”
 * @Date 2019/7/16 9:12
 * @Created by js
 */
@Slf4j
@Component
public class JsonObject {

    public ArrayList<Object> json(List<Object> objList){
        ArrayList<Object> list;
        String str = JSONObject.toJSONString(objList, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullNumberAsZero);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+str);
        list  = JSON.parseObject(str, new TypeReference<ArrayList<Object>>(){});
        return list;
    }
}
