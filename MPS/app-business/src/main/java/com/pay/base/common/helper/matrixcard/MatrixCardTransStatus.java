package com.pay.base.common.helper.matrixcard;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum MatrixCardTransStatus {
	
	ACTIVE(0),//创建
	
	FINISH(1),//完成
;
	
	private int value;
	
	MatrixCardTransStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static String getName(int value) {
		MatrixCardTransStatus tmpKey = null;
		for (MatrixCardTransStatus tmpEnum : MatrixCardTransStatus.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
	
}
