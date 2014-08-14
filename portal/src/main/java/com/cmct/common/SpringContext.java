package com.cmct.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("springContext")
@Lazy(false)
public class SpringContext implements ApplicationContextAware {
	private final static Logger logger=LoggerFactory.getLogger(SpringContext.class);
	
	private static ApplicationContext ac=null;
	
	public static Object getbean(String name){
		return ac.getBean(name);
		
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac=applicationContext;
		logger.info("SpringContext init:"+ ac);
		
	}

}
