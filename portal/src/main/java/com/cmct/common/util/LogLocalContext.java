package com.cmct.common.util;

import com.cmct.portal.po.LoginLogPO;
import com.cmct.portal.po.OperateLogPO;

/**
 * @title 		
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		hx_zhuc
 * @create		2012-8-20上午10:59:19
 */
public class LogLocalContext {
	private static ThreadLocal<OperateLogPO> operateLogLocal = new ThreadLocal<OperateLogPO>();

	private static ThreadLocal<LoginLogPO> loginLogLocal = new ThreadLocal<LoginLogPO>();

	/**
	 * @return
	 */
	public static OperateLogPO getOperateLogPO() {
		return operateLogLocal.get();
	}

	/**
	 * @return
	 */
	public static LoginLogPO getLoginLogPO() {
		return loginLogLocal.get();
	}

	/**
	 * @param signLogView
	 */
	public static void setSignLogView(LoginLogPO loginLogPO) {
		loginLogLocal.set(loginLogPO);
	}

	/**
	 * @param opLogView
	 */
	public static void setOperateLogPO(OperateLogPO operateLogPO) {
		operateLogLocal.set(operateLogPO);
	}
}
