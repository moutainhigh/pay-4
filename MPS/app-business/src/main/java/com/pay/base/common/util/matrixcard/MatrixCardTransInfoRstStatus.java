package com.pay.base.common.util.matrixcard;

public enum MatrixCardTransInfoRstStatus {

	//新建
	NEW(0),

	//验证通过
	VALIDATED(1),
	
	//失效
	INVALIDATED(2),
	
	//完成
	FINISHED(3);
	
	private int value;

	MatrixCardTransInfoRstStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static String getName(int value) {
		MatrixCardTransInfoRstStatus tmpKey = null;
		for (MatrixCardTransInfoRstStatus tmpEnum : MatrixCardTransInfoRstStatus.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
}
