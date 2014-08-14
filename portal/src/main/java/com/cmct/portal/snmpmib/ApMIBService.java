package com.cmct.portal.snmpmib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableUtils;
import org.springframework.stereotype.Service;

import com.cmct.portal.po.APAndClient;
import com.cmct.portal.po.SnmpAPtoClientAdress;
import com.cmct.portal.po.SnmpApInfo;
import com.cmct.portal.po.SnmpApStatusInfo;
import com.cmct.portal.po.SnmpClientofAP;

@Service("apMIBService")
public class ApMIBService {
	private static Logger logger = LoggerFactory.getLogger(ApMIBService.class);
	
	
	
	/**
	 * 获取保存的对象列表（clientMac+ApType+ApMac+ApSN）
	 * @param clientMac
	 * @return
	 */
	
	public List<SnmpClientofAP> findAtoCByRunInfoList(String clientMac){
		List<SnmpClientofAP> list=new ArrayList<SnmpClientofAP>();
		List<SnmpAPtoClientAdress> apClientList=getAPtoClient(null,null,null);
		List<SnmpApInfo> apInfoList=getApInfo(null,null,null);
		List<SnmpApStatusInfo> apStatusInfoList=getApStatusInfo(null,null,null);
		if(!apClientList.isEmpty()){
			for(SnmpAPtoClientAdress aca:apClientList){
				SnmpClientofAP cofap=new SnmpClientofAP();
				cofap.setApSN(aca.getApSN());
				cofap.setClientMac(aca.getClientMac());
				if(!apInfoList.isEmpty()){
					for(SnmpApInfo apinfo:apInfoList){
						if(apinfo.getApElementSerialID().equals(aca.getApSN())){
							cofap.setApElementTemplateName(apinfo.getApElementTemplateName());
							break;
						}
					}
				}
				if(!apStatusInfoList.isEmpty() && StringUtils.isNotEmpty(cofap.getApElementTemplateName())){
					for(SnmpApStatusInfo apStatusinfo:apStatusInfoList){
						if(apStatusinfo.getApTemplateName().equals(cofap.getApElementTemplateName())){
							cofap.setApMac(apStatusinfo.getApMac());
							cofap.setApStatus(apStatusinfo.getApStatus());
							break;
						}
					}
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 获取保存的对象（clientMac+ApType+ApMac+ApSN）
	 * @param clientMac
	 * @return
	 */
	public SnmpClientofAP findAtoCByRunInfo(String clientMac){
		String ip="192.168.11.75";
		String community="cmcctest";
		SnmpClientofAP bean=new SnmpClientofAP();
		
		String[] apClientOIDStr=new String[3];
		apClientOIDStr[0]="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.1";
		apClientOIDStr[1]="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.2";
		apClientOIDStr[2]="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.3";
		List<SnmpAPtoClientAdress> apClientList=getAPtoClient(ip,community,apClientOIDStr);
		
		String[] apInfoOIDStr=new String[3];
		apInfoOIDStr[0]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.2";
		apInfoOIDStr[1]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.3";
		apInfoOIDStr[2]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.4";
		List<SnmpApInfo> apInfoList=getApInfo(ip,community,apInfoOIDStr);
		
		String[] apStatusOIDStr=new String[6];
		apStatusOIDStr[0]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.2";
		apStatusOIDStr[1]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.10";
		apStatusOIDStr[2]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.3";
		apStatusOIDStr[3]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.5";
		apStatusOIDStr[4]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.8";
		apStatusOIDStr[5]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.4";
		List<SnmpApStatusInfo> apStatusInfoList=getApStatusInfo(ip,community,apStatusOIDStr);
		
		if(!apClientList.isEmpty()&&!apInfoList.isEmpty()&&!apStatusInfoList.isEmpty()){
			for(SnmpAPtoClientAdress a:apClientList){
				if(a.getClientMac().equals(clientMac)){
					bean.setApSN(a.getApSN());
					bean.setClientMac(clientMac);
					break;
				}
			}
			if(StringUtils.isNotEmpty(bean.getApSN())){
				for(SnmpApInfo b:apInfoList){
					if(b.getApElementSerialID().equals(bean.getApSN())){
						bean.setApElementTemplateName(b.getApElementTemplateName());
						break;
					}
				}
				if(StringUtils.isNotEmpty(bean.getApElementTemplateName())){
					for(SnmpApStatusInfo c:apStatusInfoList){
						if(c.getApTemplateName().equals(bean.getApElementTemplateName())){
							bean.setApStatus(c.getApStatus());
							bean.setApMac(c.getApMac());
							bean.setApIp(c.getApIPv4());
							break;
						}
					}
				}
			}
			
		}
		return bean;
	}
	
	
	public List<APAndClient> mibDataNew(){
		String ip="192.168.11.75";
		String community="cmcctest";
		//终端信息
		String[] apClientOIDStr=new String[3];
		apClientOIDStr[0]="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.1";
		apClientOIDStr[1]="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.2";
		apClientOIDStr[2]="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.3";
		List<SnmpAPtoClientAdress> apClientList=getAPtoClient(ip,community,apClientOIDStr);
		
		//关联表
		String[] apInfoOIDStr=new String[3];
		apInfoOIDStr[0]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.2";
		apInfoOIDStr[1]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.3";
		apInfoOIDStr[2]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.4";
		List<SnmpApInfo> apInfoList=getApInfo(ip,community,apInfoOIDStr);
		
		//ap信息和状态
		String[] apStatusOIDStr=new String[6];
		apStatusOIDStr[0]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.2";
		apStatusOIDStr[1]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.10";
		apStatusOIDStr[2]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.3";
		apStatusOIDStr[3]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.5";
		apStatusOIDStr[4]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.8";
		apStatusOIDStr[5]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.4";
		List<SnmpApStatusInfo> apStatusInfoList=getApStatusInfo(ip,community,apStatusOIDStr);
		
		
		List<APAndClient> list=new ArrayList<APAndClient>();
		if(apClientList!=null &&apClientList.size()>0){
			for(SnmpAPtoClientAdress a:apClientList){
				//加载数据
				APAndClient aPAndClient=new APAndClient();
				aPAndClient.setClientmac(a.getClientMac());
				String sn=a.getApSN();
				//搜索数据
				if(apInfoList!=null && apInfoList.size()>0){
					for(SnmpApInfo b:apInfoList){
						if(b.getApElementSerialID().equals(sn)){
							String eName=b.getApElementTemplateName();
							if(apStatusInfoList!=null && apStatusInfoList.size()>0){
								for(SnmpApStatusInfo c:apStatusInfoList){
									if(c.getApTemplateName().equals(eName)){
										aPAndClient.setApmac(c.getApMac());
										if(c.getApStatus().equals("5")){
											aPAndClient.setStatus("0");
										}else{
											aPAndClient.setStatus("1");
										}
									}
								}
							}
						}
					}
				}
				list.add(aPAndClient);
			}
		}
		return list;
		
	}
	
	
	/**
	 * 获取ap和终端的关联数据
	 * @param ip
	 * @param community
	 * @param oid
	 * @return
	 */
	private List<SnmpAPtoClientAdress> getAPtoClient(String ip,String community,String[] oidstr){
		List<SnmpAPtoClientAdress> apToClientList=new ArrayList<SnmpAPtoClientAdress>();
		int maxRepetitions = 100;
		Snmp protocol=null;
		CommunityTarget target=null;
		Address targetAddress=null;
		TransportMapping transportProtocol=null;
		//192.168.11.75
		target=new CommunityTarget();
		targetAddress=GenericAddress.parse("udp:"+ip+"/161");
		target.setAddress(targetAddress);
		//"cmcctest"
		target.setCommunity(new OctetString(community));
		target.setRetries(2);
		target.setTimeout(5000);
		target.setVersion(SnmpConstants.version2c);
		try {
			transportProtocol=new DefaultUdpTransportMapping();
			transportProtocol.listen();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		protocol=new Snmp(transportProtocol);
		try {
			protocol.listen();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}	
		PDUFactory pF =new DefaultPDUFactory (PDU.GETNEXT);
        TableUtils tableUtils =new TableUtils(protocol, pF);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        //3个OID
        OID[] columns =new OID[3];
        for(int i=0;i<oidstr.length;i++){
        	columns[i] =new OID(oidstr[i]); 
        }
        OID lowerBoundIndex =  null;
        OID upperBoundIndex =  null;
        List AFTList =  tableUtils.getTable(target, columns, lowerBoundIndex, upperBoundIndex);
        for ( int j = 0; j < AFTList.size();j++){
        	System.out.println(AFTList.get(j).toString());
        	SnmpAPtoClientAdress aca=new SnmpAPtoClientAdress();
        	int index=AFTList.get(j).toString().indexOf("index");
        	int indexVbs=AFTList.get(j).toString().indexOf("vbs");
        	String clientMac=AFTList.get(j).toString().substring(index+6,indexVbs-1);
        	String containVbs=AFTList.get(j).toString().substring(indexVbs);
        	int indexOfStart=containVbs.indexOf("[");
        	int indexOfEnd=containVbs.indexOf("]");
        	String dataLine=containVbs.substring(indexOfStart+1, indexOfEnd);
        	String[] str=dataLine.split(",");
        	String apSN=str[0].split("=")[1];
        	apSN=apSN.trim();
        	aca.setId(j);
        	aca.setApSN(apSN);
        	//10进制转换16进制
    		String hexadecimal=convHexadecimal(clientMac);
        	aca.setClientMac(hexadecimal);
        	apToClientList.add(aca);
        }
        return apToClientList;
	}
	
	/**
	 * 获取Ap的SN号和的信息用于对应
	 * @param ip
	 * @param community
	 * @param oidstr
	 * @return
	 */
	private List<SnmpApInfo> getApInfo(String ip,String community,String[] oidstr){
		List<SnmpApInfo> apInfoList=new ArrayList<SnmpApInfo>();
		int maxRepetitions = 100;
		Snmp protocol=null;
		CommunityTarget target=null;
		Address targetAddress=null;
		TransportMapping transportProtocol=null;
		target=new CommunityTarget();
		targetAddress=GenericAddress.parse("udp:"+ip+"/161");
		target.setAddress(targetAddress);
		target.setCommunity(new OctetString(community));
		target.setRetries(2);
		target.setTimeout(5000);
		target.setVersion(SnmpConstants.version2c);
		try {
			transportProtocol=new DefaultUdpTransportMapping();
			transportProtocol.listen();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		protocol=new Snmp(transportProtocol);
		try {
			protocol.listen();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}	
		PDUFactory pF =new DefaultPDUFactory (PDU.GETNEXT);
        TableUtils tableUtils =new TableUtils(protocol, pF);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        //3个OID
        OID[] columns =new OID[3];
        for(int i=0;i<oidstr.length;i++){
        	columns[i] =new OID(oidstr[i]); 
        }
        OID lowerBoundIndex =  null;
        OID upperBoundIndex =  null;
        List AFTList =  tableUtils.getTable(target, columns, lowerBoundIndex, upperBoundIndex);
        //解析数据
        for ( int j = 0; j < AFTList.size();j++){
        	int indexVbs=AFTList.get(j).toString().indexOf("vbs");
        	int indexOfEnd=AFTList.get(j).toString().indexOf("]");
        	String dataLine=AFTList.get(j).toString().substring(indexVbs+5,indexOfEnd);
        	String[] dataStr=dataLine.split(",");
        	String[] saveData=new String[3];
        	for(int k=0;k<dataStr.length;k++){
        		String col=dataStr[k];
        		String[] colStr=col.split("=");
        		if(!StringUtils.isEmpty(colStr[1])){
        			saveData[k]=colStr[1].trim();
        		}else{
        			saveData[k]="";
        		}
        	}
        	SnmpApInfo apInfo=new SnmpApInfo();
        	apInfo.setApElementTemplateName(saveData[0]);
        	apInfo.setApElementSerialID(saveData[1]);
        	apInfo.setApElementModelAlias(saveData[2]);
        	apInfoList.add(apInfo);
        }
        return apInfoList;
	}
	/**
	 * Ap运行信息
	 * @param ip
	 * @param community
	 * @param oidstr
	 * @return
	 */
	private List<SnmpApStatusInfo> getApStatusInfo(String ip,String community,String[] oidstr){
		List<SnmpApStatusInfo> apStatusInfoList=new ArrayList<SnmpApStatusInfo>();
		int maxRepetitions = 100;
		Snmp protocol=null;
		CommunityTarget target=null;
		Address targetAddress=null;
		TransportMapping transportProtocol=null;
		target=new CommunityTarget();
		targetAddress=GenericAddress.parse("udp:"+ip+"/161");
		target.setAddress(targetAddress);
		target.setCommunity(new OctetString(community));
		target.setRetries(2);
		target.setTimeout(5000);
		target.setVersion(SnmpConstants.version2c);
		try {
			transportProtocol=new DefaultUdpTransportMapping();
			transportProtocol.listen();
		} catch (IOException e) {
			e.getStackTrace();
		}
		protocol=new Snmp(transportProtocol);
		try {
			protocol.listen();
		} catch (IOException e) {
			e.getStackTrace();
		}	
		PDUFactory pF =new DefaultPDUFactory (PDU.GETNEXT);
        TableUtils tableUtils =new TableUtils(protocol, pF);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        //3个OID
        OID[] columns =new OID[6];
        for(int i=0;i<oidstr.length;i++){
        	columns[i] =new OID(oidstr[i]); 
        }
        OID lowerBoundIndex =  null;
        OID upperBoundIndex =  null;
        List AFTList =  tableUtils.getTable(target, columns, lowerBoundIndex, upperBoundIndex);
        for ( int j = 0; j < AFTList.size();j++){
        	int indexVbs=AFTList.get(j).toString().indexOf("vbs");
        	int indexOfEnd=AFTList.get(j).toString().indexOf("]");
        	String dataLine=AFTList.get(j).toString().substring(indexVbs+5,indexOfEnd);
        	String[] dataStr=dataLine.split(",");
        	String[] saveData=new String[6];
        	for(int k=0;k<dataStr.length;k++){
        		String col=dataStr[k];
        		String[] colStr=col.split("=");
        		if(!StringUtils.isEmpty(colStr[1])){
        			saveData[k]=colStr[1].trim();
        		}else{
        			saveData[k]="";
        		}
        	}
        	SnmpApStatusInfo apStatusInfo=new SnmpApStatusInfo();
        	apStatusInfo.setApIPv4(saveData[0]);
        	apStatusInfo.setApIPv6(saveData[1]);
        	apStatusInfo.setApMac(saveData[2]);
        	apStatusInfo.setApTemplateName(saveData[3]);
        	apStatusInfo.setApConnectionType(saveData[4]);
        	apStatusInfo.setApStatus(saveData[5]);
        	apStatusInfoList.add(apStatusInfo);
        }
        return apStatusInfoList;
	}
	
	
	/**
	 * 10进制转换16进制
	 * @param decimal
	 * @return
	 */
	private String convHexadecimal(String decimal){
		String hexadecimal = "";
		String strHex="";
		if(StringUtils.isNotEmpty(decimal)){
			String[] str=decimal.split("\\.");
			for(int i=0;i<str.length;i++){
				String ipstr=Integer.toHexString((Integer.valueOf(str[i])));
				if(ipstr.length()!=2){
					ipstr="0"+ipstr;
				}
				strHex=strHex+":"+ipstr;
			}
		}
		if(strHex.length()>5){
			hexadecimal=strHex.substring(1);
		}
		return hexadecimal;
		
	}
	
}
