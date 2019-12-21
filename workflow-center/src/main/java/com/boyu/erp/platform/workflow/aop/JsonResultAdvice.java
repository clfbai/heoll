package com.boyu.erp.platform.usercenter.aspect;

import com.alibaba.fastjson.JSON;
import com.boyu.erp.platform.workflow.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class JsonResultAdvice implements ResponseBodyAdvice
{
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response)
    {
        log.info("[JsonResultAdvice][beforeBodyWrite] start");

        if(body instanceof JsonResult)
        {
            JsonResult jsonResult=(JsonResult)body;

            log.info("返回的数据为：{}",JSON.toJSON(jsonResult));
        }
        return body;
    }
}