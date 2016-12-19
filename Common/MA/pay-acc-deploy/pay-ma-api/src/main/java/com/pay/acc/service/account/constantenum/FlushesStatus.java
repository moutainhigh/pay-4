package com.pay.acc.service.account.constantenum;

public enum FlushesStatus {

	CREATE(0, "创建"), SUCCESS(1, "冲正成功");
	private int code;
	private String message;

	private FlushesStatus(int code, String message) {
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
