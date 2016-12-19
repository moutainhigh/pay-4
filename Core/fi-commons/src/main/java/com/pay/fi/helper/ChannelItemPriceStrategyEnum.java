/**
 * 
 */
package com.pay.fi.helper;

/**
 * @author chaoyue
 *
 */
public enum ChannelItemPriceStrategyEnum {

	BOCS("10076001", "10402"),
	BOCM("10079001", "10302"),
	BOCI("10080001", "10302"),
	ABC("10002001", "10602"),
	CREDORAX("10075001","10502"),
	;

	private String code;
	private String desc;

	private ChannelItemPriceStrategyEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static boolean isExists(String code) {

		for (ChannelItemPriceStrategyEnum item : ChannelItemPriceStrategyEnum.values()) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static ChannelItemPriceStrategyEnum getChannelItemByCode(String code){
		for(ChannelItemPriceStrategyEnum item:ChannelItemPriceStrategyEnum.values()){
			if(item.getCode().equals(code)){
				return item;
			}
		}
		return null;
	}
}
