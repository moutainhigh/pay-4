package com.pay.base.common.helper.matrixcard;



/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum MatrixCardTransType {
	
	REQUEST(0),//申请
	
	BIND(1),//绑定
	
	CHANGE(2),//更换
	
	RESET(3),//重置
	
	UNBIND(4),//解绑
	
	LOGIN(5);//登陆
	
	private int value;
	
	MatrixCardTransType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public static String getName(int value) {
		MatrixCardTransType tmpKey = null;
		for (MatrixCardTransType tmpEnum : MatrixCardTransType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
}
