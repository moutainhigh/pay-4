/**
 * 
 */
package com.pay.fi.chain.util;

/**
 * 支付链相关枚举类
 * 
 * @author PengJiangbo
 *
 */
public enum O2oEnum implements IEnum {

	/** 生效 */
	payLinkEffective(0, "支付链生效"),
	/** 失效 */
	payLinkInvalid(1, "支付链失效");

	private int status;
	private String desc;

	public int getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * @param status
	 * @param desc
	 */
	private O2oEnum(final int status, final String desc) {
		this.status = status;
		this.desc = desc;
	}

	@Override
	public String getEnumName() {
		return this.getEnumName();
	}

}
