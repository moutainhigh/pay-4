/**
 *  File: SignType.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.helper;

/**
 * 
 */
public enum SignType {

	RSA("1"), MD5("2"),DES("3");

	private String value;

	private SignType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static SignType getSignType(String value) {
		for (SignType code : values()) {
			if (code.getValue() == value) {
				return code;
			}
		}
		return null;
	}
}
