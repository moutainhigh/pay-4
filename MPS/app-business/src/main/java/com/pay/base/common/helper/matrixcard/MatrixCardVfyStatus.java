package com.pay.base.common.helper.matrixcard;



/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum MatrixCardVfyStatus {
	// 申请验证
	REQUEST(0),
	
	// 验证成功
	SUCCESS(1),
	
	// 验证失败
	FAIL(2);

	private int value;

	MatrixCardVfyStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		MatrixCardVfyStatus tmpKey = null;
		for (MatrixCardVfyStatus tmpEnum : MatrixCardVfyStatus.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
}
