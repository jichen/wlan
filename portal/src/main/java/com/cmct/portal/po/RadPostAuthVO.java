package com.cmct.portal.po;

import java.util.Date;

/**
 * 
 * @title 		
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		
 * @author		jzw
 * @create		2014-5-6  10:30:01
 */
public class RadPostAuthVO {

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * username
	 */
	private String username;
	
	/**
	 * pass
	 */
	private String pass;
	
	/**
	 * reply
	 */
	private String reply;
	
	/**
	 * authdate
	 */
	private Date authdate;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}


	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getAuthdate() {
		return authdate;
	}

	public void setAuthdate(Date authdate) {
		this.authdate = authdate;
	}

}
