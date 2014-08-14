package com.cmct.portal.po;

import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Lists;



@Entity
@Table(name = "T_USER")
public class UserPO {

	private Integer userid;

	private String username;

	private String pwd;
	
	private String pwdencryption;
	
	private String salt;
	
	private String status;
	
	private String createusername;
	
	private Date createtime;

	private String updateusername;
	
	private Date updatetime;

	
	
	@Id
	@Column(name = "userid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(nullable = false, length = 20)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(nullable = false, length = 20)
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Column(nullable = false, length = 128)
	public String getPwdEncryption() {
		return pwdencryption;
	}
	public void setPwdEncryption(String pwdencryption) {
		this.pwdencryption = pwdencryption;
	}
	

	@Column(nullable=false, length=32)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Column(nullable = false, length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	
	private List<UserRolePO> userRoles = Lists.newArrayList();
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<UserRolePO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRolePO> userRoles) {
		this.userRoles = userRoles;
	}


	
}
