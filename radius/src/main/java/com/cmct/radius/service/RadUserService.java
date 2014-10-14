package com.cmct.radius.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.common.util.Constants;
import com.cmct.common.util.NumberUtil;
import com.cmct.common.util.Passwordmd5;
import com.cmct.radius.dao.RadCheckDao;
import com.cmct.radius.dao.RadReplyDao;
import com.cmct.radius.dao.RadUserGroupDao;
import com.cmct.radius.dao.UserInfoDao;
import com.cmct.radius.po.RadCheckPO;
import com.cmct.radius.po.RadReplyPO;
import com.cmct.radius.po.RadUserGroupPO;
import com.cmct.radius.po.UserInfoPO;

@Service
public class RadUserService {
	@Autowired
    private RadCheckDao radCheckDao;
	@Autowired
    private RadReplyDao radReplyDao;
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	private RadUserGroupDao radUserGroupDao;
	
	DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.US);
	/**
	 * 动态密码生成或更新
	 * @param username
	 */
	public String generateOnTimePassword(String username,Integer expirationTime,boolean sso) throws Exception {
		String onTimePassword = NumberUtil.runVerifyCode(6);
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("username", username);
		paramsMap.put("attribute", Constants.RAD_PWD_TYPE_CLERTEXT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, expirationTime);
		
		List<RadCheckPO> listCheckPOPwd = this.radCheckDao.find(paramsMap);
		String md5Pwd = Passwordmd5.MD5(onTimePassword);
		if (!listCheckPOPwd.isEmpty()) {
			RadCheckPO radCheckPwd = listCheckPOPwd.get(0);
			radCheckPwd.setValue(md5Pwd);
			
			paramsMap.clear();
			paramsMap.put("username", username);
					
			List<UserInfoPO> listCheckPOCreattime = this.userInfoDao.find(paramsMap);
			UserInfoPO userInfo = listCheckPOCreattime.get(0);
			userInfo.setPwdModifiedTime(new Date());
			
			paramsMap.clear();
			paramsMap.put("username", username);
			paramsMap.put("attribute", Constants.RAD_PWD_EXPIRATION);
			
			List<RadCheckPO> listCheckPOExpire = this.radCheckDao.find(paramsMap);
			RadCheckPO radCheckExpire = listCheckPOExpire.get(0);
			radCheckExpire.setValue(df.format(cal.getTime()));
			this.radCheckDao.update(radCheckPwd);
			this.radCheckDao.update(radCheckExpire);
			this.userInfoDao.update(userInfo);
			
    	} else {

    		
    		RadCheckPO radCheckPwd = new RadCheckPO();
    		radCheckPwd.setUsername(username);
    		radCheckPwd.setAttribute(Constants.RAD_PWD_TYPE_CLERTEXT);
    		radCheckPwd.setOp(":=");
    		radCheckPwd.setValue(md5Pwd);
    		
    		RadCheckPO radCheckExpire = new RadCheckPO();
    		radCheckExpire.setUsername(username);
    		radCheckExpire.setAttribute(Constants.RAD_PWD_EXPIRATION);
    		radCheckExpire.setOp(":=");
    		radCheckExpire.setValue(df.format(cal.getTime()));
    		
    		UserInfoPO userInfo = new UserInfoPO();
    		userInfo.setUsername(username);
    		userInfo.setUserType(Constants.USER_TYPE_DINAMIC_USER);
    		userInfo.setPwdModifiedTime(new Date());
    		
    		RadUserGroupPO userGroup = new RadUserGroupPO();
    		userGroup.setUsername(username);
    		userGroup.setGroupname(Constants.USER_GROUP_TYPE_DINAMIC_USER);
    		userGroup.setPriority(1);
    		
    		//只对新增客户设置单点登录
    		if(sso){
        		RadCheckPO radSignOn = new RadCheckPO();
        		radSignOn.setUsername(username);
        		radSignOn.setAttribute(Constants.RAD_SINGLE_SIGN_ON);
        		radSignOn.setOp(":=");
        		radSignOn.setValue(Constants.PORTAL_SIGN_ON);
    			this.radCheckDao.save(radSignOn);
    			
    			
    			RadReplyPO radReply1 = new RadReplyPO();
    			radReply1.setUsername(username);
    			radReply1.setOp(":=");
    			radReply1.setAttribute(Constants.RAD_FRAMED_IP_ADDRESS);
    			radReply1.setValue(Constants.RAD_FRAMED_IP_ADDRESS_VALUE);
    			this.radReplyDao.save(radReply1);
    		
    			RadReplyPO radReply2 = new RadReplyPO();
    			radReply2.setUsername(username);
    			radReply2.setOp(":=");
    			radReply2.setAttribute(Constants.RAD_SESSION_TIMEOUT);
    			radReply2.setValue(Constants.RAD_SESSION_TIMEOUT_VALUE);
    			this.radReplyDao.save(radReply2);
    			
			}
    		
    		
    		
    		
    		
    		
    		this.radCheckDao.save(radCheckPwd);
    		this.radCheckDao.save(radCheckExpire);
    		this.userInfoDao.save(userInfo);
    		this.radUserGroupDao.save(userGroup);
		}
		return onTimePassword;
	}
	
	/**
	 * 动态密码是否失效
	 * @param password
	 */
	public String isoverDuePassword(String username,String validtime) throws Exception {
		Map<String,Object> paramsMap = new HashMap<String, Object>();
		//有效期时间
		Integer addtime=Integer.valueOf(validtime)*60000;
		paramsMap.put("username", username);
		
		
		List<UserInfoPO> listCheckPOCreattime = this.userInfoDao.find(paramsMap);
		if(listCheckPOCreattime.size()>0){		
			UserInfoPO userInfo = listCheckPOCreattime.get(0);
			//获取动态密码生成时间
			Date valid_date=userInfo.getPwdModifiedTime();
			//有效期截至时间
			valid_date=new Date(valid_date.getTime()+addtime);
			//当前系统时间
			Date now_date=new Date();
			if(now_date.getTime()<valid_date.getTime()){
				return "effective";
			}else{
				return "overdue";
			}
		}else{
			return "overdue";
		}		

	}
	
}
