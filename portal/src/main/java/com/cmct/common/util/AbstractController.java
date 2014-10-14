/**
 * 
 */
package com.cmct.common.util;

import com.cmct.common.util.ResourceDef;
import com.cmct.common.util.ui.DwzAJaxRsp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title 		
 * @description 
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		�Ϻ�����ͨ�ż����������޹�˾
 * @author		John.Yao
 * @create		2012-11-15 ����1:13:21
 */
public abstract class AbstractController {

	public static final String LOGIN_NAME = "_loginName";

	/**
	 * �Բ����������ת��
	 * @param request
	 * @param binder
	 */
	@InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1024);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	

	public String ajaxDoneSuccess(String msg, boolean isCloseCur, String navTabId) {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_SUCCESS);
		rsp.setMessage(msg);
		if (isCloseCur) {
			rsp.setCallbackType(ResourceDef.RESPONSE_CALLBACKTYPE_ACTION);
		}
		if (!StringUtils.isBlank(navTabId)) {
			rsp.setNavTabId(navTabId);
		} else {
			rsp.setNavTabId("");
		}

		return rsp.toJSON();
	}

	public String ajaxDoneSuccess(String msg, String callbackType, String navTabId, String forwardUrl, String rel) {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_SUCCESS);
		rsp.setMessage(msg);
		if (!StringUtils.isBlank(callbackType)) {
			rsp.setCallbackType(callbackType);
		}
		if (!StringUtils.isBlank(navTabId)) {
			rsp.setNavTabId(navTabId);
		} else {
			rsp.setNavTabId("");
		}
		if (!StringUtils.isBlank(rel)) {
			rsp.setRel(rel);
		} else {
			rsp.setRel(rel);
		}
		if (!StringUtils.isBlank(forwardUrl)) {
			rsp.setForwardUrl(forwardUrl);
		} else {
			rsp.setForwardUrl("");
		}

		return rsp.toJSON();
	}

	public String ajaxDoneSuccess(String navTabId) {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_SUCCESS);
		rsp.setMessage(ResourceDef.DEFAULT_MESSAGE_SUCCESS);
		rsp.setCallbackType(ResourceDef.RESPONSE_CALLBACKTYPE_ACTION);
		rsp.setNavTabId(navTabId);
		return rsp.toJSON();
	}

	public String ajaxDoneError() {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_ERROR);
		rsp.setMessage(ResourceDef.DEFAULT_MESSAGE_ERROR);
		return rsp.toJSON();

	}

	public String ajaxDoneError(String msg) {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_ERROR);
		rsp.setMessage(msg);
		return rsp.toJSON();

	}


	/**
	 * 
	 * @return session time out 
	 */
	public String ajaxDoneTimeout(){
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_UNAUTH);
		rsp.setMessage(ResourceDef.DEFAULT_MESSAGE_UNAUTH);
		
		return rsp.toJSON() ;
	}

}
