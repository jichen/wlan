package com.cmct.portal.po;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * 
 * @title 		
 * @description	模块
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		bjc_caixin
 * @create		2013-1-6 下午2:23:17
 */
@Entity
@Table(name="T_MODULE")
public class ModulePO implements Comparable<ModulePO>, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@Column(nullable=false, length=32)
	private String name;
	
	/**
	 * 模块的入口地址
	 */
	@Column(nullable=true, length=1000)
	private String url;
	
	@Column(length=255)
	private String description;
	
	/**
	 * 标志符，用于授权名称（类似module:save）
	 */
	@Column(nullable=false, length=32)
	private String sn;
	
	/**
	 * 模块的排序号,越小优先级越高
	 */
	@Column(length=2)
	private Integer priority = 99;
	
	
	private String parentModule_name;
	
	@Formula("(select t.name from T_MODULE t where t.id = parentId)")
	public String getParentModule_name() {
		return parentModule_name;
	}


	public void setParentModule_name(String parentModule_name) {
		this.parentModule_name = parentModule_name;
	}
	

	private ModulePO parent;
	
	
	private List<ModulePO> children = Lists.newArrayList();

	
	
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
	 * 返回 url 的值   
	 * @return url  
	 */
	@Column(nullable=true, length=1000)
	public String getUrl() {
		return url;
	}

	/**  
	 * 设置 url 的值  
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**  
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
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

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	@ManyToOne
	@JoinColumn(name="parentId")
	public ModulePO getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(ModulePO parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="parent")
	@OrderBy("priority ASC")
	public List<ModulePO> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<ModulePO> children) {
		this.children = children;
	}

	/**  
	 * 返回 sn 的值   
	 * @return sn  
	 */
	public String getSn() {
		return sn;
	}

	/**  
	 * 设置 sn 的值  
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/*
	 * 
	 */
	public int compareTo(ModulePO m) {
		if (m == null) {
			return -1;
		} else if (m == this) {
			return 0;
		} else if (this.priority < m.getPriority()) {
			return -1;
		} else if (this.priority > m.getPriority()) {
			return 1;
		}

		return 0;	
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(id)
				.addValue(name)
				.addValue(parent == null ? null:parent.getName())
				.addValue(children.size())
				.toString();
	}
}
