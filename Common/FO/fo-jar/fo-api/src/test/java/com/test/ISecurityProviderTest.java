/**
 *  File: ISecurityProviderTest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Feb 7, 2012   ch-ma     Create
 *
 */
package com.test;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.api.service.ISecurityProvider;

/**
 * 
 */
public class ISecurityProviderTest extends AbstractTestNG {

	@Resource(name = "api-securityProvider")
	private ISecurityProvider iSecurityProvider;

	@Test
	public void testRsa() {

		String ss = "";

		for (int i = 0; i < 200; i++) {
			ss += "A";
		}
		System.out.println("#:"+ss.length());
		String out = iSecurityProvider.generateSignature(ss);
		System.out.println(out);
	}
}
