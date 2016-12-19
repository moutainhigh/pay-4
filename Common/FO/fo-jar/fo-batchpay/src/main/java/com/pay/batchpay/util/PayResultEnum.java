/**
 * 
 */
package com.pay.batchpay.util;

/**
 * 批付 记录状态
 * @author PengJiangbo
 *
 */
public enum PayResultEnum implements IEnum {
	
	/** 付款成功 */
	paySuccess(0),
	/** 付款中 */
	paying(1),
	/** 付款失败 */
	payFailure(2) ;
	
	private int processStatus ;
	
	/**
	 * @param processStatus
	 */
	private PayResultEnum(int processStatus) {
		this.processStatus = processStatus;
	}

	@Override
	public String getEnumName() {
		return this.name();
	}

	public int getProcessStatus() {
		return processStatus;
	}
	
}
