/**
 * 
 */
package com.pay.fi.chain.util;

/**
 * 支付链费用枚举类
 * 
 * @author PengJiangbo
 *
 */
public enum FeeEnum implements IEnum {

	/** 运费 */
	freight(99001, "运费"),
	/** 服务费 */
	serviceCharge(99002, "服务费"),
	/** 税费 */
	taxation(99003, "税费"),
	/** 其他费用 */
	otherCharge(99004, "其他费用");

	private int code;
	private String desc;

	/**
	 * @param code
	 * @param desc
	 */
	private FeeEnum(final int code, final String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getEnumName() {
		return this.getEnumName();
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
