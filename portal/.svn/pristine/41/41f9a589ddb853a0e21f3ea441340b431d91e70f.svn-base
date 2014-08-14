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
@Table(name = "T_AUTH_RULE")
public class AuthRulePO {

	private Integer id;
	
	private String createusername;
	
	private Date createtime;

	private String updateusername;
	
	private Date updatetime;	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private String is_only_client;
	
	@Column(nullable = false, length = 1)
	public String getIs_only_client() {
		return is_only_client;
	}
	public void setIs_only_client(String is_only_client) {
		this.is_only_client = is_only_client;
	}

	private Integer auth_interval;
	@Column(nullable = false, length = 11)
	public Integer getAuth_interval() {
		return auth_interval;
	}
	public void setAuth_interval(Integer auth_interval) {
		this.auth_interval = auth_interval;
	}

	private Integer time;
	@Column(nullable = false, length = 11)
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}

	@Column(nullable = false, length = 20)
	public String getCreateusername() {
		return createusername;
	}
	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
	@Column(nullable = true, length = 20)
	public String getUpdateusername() {
		return updateusername;
	}
	public void setUpdateusername(String updateusername) {
		this.updateusername = updateusername;
	}	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
