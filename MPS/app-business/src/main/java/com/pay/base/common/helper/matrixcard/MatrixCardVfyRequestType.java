package com.pay.base.common.helper.matrixcard;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum MatrixCardVfyRequestType {
	//绑定
	BIND(0),
	//更换
	CHANGE(1),
	//重置
	RESET(2),
	//登录
	LOGIN(3),
	//支付
	PAY(4);

	private int value;

	MatrixCardVfyRequestType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		MatrixCardVfyRequestType tmpKey = null;
		for (MatrixCardVfyRequestType tmpEnum : MatrixCardVfyRequestType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
}
