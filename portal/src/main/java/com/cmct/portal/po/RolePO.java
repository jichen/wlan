package com.cmct.portal.po;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

/**
 * 
 * @title 		
 * @description	角色
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		bjc_caixin
 * @create		2013-1-9 下午4:26:20
 */
@Entity
@Table(name="T_ROLE")
public class RolePO {
	private Integer id;
	
	//角色名称
	@Column(nullable=false, length=32)
	private String name;
	
	//角色编号
	@Column(nullable=false, length=32)
	private String code;
	
	//角色描述
	@Column(name = "description")
	private String description;
	
	//权限点
	@Column(name = "permission")
	private List<String> permissionList = Lists.newArrayList();
	
	//用户角色
	private List<UserRolePO> userRoles = new ArrayList<UserRolePO>(0);

	public RolePO(){
	}

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
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * 返回 userRoles 的值   
	 * @return userRoles  
	 */
	@OneToMany(mappedBy="role")
	public List<UserRolePO> getUserRoles() {
		return userRoles;
	}

	/**  
	 * 设置 userRoles 的值  
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRolePO> userRoles) {
		this.userRoles = userRoles;
	}

	/**  
	 * 返回 permissionList 的值   
	 * @return permissionList  fetch=FetchType.LAZY
	 */
	@ElementCollection
	@CollectionTable(
			name = "t_role_permission", 
			joinColumns = { @JoinColumn(name = "roleid")}
	)
	public List<String> getPermissionList() {
		return permissionList;
	}

	/**  
	 * 设置 permissionList 的值  
	 * @param permissionList
	 */
	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取角色编号
	 * @return
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置角色编号
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

}




