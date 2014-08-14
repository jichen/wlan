package com.cmct.radius.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity
@Table(name = "radpostauth")
public class RadPostAuthPO {

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


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="username",nullable = false, length = 64)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="pass",nullable = false, length = 64)
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Column(name="reply",nullable = false, length = 32)
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="authdate")
	public Date getAuthdate() {
		return authdate;
	}

	public void setAuthdate(Date authdate) {
		this.authdate = authdate;
	}

}
