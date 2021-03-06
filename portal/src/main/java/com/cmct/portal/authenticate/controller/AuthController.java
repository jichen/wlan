package com.cmct.portal.authenticate.controller;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmct.common.util.PropertyConfigureHandler;
import com.cmct.portal.po.ACPO;
import com.cmct.portal.po.APAndClient;
import com.cmct.portal.po.APPO;
import com.cmct.portal.po.AuthRulePO;
import com.cmct.portal.po.PhoneAndMac;
import com.cmct.portal.po.SMSPWDLogPO;
import com.cmct.portal.po.SnmpClientofAP;
import com.cmct.portal.po.WLANUSERLogPO;
import com.cmct.portal.service.ACService;
import com.cmct.portal.service.APAndClientService;
import com.cmct.portal.service.APService;
import com.cmct.portal.service.AuthRuleService;
import com.cmct.portal.service.BLackListService;
import com.cmct.portal.service.PhoneAndMacService;
import com.cmct.portal.service.SMSPWDLogService;
import com.cmct.portal.service.WLANUSERLogService;
import com.cmct.portal.snmpmib.ApMIBService;
import com.cmct.portal.interfaces.exception.PortalException;

import freemarker.template.TemplateException;


@Controller
@RequestMapping(value = "/authenticate")
public class AuthController {

	@Autowired
	private AuthRuleService authRuleService;
	
	@Autowired
	private ACService acService;
	
	@Autowired
	private APService apService;
	
	@Autowired
	private BLackListService blackListService;
	
	@Autowired
	private SMSPWDLogService smsPWDLogService; 
	
	@Autowired
	private WLANUSERLogService wUSERLogService;
	
	@Autowired
	private ApMIBService apMIBService;
	
	@Autowired
	private PhoneAndMacService phoneAndMacService;
	
	@Autowired
	private APAndClientService apAndClientService;
			
	@RequestMapping(value = "/authPage")
	public String createPage(HttpServletRequest request,HttpServletResponse resp) throws IOException, TemplateException {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		if (userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("android")) {
			return "/pages/authenticate/mobileAuth";
		} else {
			return "/pages/authenticate/pcAuth";
		}
	}
	

	@RequestMapping(value = "/countdown")
	@ResponseBody
	public  Map<String,Object> countdown(HttpServletRequest request,HttpServletResponse response,String username) throws HttpException, IOException {
		//判断用户是否存在于黑名单中
		Map<String,Object> rs = new HashMap<String,Object>();
		//对用户名进行判断是否存在于黑白名单中
		if(blackUser(username)){
			rs.put("status","0");
			rs.put("msg", "您被加入了黑名单，请与服务方联系。");
			return rs;
		}else{
			//获取认证的规则，以后要修改的			
			List<AuthRulePO> list=authRuleService.getAll();
			AuthRulePO arPO=list.get(0);
			//重复申请间隔
			String repeat=arPO.getAuth_interval().toString();

			//判断是否可以进行重复申请了
			String result=howMuchTime(username, repeat);
			if(!result.equals("Y")){
				rs.put("status","1");
				rs.put("repeat",repeat);
				rs.put("msg", "请不要短时间内重复获取动态密。");
				return rs;
			}else{
				rs.put("status","2");
				rs.put("repeat",repeat);
				rs.put("msg", "OK");
				return rs;
			}
		}		
	}

	
	
	@RequestMapping(value = "/dynamic")
	@ResponseBody
	public  Map<String,Object> dynamic(HttpServletRequest request,HttpServletResponse response,String username) throws HttpException, IOException {
		Map<String,Object> rs = new HashMap<String,Object>();
		//获取认证的规则，以后要修改的			
		List<AuthRulePO> list=authRuleService.getAll();
		AuthRulePO arPO=list.get(0);
		//密码有效期
		String validtime=arPO.getTime().toString();
		String signOn=arPO.getIs_only_client();
		//申请动态密码
		SMSPWDLogPO bean=new SMSPWDLogPO();
		bean.setUsername(username);
		HttpClient client = new HttpClient();
		//从配置文件中获取接口地址
		String path = (String)PropertyConfigureHandler.getCtxPropertiesMap().get("dynamic");
		//PostMethod pm = new PostMethod("http://192.168.60.69:8081/radius/dinamicPwd/fetch");
		PostMethod pm = new PostMethod(path);
		NameValuePair[] vp = {
				new NameValuePair("username",username),
				new NameValuePair("validtime",validtime),
				new NameValuePair("signOn",signOn)
			};		
		pm.addParameters(vp);
		/*
		 *  一个参数
		 * 		 
		NameValuePair vp = new NameValuePair();
		vp.setName("username");
		vp.setValue(username);
		pm.setQueryString(new NameValuePair[]{vp});
		*/			
		int Status=client.executeMethod(pm);
		//判断密码是否申请成功
		if(Status==HttpStatus.SC_OK){
			bean.setDatetime(new Date());
			bean.setResult("Y");
			//记录成功的log
			smsPWDLogService.save(bean);
			rs.put("status","0");
			return rs;
		}else{
			bean.setDatetime(new Date());
			bean.setResult("N");
			//记录失败的log
			smsPWDLogService.save(bean);
			rs.put("status","1");
			return rs;
		}
	}
	


	
	
	//联网登录验证 
	@RequestMapping(value = "/overdue")
	@ResponseBody
	public Map<String,Object> isoverdue(String username,String password,String userip,String wlanurl,String clientMac,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) throws HttpException, IOException {
		Map<String,Object> rs = new HashMap<String,Object>();
		
		//对用户名进行判断是否存在于黑白名单中
		if(blackUser(username)){
			rs.put("status","2");
			rs.put("msg", "您没有任何权限登录。");
			return rs;
		}
		
		Date now=new Date();
		//获取客户端IP
		//String userip=request.getRemoteAddr();
		WLANUSERLogPO po = new WLANUSERLogPO();
		po.setAuthtime(now);
		po.setUsername(username);
		po.setUserip(userip);
		//进入认证流程
		try {
			Map<String,Object> result=new HashMap<String, Object>();
			//获取ACIP
			String acIP="";
			String sql=" from ACPO where 1=1  and isdelete= :isdelete order by id ";
			Map<String,Object> propertiesMap =new HashMap<String,Object>();
			propertiesMap.put("isdelete","N");
			List<ACPO> acs=acService.findPages_sql(sql, propertiesMap, 0, 10);
			if(null != acs && acs.size()>0){
				ACPO ac=acs.get(0);
				acIP=ac.getIp();
			}
			result=com.cmct.portal.interfaces.radius.PortaltoRadius.wlanLogin(acIP,userip,username,password);
			//对result进行判断，以及添加log
			//目前无法获取
			
			po.setApid("");
			po.setAuthresult("OK");
			wUSERLogService.save(po);
			//将数据放入session中
			session.setAttribute("username", username);
			session.setAttribute("password", password);				
			session.setAttribute("userip", result.get("userip"));
			session.setAttribute("serialNo", result.get("serialNo"));
			session.setAttribute("reqID", result.get("reqID"));
			rs.put("status","0");
			rs.put("ctx", request.getContextPath());
			rs.put("url", wlanurl);
			rs.put("mac", clientMac);
			rs.put("msg", "登陆成功");
			//登录成功后将手机和ap进行关联
			clientToApOfMAC_Login(username,clientMac);
			return rs;
		} catch (PortalException e) {
			//po.setApid("");
			po.setAuthresult("NO");
			wUSERLogService.save(po);
			rs.put("status","1");
			rs.put("msg", "密码过期，请重新获取");
			e.printStackTrace();
			return rs;
		}
	}
	
	

	
	
	
	//联网退出
	@RequestMapping(value = "/wLanExit")
	@ResponseBody
	public Map<String,Object> wLanExit(HttpServletRequest request,HttpServletResponse response,
			HttpSession session,String userip,String clientMac) throws HttpException, IOException {
		System.out.println("wLanExit");
		Map<String,Object> rs = new HashMap<String,Object>();
		if(session!=null){
			try {
				Map<String,Object> result=new HashMap<String, Object>();
				String userIP=(String) session.getAttribute("userip");
				//获取ACIP(根据获取的信息，判断那个ac都数据库查询该AC的配置信息)
				String acIP="";
				String sql=" from ACPO where 1=1  and isdelete= :isdelete order by id ";
				Map<String,Object> propertiesMap =new HashMap<String,Object>();
				propertiesMap.put("isdelete","N");
				List<ACPO> acs=acService.findPages_sql(sql, propertiesMap, 0, 10);
				if(null != acs && acs.size()>0){
					ACPO ac=acs.get(0);
					acIP=ac.getIp();
				}
				result=com.cmct.portal.interfaces.radius.PortaltoRadius.wlanExit(acIP, userIP);
				Date now=new Date();
				//判断返回值
				String code=(String) result.get("code");
				System.out.println("code==="+code);
				if(code.equals("0")){
					rs.put("status","0");
					rs.put("msg", "下线成功。");
					String username=(String) session.getAttribute("username");
					WLANUSERLogPO po=wUSERLogService.findNewOne(username, userIP);
					String strAp=clientToApOfMAC_Exit(username,clientMac);
					po.setApid(strAp);
					if(po!=null){
						po.setExittime(now);
						po.setExitway("1");
						//分钟
						long time = (now.getTime()-po.getAuthtime().getTime())/1000/60;
						String time1=time+"";
						Integer time2=Integer.valueOf(time1);
						po.setOnlinetime(time2+"");
						wUSERLogService.update(po);
					}
				}else if(code.equals("1")){
					rs.put("status","1");
					rs.put("msg", "下线被系统拒绝。");
				}else if(code.equals("2")){
					rs.put("status","2");
					rs.put("msg", "下线失败。");
				}else if(code.equals("3")){
					rs.put("status","3");
					rs.put("msg", "系统异常请尽快联系服务方。");
				}
				return rs;
			} catch (PortalException e) {
				e.printStackTrace();
				rs.put("status","3");
				rs.put("msg", "系统异常请尽快联系服务方。");
				return rs;
			}
		}else{
			rs.put("status","3");
			rs.put("msg","系统异常请尽快联系服务方。");
			return rs;
		}
	}
	
	
	
	private void clientToApOfMAC_Login(String phone,String clientMac){
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("phone", phone);
		mp.put("mac", clientMac);
		//更新WLAN登录用户表
		PhoneAndMac pm=new PhoneAndMac();

		if(phoneAndMacService.find(mp)!=null){
			pm=phoneAndMacService.find(mp);
			pm.setStatus("0");
			pm.setTime(new Date());
			phoneAndMacService.updatePM(pm);
		}else{
			pm.setStatus("0");
			pm.setTime(new Date());
			pm.setMac(clientMac);
			pm.setPhone(phone);
			phoneAndMacService.savePM(pm);
		}
		//client+apMac
		SnmpClientofAP obj=apMIBService.findAtoCByRunInfo(clientMac);
		if(obj.getApMac()!=null){
			//新增关联的APAndClient
			APAndClient apc=new APAndClient();
			apc.setApmac(obj.getApMac().toString());
			apc.setClientmac(clientMac);
			apc.setStatus("0");
			apc.setIntime(new Date());
			apAndClientService.saveAPClient(apc);
		}
	}
	
	private String clientToApOfMAC_Exit(String phone,String clientMac){
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("mac", clientMac);
		mp.put("phone", phone);
		//更新WLAN登录用户表
		PhoneAndMac pm=new PhoneAndMac();
		pm=phoneAndMacService.find(mp);
		pm.setStatus("1");
		pm.setTime(new Date());
		phoneAndMacService.updatePM(pm);
		//更改APAndClient
		Map<String,Object> mp1=new HashMap<String,Object>();
		mp1.put("clientmac", clientMac);
		//mp1.put("status", "0");
		String sql="from APAndClient where clientmac= :clientmac order by outtime desc";
		APAndClient apc=apAndClientService.find_Newest(sql,mp1);
		if(apc!=null){
			apc.setStatus("1");
			apc.setOuttime(new Date());
			apAndClientService.updateAPClient(apc);
		}
		APPO appo=apService.findLoginAp(apc.getApmac());
		String str=appo.getAc_name()+"("+appo.getMac()+")";
		return str;
	}
	
	
	//判断是否存在黑名单中
	public Boolean blackUser(String username){
		if(username != null && username.trim()!=""){
			return blackListService.findOneofUsername(username.trim());
		}else{
			//用户名为null或是不合法
			return true;
		}
	}
	//判断是否可以重新获取动态密码了
	public String howMuchTime(String username,String repeat){
		//规则规定的间隔时间（秒）
		Integer repeattime=Integer.valueOf(repeat)*60;
		SMSPWDLogPO po=smsPWDLogService.findNewOne(username);
		if(po==null){
			return "Y";
		}
		Date nowTime=new Date();
		//目前已经间隔的时间
		long a=(nowTime.getTime()-po.getDatetime().getTime())/1000;
		//判断是否已经可以进行申请了
		if(a>repeattime){
			return "Y";
		}else{
			a=repeattime-a;
			return a+"";
		}
		
		
	}	
	
}
