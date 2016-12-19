/**
 * 
 */
package com.pay.fo.bankcorp.common;

/**
 * @author new
 *
 */
public enum ReturnCodeEnum {
	ERROR("9999","异常"),
	SUCCESS("0000","成功"),
	PROCESSING("0001","处理中");
	
	private String value;
	private String desc;
	
	private ReturnCodeEnum(String val,String desc){
		this.value = val;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public ReturnCodeEnum get(String val){
		for (ReturnCodeEnum element : ReturnCodeEnum.values()) {
			if(element.getValue().equals(val)){
				return element;
			}
		}
		return null;
	}
	
	
}
