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

import org.hibernate.annotations.Formula;


@Entity
@Table(name = "W_PHONE_MAC")
public class PhoneAndMac {
	
	private Integer id;
	
	private String phone;
	
	private String mac;
	
	/**
	 * 0在线，1离线
	 */
	private String status;
	
	/**
	 * 状态更新时间
	 */
	private Date time;	
	
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
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(nullable = false, length = 20)
	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(nullable = false, length = 1)
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}
	
}
