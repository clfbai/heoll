package com.boyu.erp.platform.product.aop;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 声明一个切面,记录每个Action的执行时间
 * @author administrator
 */
@Aspect
@Component
public class LogAspect {

	private static final Logger logger=LoggerFactory.getLogger(LogAspect.class);
	/**
	 * 切入点：表示在哪个类的哪个方法进行切入。配置有切入点表达式
	 */
	@Pointcut("execution(* com.boyu.erp.platform.product.controller.*.*(..))")
	public void pointcutExpression() {
		logger.debug("配置切入点");
	}
	/**
	 * 1 前置通知
	 * @param joinPoint
	 */
	@Before("pointcutExpression()")
	public void beforeMethod(JoinPoint joinPoint) {

		// 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求相关参数
        logger.info("===== Start ======");
        // 打印请求 url
        logger.info("URL:{}", request.getRequestURL().toString());
        // 打印 Http method
        logger.info("HTTP Method:{}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method:{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        logger.info("IP:{}", request.getRemoteAddr());
		//获取传入目标方法的参数
		logger.info("args={}",joinPoint.getArgs());
	}
	
	/**
	 * 2 后置通知
	 * 在方法执行之后执行的代码. 无论该方法是否出现异常
	 */
	@After("pointcutExpression()") 
	public void afterMethod(JoinPoint joinPoint) {
		logger.debug("后置通知执行了，有异常也会执行");
	}
	
	/**
	 * 3 返回通知
	 * 在方法法正常结束受执行的代码
	 * 返回通知是可以访问到方法的返回值的!
	 * @param joinPoint
	 * @param returnValue
	 */
	@AfterReturning(value = "pointcutExpression()", returning = "returnValue")
	public void afterRunningMethod(JoinPoint joinPoint, Object returnValue) {
		logger.debug("返回通知执行，执行结果：" + returnValue);
	}
	/**
	 * 4 异常通知
	 * 在目标方法出现异常时会执行的代码.
	 * 可以访问到异常对象; 且可以指定在出现特定异常时在执行通知代码
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(value = "pointcutExpression()", throwing = "e")
	public void afterThrowingMethod(JoinPoint joinPoint, Exception e)
	{
		logger.debug("异常通知, 出现异常 " + e);
	}
	
	/**
	 * 环绕通知需要携带 ProceedingJoinPoint 类型的参数. 
	 * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.
	 * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
	 */
	@Around("pointcutExpression()")
	public Object aroundMethod(ProceedingJoinPoint pjd)
	{
		StopWatch clock = new StopWatch();
		//返回的结果
		Object result = null;
		//方法名称
		String className=pjd.getTarget().getClass().getName();
		
		String methodName = pjd.getSignature().getName();
		
		try 
		{
			// 计时开始
			clock.start(); 
			//前置通知
			//执行目标方法
			result = pjd.proceed();
			//返回通知
			clock.stop();
		} catch (Throwable e) 
		{
			//异常通知
			e.printStackTrace();
		}
		//后置通知
		if(!methodName.equalsIgnoreCase("initBinder"))
		{
			long constTime=clock.getTime();
			
			logger.info("["+className+"]"+"-" +"["+methodName+"]"+" 花费时间： " +constTime+"ms");
			
			if(constTime>500)
			{
				logger.error("["+className+"]"+"-" +"["+methodName+"]"+" 花费时间过长，请检查: " +constTime+"ms");
			}
		}
		return result;
	}
}