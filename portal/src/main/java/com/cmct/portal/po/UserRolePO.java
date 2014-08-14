package com.cmct.portal.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @title 		
 * @description	用户角色
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		bjc_caixin
 * @create		2013-1-6 下午2:23:34
 */
@Entity
@Table(name="T_USER_ROLE")
public class UserRolePO  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** 描述  */
	
	private Integer id;
	
	
	/**
	 * 值越小，优先级越高
	 */
	//@Column(length=2, nullable=false)
	private Integer priority = 99;
	

	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

	
	
	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
	@Column(length=2, nullable=false)
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	private UserPO user;
	
	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid")
	public UserPO getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(UserPO user) {
		this.user = user;
	}

	private RolePO role;
	
	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	@ManyToOne
	@JoinColumn(name="roleid")
	public RolePO getRole() {
		return role;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setRole(RolePO role) {
		this.role = role;
	}
	
}
