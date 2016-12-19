/**
 * 
 */
package com.pay.fi.fill.util;

/**
 * @author PengJiangbo
 *
 */
public enum OrderFillResultDesEnum implements IEnum {
	
	auditRefused("审核拒绝"),
	audit_record_not_match("审核记录不符");

	private String reason ;
	
	
	/**
	 * @param reason
	 */
	private OrderFillResultDesEnum(String reason) {
		this.reason = reason;
	}


	public String getReason() {
		return reason;
	}


	@Override
	public String getEnumName() {
		return this.name();
	}

}
