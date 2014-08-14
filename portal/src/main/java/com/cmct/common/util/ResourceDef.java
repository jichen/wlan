/**
 * 
 */
package com.cmct.common.util;

/**
 * 
 * @title 		
 * @description	定义所有的常量资源类
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		John.Yao
 * @create		2012-11-15 上午10:55:49
 */
public interface ResourceDef {

	
	//异常提示信息
	String EXCEPTION_ERROR_MESSAGE = "服务器处理异常，请稍后再试！";
	//操作成功
	String OPERATE_STATUS_CODE_SUCCESS = "200";
	//操作失败
	String OPERATE_STATUS_CODE_ERROR = "300";
	//重新登录
	String OPERATE_STATUS_CODE_UNAUTH = "403" ;
	//关闭当前tab
	String RESPONSE_CALLBACKTYPE_ACTION = "closeCurrent";
	

	/**********************************分页参数定义************************************/
	/**
	 * 定义每页显示的记录数
	 */
	int PAGE_SIZE = 20;

	/**
	 * 排序--升序
	 */
	String PAGE_SORT_TYPE_ASC = "ASC";
	/**
	 * 排序--降序
	 */
	String PAGE_SORT_TYPE_DESC = "DESC";

	/**
	 * 页面查询字段前缀
	 */
	String PAGE_QUERY_PARAM_PREFIX = "search_";

	//默认成功操作返回信息
	String DEFAULT_MESSAGE_SUCCESS = "当前操作已成功!";

	//默认操作失败返回信息
	String DEFAULT_MESSAGE_ERROR = "当前操作失败!";
	
	//未登录或者会话已经失效
	String DEFAULT_MESSAGE_UNAUTH = "当前操作失败!";

	
	/**
	 * 删除状态-有效
	 */
	String DELETE_STATUS_ACTIVE = "1";
	/**
	 * 删除状态-无效
	 */
	String DELETE_STATUS_INACTIVE = "0";
	

}
