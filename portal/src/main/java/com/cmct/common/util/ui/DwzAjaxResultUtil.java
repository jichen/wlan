package com.cmct.common.util.ui;

 
public class DwzAjaxResultUtil {

	private static final String HTTP_STATUS_CODE_SUCCESS = "200";

	private static final String RESPONSE_CALLBACKTYPE_ACTION = "closeCurrent";

	private static final String DEFAULT_MESSAGE_SUCCESS = "当前操作已成功";

	public static String ajaxDoneSuccess(String msg, boolean isCloseCur) {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(HTTP_STATUS_CODE_SUCCESS);
		rsp.setMessage(msg);
		if (isCloseCur) {
			rsp.setCallbackType(RESPONSE_CALLBACKTYPE_ACTION);
		}

		return rsp.toJSON();
	}

	public static String ajaxDoneSuccess() {
		DwzAJaxRsp rsp = new DwzAJaxRsp();
		rsp.setStatusCode(HTTP_STATUS_CODE_SUCCESS);
		rsp.setMessage(DEFAULT_MESSAGE_SUCCESS);
		rsp.setCallbackType(RESPONSE_CALLBACKTYPE_ACTION);
		return rsp.toJSON();
	}

}
