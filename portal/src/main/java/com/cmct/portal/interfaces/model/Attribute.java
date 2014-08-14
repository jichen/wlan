package com.cmct.portal.interfaces.model;

/**
 * 
 * @title 		
 * @description	
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		jichen
 * @create		2014-3-19 下午2:32:03
 */
public class Attribute {

	private int attrType;
	
	private int attrLength;
	
	private byte[] value;

	public int getAttrType() {
		return attrType;
	}

	public void setAttrType(int attrType) {
		this.attrType = attrType;
	}

	public int getAttrLength() {
		return attrLength;
	}

	public void setAttrLength(int attrLength) {
		this.attrLength = attrLength;
	}

	public  byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}
