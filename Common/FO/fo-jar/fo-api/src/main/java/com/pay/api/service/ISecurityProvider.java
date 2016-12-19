/**
 *  File: ISecurityProvider.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-11-18   Sany        Create
 *
 */
package com.pay.api.service;

/**
 * 签名服务
 */
public interface ISecurityProvider {

	/**
	 * 
	 * @param srcData
	 * @param pubKey
	 * @param signMsg
	 * @return
	 */
	public boolean verifySignature(String srcData, String pubKey, String signMsg);
	
	/**
	 * 
	 * @param originalValue
	 * @return
	 */
	public String generateSignature(String srcData);
}
