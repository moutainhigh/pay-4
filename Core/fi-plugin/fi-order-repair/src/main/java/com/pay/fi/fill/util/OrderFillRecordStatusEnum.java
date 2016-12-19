/**
 * 
 */
package com.pay.fi.fill.util;

/**
 * 补单记录明细信息状态枚举
 * @author PengJiangbo
 *
 */
public enum OrderFillRecordStatusEnum implements IEnum {
	init(0, "初始"),
	success(1, "补单成功"),
	failed(2, "补单失败");
	
	private int code ;
	private String desc ;
	
	/**
	 * @param code
	 * @param desc
	 */
	private OrderFillRecordStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String getEnumName() {
		return this.name();
	}

}
