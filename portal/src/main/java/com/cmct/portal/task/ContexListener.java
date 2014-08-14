package com.cmct.portal.task;


import java.util.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmct.portal.task.MyTask;
public class ContexListener implements ServletContextListener{
	private static Logger logger = LoggerFactory.getLogger(ContexListener.class);
	
	private  Timer timer = null;
	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("计划开始");
		// TODO Auto-generated method stub
		timer = new Timer(true);
		sce.getServletContext().log("服务器已经启动了");
        timer.schedule(new MyTask(sce.getServletContext()),0,1000*60*5);//每隔5分钟检查扫描一次
        sce.getServletContext().log("已经加载了任务的列表");
        logger.debug("计划结束");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		timer.cancel();
		sce.getServletContext().log("任务列表已经销毁了");
	}

}
