/**
 * 
 */
package com.pay.credit.util;

/**
 * 授信出款方式
 * @author PengJiangbo
 *
 */
public enum CreditFoType implements IEnumType {
	virtualAccount("W", "提现账户"),
	bankAccount("B", "银行账户");
	
	private String code ;
	private String desc ;
	
	/**
	 * @param code
	 * @param desc
	 */
	private CreditFoType(final String code, final String desc) {
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
