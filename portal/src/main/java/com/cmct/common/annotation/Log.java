package com.cmct.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @title 		需要做日志切面的自定注解,在需要切面的方法上添加
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		hx_jic
 * @create		2012-4-10 上午11:11:08
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	/**
	 * 功能名称
	 * 
	 * @return
	 */
	String function() default "";

	/**
	 * 模块名称
	 * 
	 * @return
	 */
	String module() default "";

	/**
	 * 是否是登录日志
	 * 
	 * @return
	 */
	boolean isSign() default false;
}
