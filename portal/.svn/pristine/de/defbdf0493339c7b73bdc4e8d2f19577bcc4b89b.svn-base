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
@Table(name = "T_AC")
public class ACPO {
	
	private Integer id;
	
	private String remark;

	private Integer cust_id;
	
	private String ac_name;
	
	private String location;
	
	private String ip;
	
	private String isdelete;
	
	private String cust_name;	
		
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
	
	

	@Column(nullable = true, length = 255)
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	@Column(nullable = false, length = 11)
	public Integer getCust_id() {
		return cust_id;
	}


	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}


	@Column(nullable = false, length = 200)
	public String getAc_name() {
		return ac_name;
	}


	public void setAc_name(String ac_name) {
		this.ac_name = ac_name;
	}


	@Column(nullable = false, length = 100)	
	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}



	@Column(nullable = false, length = 20)
	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}
	

	@Column(nullable = false, length = 1)
	public String getIsdelete() {
		return isdelete;
	}


	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	

	@Formula("(select t.cust_name from T_CUSTOMER t where t.id = cust_id)")
	public String getCust_name() {
		return cust_name;
	}


	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
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
