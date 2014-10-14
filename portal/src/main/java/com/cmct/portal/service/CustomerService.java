package com.cmct.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.CustomerDao;
import com.cmct.portal.po.CustomerPO;


@Service
public class CustomerService  {
	@Autowired
	private CustomerDao customerDao;
	
	public List<CustomerPO> getAll(){
		return customerDao.findAll();
		
	} 
	
	public void saveCustomer(CustomerPO entity){
		customerDao.save(entity);
	} 
	
	public void updateCustomer(CustomerPO entity){
		customerDao.update(entity);
	} 	


	
	public Integer countRow() {
		String sql=" from CustomerPO ";
		return customerDao.getCount(sql);

	} 
	
	public CustomerPO findOne(Integer id) {
		return customerDao.get(id);

	} 
	
	
	public List<CustomerPO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<CustomerPO> list=customerDao.find(propertiesMap, start, limit);
		return list;
	}
	
	
	public List<CustomerPO> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<CustomerPO> list=customerDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return customerDao.getTotalCount(sql, propertiesMap);
	} 
	
	public CustomerPO findCustomerName(String customerName){
		Map<String,Object> propertiesMap=new HashMap<String, Object>();
		propertiesMap.put("cust_name", customerName);
		propertiesMap.put("isdelete", "N");
		List<CustomerPO> list =customerDao.find(propertiesMap, 0, 10);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
}
