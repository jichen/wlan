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
@Table(name = "T_AP")
public class APPO {
	private Integer id;
	
	private String remark;
	
	private Integer ac_id;
	
	private String nas_port_id;
	
	private String location;
	
	private String mac;

	private String ap_name;
	
	private String isdelete;
	
	private String ac_name;

	private String createusername;
	
	private Date createtime;

	private String updateusername;
	
	private Date updatetime;	
	
	
	@Formula("(select t.ac_name from T_AC t where t.id = ac_id)")
	public String getAc_name() {
		return ac_name;
	}


	public void setAc_name(String ac_name) {
		this.ac_name = ac_name;
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
	
	@Column(nullable = false, length = 100)
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@Column(nullable = true, length = 255)
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	@Column(nullable = false, length = 11)
	public Integer getAc_id() {
		return ac_id;
	}


	public void setAc_id(Integer ac_id) {
		this.ac_id = ac_id;
	}



	@Column(nullable = false, length = 200)
	public String getNas_port_id() {
		return nas_port_id;
	}


	public void setNas_port_id(String nas_port_id) {
		this.nas_port_id = nas_port_id;
	}



	@Column(nullable = false, length = 200)	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	@Column(nullable = false, length = 50)
	public String getAp_name() {
		return ap_name;
	}


	public void setAp_name(String ap_name) {
		this.ap_name = ap_name;
	}
	

	@Column(nullable = false, length = 1)
	public String getIsdelete() {
		return isdelete;
	}


	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
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
