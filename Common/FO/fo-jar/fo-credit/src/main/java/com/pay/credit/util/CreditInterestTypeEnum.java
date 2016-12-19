/**
 * 
 */
package com.pay.credit.util;

/**
 * @author PengJiangbo
 *
 */
public enum CreditInterestTypeEnum implements IEnumType {
	
	withBorrowAlso("0", "随借随还"),
	instalmentRepayment("1", "分期还款，等额本息"),
	creditOrder("2", "订单授信");
	
	private String code ;
	private String desc ;
	
	/**
	 * @param code
	 * @param desc
	 */
	private CreditInterestTypeEnum(final String code, final String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String getEnumName() {
		return this.name() ;
	}

}
