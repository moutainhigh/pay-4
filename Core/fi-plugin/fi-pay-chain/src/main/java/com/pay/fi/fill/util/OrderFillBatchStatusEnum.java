/**
 * 
 */
package com.pay.fi.fill.util;

/**
 * 补单审核状态枚举
 * @author PengJiangbo
 *
 */
public enum OrderFillBatchStatusEnum implements IEnum {
	
	/** 初始状态，待审核 */
	orderFillInit(0, "待审核"),
	auditPass(1, "审核通过"),
	auditRefuse(2, "审核拒绝")
	;

	private int status ;
	private String StatusDes ;
	
	/**
	 * @param status
	 * @param statusDes
	 */
	private OrderFillBatchStatusEnum(int status, String statusDes) {
		this.status = status;
		StatusDes = statusDes;
	}

	@Override
	public String getEnumName() {
		return this.name() ;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDes() {
		return StatusDes;
	}

}
