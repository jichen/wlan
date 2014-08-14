package com.cmct.radius.po;

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
@Table(name = "user_info")
public class UserInfoPO {

	private Integer id;
	
	private String username;
	
	private String userType;
	
	private Date pwdModifiedTime;

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

	@Column(name="usertype",nullable = false, length = 1)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pwd_modified_time")
	public Date getPwdModifiedTime() {
		return pwdModifiedTime;
	}

	public void setPwdModifiedTime(Date pwdModifiedTime) {
		this.pwdModifiedTime = pwdModifiedTime;
	}
}
