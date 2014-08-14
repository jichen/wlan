package com.cmct.portal.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "S_OPERATE_LOG")
public class OperateLogPO {
	/*
	 * 主键
	 */
	private Integer id;

	/*
	 * 用户名
	 */
	private String username;	
	
	/*
	 * 方法名 （记录curd）
	 */	
	private String function_name;
	
	/*
	 * 操作时间
	 */	
	private Date operatetime;
	
	/*
	 * 操作人员IP
	 */		
	private String ip;	

	/*
	 * 操作（记录那个菜单）
	 */
	private String operate;	

	/*
	 * 操作数据的ID
	 */
	private Integer operate_id;
	
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(nullable = false, length = 20)
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Column(nullable = true, length = 20)
	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(nullable = true, length = 50)
	public String getFunction_name() {
		return function_name;
	}


	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	
	@Column(nullable = true, length = 50)
	public String getOperate() {
		return operate;
	}


	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	@Column(nullable = true, length = 11)
	public Integer getOperate_id() {
		return operate_id;
	}
	public void setOperate_id(Integer operate_id) {
		this.operate_id = operate_id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}
}
