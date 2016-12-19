/**
 * 
 */
package com.pay.credit.util;

/**
 * 授信还款方式枚举
 * @author PengJiangbo
 *
 */
public enum CreditRepayWayEnum implements IEnumType {
	
	systemWithHolding("S", "系统代扣"),
	bankTransfer("B", "银行转账"),
	autoRepayment("X", "自动还款");
	
	private String code ;
	private String desc ;
	
	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 * @param desc
	 */
	private CreditRepayWayEnum(final String code, final String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getEnumName() {
		return this.name();
	}

}
