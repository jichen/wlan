package com.cmct.portal.interfaces.radius;


import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.cmct.portal.interfaces.exception.PortalException;
import com.cmct.portal.interfaces.model.PortalClient;
import com.cmct.portal.interfaces.model.PortalData;
import com.cmct.portal.interfaces.model.Protocal;


public class PortaltoRadius {

	/**
	 * acIp:使用的AC的id，AC是跟着企业走的，以后需要
	 * username 登录名 一般是11位的电话号码
	 * password 这里特指的是动态短信密码
	 * 
	 * 
	 */
	//上线认证
	private static Map<String,Object> rnum=new Hashtable();
	
	public static  Map<String,Object> wlanLogin(String acIP,String userIP,String username,String password) throws IOException, PortalException{
		//目前写死，以后可能会传值
		//acIP="";
		//userIP="192.168.200.3";
		//"TestDalo", "123456"
		
		PortalClient client = new PortalClient(acIP, 2000, 4000, 3);		
		//随机的数字生成方法
		Integer serialNo=serialNo();
		
		//65535是系统随机生成的0-65535的整数。需要保证每个认证的线程的随机数唯一
		PortalData challengeRequestData = client.wrapChallengeRequestData(userIP, serialNo);
		
		//challenge requests
		PortalData ackChallengeData = client.sendData(challengeRequestData);
		if (ackChallengeData == null) {
			//响应超时 抛出异常认证失败
			throw new PortalException("PORTAL_E01");
		}
		
		if (ackChallengeData.getErrorCode() != Protocal.PORTAL_PROTOCOL_ERRCODE_REQ_SUCCESS) {
			//抛出异常,认证失败
			throw new PortalException("PORTAL_E02");
		}
		byte[] challenge = ackChallengeData.getAttributes().get(0).getValue();
		PortalData authRequestData = client.wrapAuthRequestData(ackChallengeData.getIp(), ackChallengeData.getReqId(), ackChallengeData.getSerialNo(), username, password, challenge);
		PortalData ackAuthData = client.sendData(authRequestData);
		if (ackAuthData == null) {
			//响应超时 抛出异常认证失败
			throw new PortalException("PORTAL_E03");
		}		
		if (ackAuthData.getErrorCode() != Protocal.PORTAL_PROTOCOL_ERRCODE_REQ_SUCCESS) {
			//抛出异常,认证失败			throw new PortalException("PORTAL_E04");
		}
		PortalData affAckAuthData = client.wrapAffAckAuthData(ackAuthData.getIp(), ackAuthData.getReqId(), ackAuthData.getSerialNo());
		client.sendDataWithoutResponese(affAckAuthData);
		Map<String,Object> result=new HashMap<String, Object>();
		result.put("userip", ackChallengeData.getIp());
		result.put("serialNo", serialNo);
		result.put("reqID", ackChallengeData.getReqId());
		//离开时将过期的serialNo删除
		rnum.remove(serialNo.toString());
		return result;
		
	}
	
	
	//下线认证
	public static  Map<String,Object> wlanExit(String acIP,String userIP) throws IOException, PortalException{
		
		PortalClient client = new PortalClient(acIP, 2000, 4000, 3);
		//随机的数字生成方法
		Integer serialNo=serialNo();
		PortalData logoutRequestData = client.wrapLogoutRequestData(userIP, 0, serialNo, 0);
		PortalData ackLogoutData = client.sendData(logoutRequestData);
		String errcode="";
		if(ackLogoutData==null){
			//响应超时 抛出异常认证失败
			throw new PortalException("PORTAL_E03");
		}
		if(ackLogoutData.getErrorCode() == Protocal.PORTAL_PROTOCOL_ACKLOGOUT_ERRCODE_SUCCESS){
			//下线成功
			errcode="0";
		}else if(ackLogoutData.getErrorCode() == Protocal.PORTAL_PROTOCOL_ACKLOGOUT_ERRCODE_REFUSED){
			//下线被拒绝
			errcode="1";
		}else if(ackLogoutData.getErrorCode() == Protocal.PORTAL_PROTOCOL_ACKLOGOUT_ERRCODE_FAIL){
			//下线失败
			errcode="2";
		}else{
			//下线异常
			errcode="3";
		}
		Map<String,Object> result=new HashMap<String, Object>();
		result.put("code", errcode);
		//离开时将过期的serialNo删除
		rnum.remove(serialNo.toString());
		return result;
	}
	
	//自动生成serialNo
	public static Integer serialNo(){
		Integer num = (int) (Math.random() * (65535 - 0)) + 0;
		if(rnum!=null){
			boolean flag=true;
			//判断是否存在重复的编号
			Iterator<Object> iter = rnum.values().iterator();
			while (iter.hasNext()) {
				int value = (Integer) iter.next();
				if(value==num){
					flag=false;
					break;
				}
			}
			if(flag){
				//没有，这纪录该编号
				rnum.put(num.toString(), num);
				return num; 
			}else{
				//重新生成
				num=serialNo();
				return num; 
			}
		}else{
			rnum.put(num.toString(), num);
			return num; 
		} 
	}
	
} 
