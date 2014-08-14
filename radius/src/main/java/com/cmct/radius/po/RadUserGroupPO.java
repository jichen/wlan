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
 * @create		2014-3-18 下午2:31:43
 */
@Entity
@Table(name = "radusergroup")
public class RadUserGroupPO {
	private Integer id;
	
	private String username;
	
	private String groupname;
	
	private Integer priority;

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

	@Column(name="groupname",nullable = false, length = 64)
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name="priority",nullable = false)
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
