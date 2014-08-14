package com.cmct.portal.po;

public class SnmpClientofAP {
	private String clientMac;
	private String clientIp;
	private String apMac;
	private String apIp;
	private String apSN;
	private String apStatus;
	private String apElementTemplateName;
	public String getApElementTemplateName() {
		return apElementTemplateName;
	}
	public void setApElementTemplateName(String apElementTemplateName) {
		this.apElementTemplateName = apElementTemplateName;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getApIp() {
		return apIp;
	}
	public void setApIp(String apIp) {
		this.apIp = apIp;
	}
	public String getApStatus() {
		return apStatus;
	}
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}
	public String getApMac() {
		return apMac;
	}
	public void setApMac(String apMac) {
		this.apMac = apMac;
	}
	public String getClientMac() {
		return clientMac;
	}
	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}
	public String getApSN() {
		return apSN;
	}
	public void setApSN(String apSN) {
		this.apSN = apSN;
	}
	
}
