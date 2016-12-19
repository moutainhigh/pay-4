

package com.pay.base.common.helper.matrixcard;


/**
 * @author jim_chen
 * @version 
 * 2010-9-18 
 */
public enum MatrixCardTransInfoValStatus {
	
	//创建
	NEW(0),	
	//成功
	SUCCESS(1),
	//失败
	FAIL(2),
	//完成
	FINISHED(3)
	;

	private int value;
	
	MatrixCardTransInfoValStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static String getName(int value) {
		MatrixCardTransInfoValStatus tmpKey = null;
		for (MatrixCardTransInfoValStatus tmpEnum : MatrixCardTransInfoValStatus.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}

}
