package com.pay.acc.service.account.constantenum;

public enum OperateTypeEnum {
	FREEZE(1, "冻结"), UNFREEZE(2, "解冻"),
	FREEZE_IN(3, "止入"), UNFREEZE_IN(4, "解除止入"), FREEZE_OUT(5, "止出"),
	UNFREEZE_OUT(6, "解除止出")
	;
	private int code;
	private String message;

	private OperateTypeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
