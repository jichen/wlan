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
@Table(name = "W_USER_LOG")
public class WLANUSERLogPO {
	/*
	 * 主键
	 */
	private Integer id;

	/*
	 * 用户名
	 */
	private String username;	
	
	/*
	 * 用户名ip
	 */
	private String userip;	
	
	/*
	 * 认证时间
	 */	
	private Date authtime;


	
	/*
	 * 认证结果 Y成功  N失败 
	 */
	private String authresult;	
	
	/*
	 * 退出时间
	 */	
	private Date exittime;
	
	/*
	 * 退出方式  （不全？）
	 * 1.正常退出
	 * 2.闲置超时
	 * 3.异常退出
	 * 
	 */
	private String exitway;	
	
	/*
	 * 在线时间
	 */	
	private String onlinetime;

	/*
	 * 登录的ap
	 */	
	private Integer apid;
	

	
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

	@Column(nullable = false, length = 20)
	public String getUserip() {
		return userip;
	}
	public void setUserip(String userip) {
		this.userip = userip;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getAuthtime() {
		return authtime;
	}
	public void setAuthtime(Date authtime) {
		this.authtime = authtime;
	}

	
	@Column(nullable = false, length = 2)
	public String getAuthresult() {
		return authresult;
	}
	public void setAuthresult(String authresult) {
		this.authresult = authresult;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getExittime() {
		return exittime;
	}
	public void setExittime(Date exittime) {
		this.exittime = exittime;
	}
	
	@Column(nullable = true, length = 10)
	public String getExitway() {
		return exitway;
	}
	public void setExitway(String exitway) {
		this.exitway = exitway;
	}
	
	@Column(nullable = true, length = 20)
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}
	
	@Column(nullable = true, length = 11)
	public Integer getApid() {
		return apid;
	}
	public void setApid(Integer apid) {
		this.apid = apid;
	}
	
}
