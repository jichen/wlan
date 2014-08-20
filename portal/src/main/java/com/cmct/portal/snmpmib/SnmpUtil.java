package com.cmct.portal.snmpmib;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableUtils;

import com.cmct.portal.interfaces.exception.PortalException;
import com.cmct.portal.po.SnmpAPtoClientAdress;
import com.cmct.portal.po.SnmpApInfo;
import com.cmct.portal.po.SnmpApStatusInfo;


public class SnmpUtil {

	private Snmp snmp = null;

	private Address targetAddress = null;

	public void initComm() throws IOException {
		// 设置Agent方的IP和端口
		targetAddress = GenericAddress.parse("udp:192.168.11.75/161");
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}
	
	public void sendPDU() throws IOException {
		
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("cmcctest"));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		//version1   OID=1.3.6.1.4.1.2011.10.2.75.3.1.2.1.2.64.44.244.12.66.23
		//version2c  OID=1.3.6.1.4.1.2011.10.2.75.3.1.2.1.2
		target.setVersion(SnmpConstants.version2c);
		// 创建 PDU
		PDU pdu = new PDU();
		//h3cDot11StationSSIDName	1,3,6,1,4,1,2011,10,2,75,3,1,1,1,12
		//h3cDot11CurrAPID			1,3,6,1,4,1,2011,10,2,75,3,1,2,1,1
		//h3cDot11CurrRadioID		1,3,6,1,4,1,2011,10,2,75,3,1,2,1,2
		//h3cDot11APObjID			1,3,6,1,4,1,2011,10,2,75,2,1,2,1,3 
		//手机MAC						1,3,6,1,2,1,3,1,1,2,14,1    1,3,6,1,2,1,3,1,1,2
		pdu.add(new VariableBinding(new OID(new int[] { 1,3,6,1,4,1,2011,10,2,75,3,1,2,1 })));
		// MIB的访问方式    GETBULK和GETNEXT可以访问到内部MIB
		pdu.setType(PDU.GETBULK);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		// 解析Response
		if (respEvnt != null && respEvnt.getResponse() != null) {
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			//Vector<? extends VariableBinding> recVBs = respEvnt.getResponse().getVariableBindings();
			
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				System.out.println(recVB.getOid() + " : " + recVB.getVariable());
			}
		}
	}

	public static List<SnmpAPtoClientAdress> getPortAFT(){
		List<SnmpAPtoClientAdress> apToClientList=new ArrayList<SnmpAPtoClientAdress>();
//		Map<Integer,String> AFT=null;
//		ArrayList<String> macSet=new ArrayList<String>();
		int maxRepetitions = 100;
		Snmp protocol=null;
		CommunityTarget target=null;
		Address targetAddress=null;
		TransportMapping transportProtocol=null;
		target=new CommunityTarget();
		targetAddress=GenericAddress.parse("udp:192.168.11.75/161");
		target.setAddress(targetAddress);
		target.setCommunity(new OctetString("cmcctest"));
		target.setRetries(2);
		target.setTimeout(5000);
		target.setVersion(SnmpConstants.version2c);
		try {
			transportProtocol=new DefaultUdpTransportMapping();
			transportProtocol.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		protocol=new Snmp(transportProtocol);
		try {
			protocol.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		PDUFactory pF =new DefaultPDUFactory (PDU.GETNEXT);
        TableUtils tableUtils =new TableUtils(protocol, pF);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        OID[] columns =new OID[1];
        //监控的OID
        String oidstr="1.3.6.1.4.1.2011.10.2.75.3.1.2.1.1" ;
        columns[0] =new OID(oidstr); 
        
        OID lowerBoundIndex =  null;
        OID upperBoundIndex =  null;
        List AFTList =  tableUtils.getTable(target, columns, lowerBoundIndex, upperBoundIndex);
        for ( int j = 0; j < AFTList.size()-1;j++){
        	System.out.println("bbbb========"+AFTList.get(j));
//        	SnmpAPtoClientAdress aca=new SnmpAPtoClientAdress();
//        	int index=AFTList.get(j).toString().indexOf("index");
//        	int indexVbs=AFTList.get(j).toString().indexOf("vbs");
//        	String clientMac=AFTList.get(j).toString().substring(index+6,indexVbs-1);
//        	String containVbs=AFTList.get(j).toString().substring(indexVbs);
//        	int indexFirstEqualSign=containVbs.indexOf("=");
//        	String containFirstEqualSign=containVbs.substring(indexFirstEqualSign+2);
//        	int indexSecondEqualSign=containFirstEqualSign.indexOf("=");
//        	int indexOfEnd=containFirstEqualSign.indexOf("]");
//        	String apSN=containFirstEqualSign.substring(indexSecondEqualSign+2,indexOfEnd);
//        	System.out.println(apSN);
//        	aca.setId(j);
//        	aca.setApSN(apSN);
//        	//10进制转换16进制
//    		String hexadecimal="";
//    		if(StringUtils.isNotEmpty(clientMac)){
//    			String[] str=(clientMac.trim()).split("\\.");
//    			for(int i=0;i<str.length;i++){
//    				hexadecimal=hexadecimal+":"+Integer.toHexString((Integer.valueOf(str[i])));
//    			}
//    			hexadecimal=hexadecimal.substring(1);
//    		}
//        	aca.setClientMac(hexadecimal);
//        	aca.setOid(oidstr);
//        	apToClientList.add(aca);
        }
        return apToClientList;
	}
	
	public static List<SnmpApInfo> getApInfo(String ip,String community,String[] oidstr){
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
        OID[] columns =new OID[3];
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


	
	public static List<SnmpApStatusInfo> getApStatusInfo(String ip,String community,String[] oidstr){
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
	
	//.
	public static void main(String[] args) throws IOException, PortalException {
			com.cmct.portal.interfaces.radius.PortaltoRadius.wlanExit("192.168.11.75", "192.168.200.2");

//			//获取终端挂载信息
////			List<SnmpAPtoClientAdress> apToClientList= 
//			getPortAFT();
////			System.out.println(apToClientList.size());
//			//获取AP信息
////			String[] oidStr=new String[3];
////			oidStr[0]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.2";
////			oidStr[1]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.3";
////			oidStr[2]="1.3.6.1.4.1.2011.10.2.75.12.1.1.1.4";
////			getApInfo("192.168.11.75", "cmcctest", oidStr);
//			
//			//终端挂载AP的MAC地址
//			String[] oidStr=new String[6];
//			oidStr[0]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.2";
//			oidStr[1]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.10";
//			oidStr[2]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.3";
//			oidStr[3]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.5";
//			oidStr[4]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.8";
//			oidStr[5]="1.3.6.1.4.1.2011.10.2.75.2.1.1.1.4";
//			getApStatusInfo("192.168.11.75", "cmcctest", oidStr);			
//			
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
	}
	
	
	
	
}
