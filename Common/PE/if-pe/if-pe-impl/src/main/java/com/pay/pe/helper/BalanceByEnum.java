package com.pay.pe.helper;

/**
 * 余额方式
 * 
 */
public enum BalanceByEnum {

	ADD(1), DIVE(2);
	private int value;

	public int getValue() {
		return value;
	}

	BalanceByEnum(int value) {
		this.value = value;
	}

	/**
	 * 返回ACCTLEVEL对应的值 Example getValue(ACCTLEVEL.GENERAL);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(BalanceByEnum key) {

		return key.getValue();
	}

}
