package com.boyu.erp.platform.gateway.client.fallback;

import com.boyu.erp.platform.gateway.client.result.JsonResult;
import com.boyu.erp.platform.gateway.client.result.JsonResultCode;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义Zuul回退机制处理器。
 */
@Component
public class ApiGatewayFallbackProvider implements FallbackProvider {

    private static final Logger logger=LoggerFactory.getLogger(ApiGatewayFallbackProvider.class);
	/**
     * 返回值表示需要针对此微服务做回退处理（该名称一定要是注册进入 eureka 微服务中的那个 serviceId 名称）；
     * 表明是为哪个微服务提供回退，*表示为所有微服务提供回退
     * @return
     */
	@Override
	public String getRoute() {
		return "*";
	}
	/**
	 * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的，
	 * 不应该把api的404,500等问题抛给客户端
	 * 网关和api服务集群对于客户端来说是黑盒子
	 */
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause){

		if(cause instanceof HystrixTimeoutException){
			return response(HttpStatus.GATEWAY_TIMEOUT);
		}else{
			return this.fallbackResponse();
		}
	}

	public ClientHttpResponse fallbackResponse(){
		return this.response(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	private ClientHttpResponse response(final HttpStatus status){

		return new ClientHttpResponse() {
			@Override
			public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
			}
			@Override
			public int getRawStatusCode() throws IOException{
				return status.value();
			}
			@Override
			public String getStatusText() throws IOException{
				return status.getReasonPhrase();
			}
			@Override
			public void close(){
			}
			 /**
             * 当 springms-provider-user 微服务出现宕机后，客户端再请求时候就会返回 fallback 等字样的字符串提示；
             * 但对于复杂一点的微服务，我们这里就得好好琢磨该怎么友好提示给用户了；
             * 如果请求用户服务失败，返回什么信息给消费者客户端
             * @return
             * @throws IOException
             */
			@Override
			public InputStream getBody() throws IOException {
                JsonResult jsonResult=new JsonResult();
				try {
                    jsonResult.setCode(JsonResultCode.FAILURE);
                    jsonResult.setMessage("系统错误，请求失败");
				} catch (Exception e) {
                    logger.error("ApiGatewayFallbackProvider exception",e);
				}
				return new ByteArrayInputStream(jsonResult.toString().getBytes("UTF-8"));
			}
			@Override
			public HttpHeaders getHeaders(){
				// headers设定
				HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}
		};
	}
}