package com.pay.acc.service.cidverify.dto;
/**
 * 公安网是实名认证返回结果集
 * @author lei.jiangl 
 * @version 
 * @data 2010-9-14 上午11:20:00
 */
public class CidResult {
	/**
	 * 公安网原始返回结果
	 */
	private String govResult;
	/**
	 * 实名认证状态 （同DB）1 成功 2 失败   3 认证中
	 */
	private int stateResult;
	/**
	 * 库中无此号
	 */
	private boolean noCid;
	
	public String getGovResult() {
		return govResult;
	}
	public void setGovResult(String govResult) {
		this.govResult = govResult;
	}
	public int getStateResult() {
		return stateResult;
	}
	public void setStateResult(int stateResult) {
		this.stateResult = stateResult;
	}
	public boolean isNoCid() {
		return noCid;
	}
	public void setNoCid(boolean noCid) {
		this.noCid = noCid;
	}
}
