package com.cmct.common.util;
import java.security.MessageDigest;

public class Passwordmd5 {
	
	public static String MD5(String inStr){
		MessageDigest md5=null;
		try{
			md5=MessageDigest.getInstance("MD5");
		}catch(Exception e){
			return "";
		}
		char [] charArray=inStr.toCharArray();
		byte [] byteArray=new byte[charArray.length];
		
		for(int i=0;i< charArray.length;i++){
			byteArray[i]=(byte)charArray[i];
		}
		
		byte [] Md5bytes=md5.digest(byteArray);
		StringBuffer hexValue= new StringBuffer();	
		
		for(int i=0;i< Md5bytes.length;i++){	
			int val= ((int)Md5bytes[i]) & 0xff ;
			if(val<16){
				hexValue.append(0);
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	//¿ÉÄæ
	public static String KL(String inStr){
		char[] a =inStr.toCharArray();
		for(int i=0;i<a.length;i++){
			a[i]=(char)(a[i] ^ 't');
		}
		String k=new String(a);
		return k;
	}
	
	//½âÃÜ
	public static String JM(String inStr){
		char[] a =inStr.toCharArray();
		for(int i=0;i<a.length;i++){
			a[i]=(char)(a[i] ^ 't');
		}
		String k=new String(a);
		return k;
	}

	/**
	 * test
	 
	public static void main(String[] args) {
		System.out.println("--------------¼ÓÃÜ-------------");
		System.out.println(MD5("4321"));

	}
	*/


}
