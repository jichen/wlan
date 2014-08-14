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
@Table(name = "S_SMS_PWD_LOG")
public class SMSPWDLogPO {
	/*
	 * 主键
	 */
	private Integer id;

	/*
	 * 用户名
	 */
	private String username;	
	
	
	/*
	 * 操作时间
	 */	
	private Date datetime;
	

	/*
	 * 结果 Y成功  N失败 
	 */
	private String result;	

	
	
	
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


	@Column(nullable = false, length = 1)
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
}
