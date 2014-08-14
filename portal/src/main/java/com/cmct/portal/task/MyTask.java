package com.cmct.portal.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import javax.servlet.ServletContext;

import com.cmct.common.SpringContext;
import com.cmct.portal.po.APAndClient;
import com.cmct.portal.po.PhoneAndMac;
import com.cmct.portal.service.APAndClientService;
import com.cmct.portal.service.PhoneAndMacService;
import com.cmct.portal.snmpmib.ApMIBService;

public class MyTask extends TimerTask{

	private static boolean isRun = false;
    private ServletContext context;
    
    public MyTask(ServletContext context)
    {
      this.context = context;
    }
    
	@Override
	public void run() {
		if(!isRun){
				isRun = true;
				context.log("正在执行所加载的任务");
				//去mib拿在线数据,更新
				taskMibData();
				isRun =false;
				context.log("任务顺利完成");
	     }else{
	          System.out.println("任务还没有执行完毕");
	     }
	}
	private void taskMibData(){
		ApMIBService apMIBService=(ApMIBService)SpringContext.getbean("apMIBService");
		APAndClientService apAndClientService=(APAndClientService)SpringContext.getbean("apAndClientService");
		
		Map<String,Object> mp =new HashMap<String,Object>();
		mp.put("status","0");

		//数据库中数据
		List<APAndClient> list2=apAndClientService.findAllWhere(mp);
		//mib实时数据
		List<APAndClient> list1=apMIBService.mibDataNew();
		//需要更新的数据（在线）
		List<APAndClient> list3=new ArrayList<APAndClient>();
		//需要更新的数据（下线）
		List<APAndClient> list4=new ArrayList<APAndClient>();
		//获取更新数据(在线)
		if(list1!=null && list1.size()>0){
			for(APAndClient a1:list1){
				if(list2!=null && list2.size()>0){
					for(APAndClient a2:list2){
						//判断是否在数据库中，并且是在线状态
						if(a1.getClientmac().equals(a2.getClientmac())){
							if(!a1.getApmac().equals(a2.getApmac())){
								a2.setApmac(a1.getApmac());
								list3.add(a2);
							}
						}
					}
				}
			}
		}
		//获取更新数据(下线),去除mib中在线的数据，剩余的就是离线的数据
		if(list2!=null && list2.size()>0){
			for(int i=list2.size()-1;i>=0;i--){
				for(APAndClient a:list1){
					if(list2.get(i).getClientmac().equals(a.getClientmac())){
						list2.remove(i);
						break;
					}
				}
			}
		}
		Date d=new Date();
		if(list2!=null && list2.size()>0){
			for(APAndClient a2:list2){
				a2.setOuttime(d);
				a2.setStatus("1");
			}
		}
		apAndClientService.updateList(list3);
		apAndClientService.updateList(list2);
		
		if(list2!=null && list2.size()>0){
			PhoneAndMacService phoneAndMacService=(PhoneAndMacService)SpringContext.getbean("phoneAndMacService");
			List<PhoneAndMac> pmList=new ArrayList<PhoneAndMac>();
			for(APAndClient a2:list2){
				mp.put("mac",a2.getClientmac());
				List<PhoneAndMac> pAndmList=phoneAndMacService.finALLWhere(mp);
				pmList.addAll(pAndmList);
			}
			if(pmList!=null && pmList.size()>0){
				for(PhoneAndMac entity:pmList){
					entity.setStatus("1");
					entity.setTime(d);
					phoneAndMacService.updatePM(entity);
				}
			}
		}
	}
}
