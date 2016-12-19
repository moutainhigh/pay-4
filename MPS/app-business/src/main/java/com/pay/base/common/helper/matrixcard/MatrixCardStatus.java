package com.pay.base.common.helper.matrixcard;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum MatrixCardStatus {
	
	CREATE(0),//创建
	
	BIND(1),//绑定
		
	UNBIND(2),//解绑
	
	INVALIDATE(3);//失效
	
	
	private int value;
	
	MatrixCardStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static String getName(int value) {
		MatrixCardStatus tmpKey = null;
		for (MatrixCardStatus tmpEnum : MatrixCardStatus.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
}
