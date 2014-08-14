package com.cmct.radius.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.radius.dao.SMSLogDao;
import com.cmct.radius.po.SMSLogPO;
@Service
public class SMSLogService {
	@Autowired
    private SMSLogDao smsLogDao;

	
	/**
	 * 保存SMS的返回记录
	 * @param password
	 */
	public void saveSMSLOG(String username,String result) {
		SMSLogPO sl=new SMSLogPO();
		sl.setUsername(username);
		sl.setResult(result);
		sl.setSendTime(new Date());
		smsLogDao.save(sl);
	}
	
}
