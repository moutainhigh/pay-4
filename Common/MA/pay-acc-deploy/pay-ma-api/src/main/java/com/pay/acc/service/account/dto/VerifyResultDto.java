package com.pay.acc.service.account.dto;

import java.io.Serializable;

/**验证支付，登陆密码返回的信息
 * @author jim_chen
 * @version 
 * 2010-11-19 
 */
public class VerifyResultDto implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	int leavingTime;//剩余次数
	int totalTime;//总的次数
	int leavingMinute;//剩余分钟
	public int getLeavingTime() {
    	return leavingTime;
    }
	public VerifyResultDto setLeavingTime(int leavingTime) {
    	this.leavingTime = leavingTime;
    	return this;
    }
	public int getTotalTime() {
    	return totalTime;
    }
	public VerifyResultDto setTotalTime(int totalTime) {
    	this.totalTime = totalTime;
    	return this;
    }
	public int getLeavingMinute() {
    	return leavingMinute;
    }
	public VerifyResultDto setLeavingMinute(int leavingMinute) {
    	this.leavingMinute = leavingMinute;
    	return this;
    }
	

}
