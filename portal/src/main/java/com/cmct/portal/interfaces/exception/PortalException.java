package com.cmct.portal.interfaces.exception;

public class PortalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6120000235895726001L;
	
	private String errorCode;
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public PortalException() {
		super();
	}
	
	public PortalException(String msg) {
		super(msg);
		this.errorCode = msg;
	}

}
