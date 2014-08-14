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
@Table(name = "W_AP_ClIENT")
public class APAndClient {
	
	private Integer id;
	
	private String clientmac;

	private String apmac;
	
	/**
	 * 0进入，1离开
	 */
	private String status;
	
	/**
	 * 进入时间
	 */
	private Date intime;	
	
	/**
	 * 离开时间
	 */
	private Date outtime;	
	
	
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
	public String getClientmac() {
		return clientmac;
	}

	public void setClientmac(String clientmac) {
		this.clientmac = clientmac;
	}


	@Column(nullable = false, length = 20)
	public String getApmac() {
		return apmac;
	}

	public void setApmac(String apmac) {
		this.apmac = apmac;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getIntime() {
		return intime;
	}


	public void setIntime(Date intime) {
		this.intime = intime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getOuttime() {
		return outtime;
	}


	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}


	@Column(nullable = false, length = 1)
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
