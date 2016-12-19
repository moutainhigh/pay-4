
package com.pay.base.common.helper.matrixcard;



/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public enum MatrixCardVfyType {
	
	//新卡绑定验证申请
	NEWCARD_VFY(0),
	
	//已绑定卡验证申请
	NORMALCARD_VFY(1);

	private int value;

	MatrixCardVfyType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		MatrixCardVfyType tmpKey = null;
		for (MatrixCardVfyType tmpEnum : MatrixCardVfyType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}

		}
		return tmpKey.name();
	}
}
