package com.pay.fi.commons;


/**
 * 终端类型
 * @author peiyu.yang
 * @since 2015年8月20日14:09:07
 */
public enum TerminalType {
	
    PC("00"),MOBILE("01"),OTHER("02");
    
    private String code;
    
    private TerminalType(String code){
    	this.code = code;
    }
    
	public static boolean isExists(String code) {
		for (TerminalType item : TerminalType.values()) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public String getCode() {
		return code;
	}
}
