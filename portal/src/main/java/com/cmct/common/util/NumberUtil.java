package com.cmct.common.util;

import java.util.Random;

/**
 * 
 * @title 		
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		jichen
 * @create		2014-3-12 下午3:11:50
 */
public class NumberUtil {
	
	static Random r=new Random();
	static String ssource="0123456789";
	static char[] src =ssource.toCharArray();
	
	//生成指定位数
	private static String randString(int length){
		char[] buf=new  char[length];
		int rmd;
		for(int i=0;i<length;i++){
			rmd=Math.abs(r.nextInt())%src.length;
			buf[i]=src[rmd];
		}
		return new String(buf);
	}
	
	public static String runVerifyCode(int i){
		String verifyCode=randString(i);
		return verifyCode;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NumberUtil rm=new NumberUtil();
		String rondom=rm.runVerifyCode(6);
		System.out.println("rm="+rondom);
	}

}
