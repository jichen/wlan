package com.cmct.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @title 		这个类既完成了PropertyPlaceholderConfigurer的任�?同时又提供了上下文properties访问的功�?
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信�?��工程有限公司
 * @author		hx_zhuc
 * @create		2012-10-15下午2:37:50
 */
public class PropertyConfigureHandler extends PropertyPlaceholderConfigurer {

	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

	/**
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory,
	 *      java.util.Properties)
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		if (null == ctxPropertiesMap) {
			ctxPropertiesMap = new HashMap<String, Object>();
		}
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 获取上下文属性Map
	 * 
	 * @return the ctxPropertiesMap
	 */
	public static Map<String, Object> getCtxPropertiesMap() {
		return ctxPropertiesMap;
	}

	/**
	 * 根据name获取上下文属性的�?
	 * 
	 * @param name
	 * @return
	 */
	public static String getContextProperty(String name) {
		return ctxPropertiesMap.get(name).toString();
	}
}
