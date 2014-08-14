package com.cmct.portal.po;

public class SnmpAPtoClientAdress {
	private Integer id;
	private String oid;
	private String clientMac;
	private String apSN;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
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
