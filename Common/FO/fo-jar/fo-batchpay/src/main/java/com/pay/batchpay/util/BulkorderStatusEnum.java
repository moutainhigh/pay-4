/**
 * 
 */
package com.pay.batchpay.util;

/**
 * @author PengJiangbo
 *
 */
public enum BulkorderStatusEnum implements IEnum {
	/** 待商户审核 */
	merchantCheck(0),
	/** 商户审核拒绝 */
	merchantCheckRefused(1),
	/** 商户审核通过待复核 */
	merchantCheckPassed(2),
	/** 复核拒绝 */
	possCheckRefused(3),
	/** poss审核通过，付款中 */
	paymenting(4),
	/** 付款完成 */
	payCompletion(5),
	/** 已作废 */
	anandoned(6)
	;

	private int status ;
	
	/**
	 * @param status
	 */
	private BulkorderStatusEnum(int status) {
		this.status = status;
	}

	@Override
	public String getEnumName() {
		return this.name();
	}

	public int getStatus() {
		return status;
	}
	
}
