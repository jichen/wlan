package com.cmct.common.util.ui;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DwzAJaxRsp {



	private String statusCode;

	private String message = "";

	private String navTabId = "";

	private String rel = "";

	private String callbackType = "";

	private String forwardUrl = "";

	public String toJSON() {
		StringBuilder sb = new StringBuilder();

		if (!StringUtils.isBlank(statusCode) && StringUtils.isNumeric(statusCode)) {
			sb.append("{");

			sb.append("\"statusCode\":");
			sb.append("\"" + statusCode + "\"");
			sb.append(",");

			sb.append("\"message\":");
			sb.append("\"" + message + "\"");
			sb.append(",");

			sb.append("\"navTabId\":");
			sb.append("\"" + navTabId + "\"");
			sb.append(",");

			sb.append("\"rel\":");
			sb.append("\"" + rel + "\"");
			sb.append(",");

			sb.append("\"callbackType\":");
			sb.append("\"" + callbackType + "\"");
			sb.append(",");

			sb.append("\"forwardUrl\":");
			sb.append("\"" + forwardUrl + "\"");

			sb.append("}");

		}

		return sb.toString();
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

}
