/** @Description 
 * @project 	fo-securitycheck
 * @file 		DenyVoteMsg.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-30		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.model;


/**
 * <p>
 * 投票的拒绝提示
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-30
 * @see
 */
public class DenyVoteMsg {
	private String code="000000";
	private String[] tips;


	public String getCode() {
		return code;
	}

	public String[] getTips() {
		return tips;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setTips(String[] tips) {
		this.tips = tips;
	}

}
