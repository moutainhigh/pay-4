package com.pay.txncore.dto;

import java.io.Serializable;

/**
 * 验证支付密码结果DTO
 */

public class VerifyPayPasswordResultDTO implements Serializable{
	
	private static final long serialVersionUID = -8198126527951171693L;

	// 1成功:2账户没有锁定，密码出错3:账户锁定
	private Integer resultStatus;
	
	private String errorCode;
	
	private String errorMsg;
	
    private Long leavingTime; // 剩余次数
    
    private Long totalTime;	  // 总的次数
    
    private Long leavingMinute;//剩余分钟(剩余多少时间可以解锁，按分钟算)

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(Long leavingTime) {
		this.leavingTime = leavingTime;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public Long getLeavingMinute() {
		return leavingMinute;
	}

	public void setLeavingMinute(Long leavingMinute) {
		this.leavingMinute = leavingMinute;
	}

	@Override
	public String toString() {
		return "VerifyPayPasswordResultDTO [errorCode=" + errorCode
				+ ", errorMsg=" + errorMsg + ", leavingMinute=" + leavingMinute
				+ ", leavingTime=" + leavingTime + ", resultStatus="
				+ resultStatus + ", totalTime=" + totalTime + "]";
	}
    
    
}
