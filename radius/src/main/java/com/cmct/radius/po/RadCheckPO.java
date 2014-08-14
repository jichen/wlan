package com.cmct.radius.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @title 		
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		jichen
 * @create		2014-3-14 上午10:04:01
 */
@Entity
@Table(name = "radcheck")
public class RadCheckPO {

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * username
	 */
	private String username;
	
	/**
	 * attribute
	 */
	private String attribute;
	
	/**
	 * op
	 */
	private String op;
	
	/**
	 * value
	 */
	private String value;

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

	@Column(name="op",nullable = false, length = 2)
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	@Column(name="value",nullable = false, length = 253)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name="attribute",nullable = false, length = 64)
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
