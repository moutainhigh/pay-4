/** @Description 
 * @project 	fo-securitycheck
 * @file 		FAMILY.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.common;

/**
 * <p>
 * 投票器家族
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 * @see
 */
public enum VoterFamilyEnum {
	ACCT_FAMILY(100, "账户型投票器"), RISK_FAMILY(200, "风控型投票器"), BUSI_FAMILY(300,
			"业务型投票器"), POLICY_FAMILY(400, "政策型投票器");
	private final int code;
	private final String description;

	private VoterFamilyEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static VoterFamilyEnum valueof(int code) {
		for (VoterFamilyEnum family : values()) {
			if (family.getCode() == code) {
				return family;
			}
		}
		return null;
	}
}
