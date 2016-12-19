package com.pay.pe.helper;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付交易方
 */
public enum Transactor {
	
	PAYER(1), PAYEE(2), ACCOUNT(3);
	
	private int value;

	public int getValue() {
		return value;
	}

	Transactor(int value) {
		this.value = value;
	}

	public final static Map<Transactor, String> transactorMap;

	static {
		transactorMap = new EnumMap<Transactor, String>(Transactor.class);
		transactorMap.put(Transactor.PAYER, "付款方");
		transactorMap.put(Transactor.PAYEE, "收款方");
		transactorMap.put(Transactor.ACCOUNT, "内部科目");
	}

	/**
	 * 返回TRANSACTOR对应的值 Example getValue(TRANSACTOR.PAYEE);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(Transactor key) {

		return key.getValue();
	}

	/**
	 * 返回TRANSACTOR对应的值 Example getTRANSACTORValue("PAYEE");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getTRANSACTORValue(String key) {
		return Transactor.valueOf(key).getValue();
	}

	/**
	 * 返回TRANSACTORMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getTRANSACTORMAPList() {
		return transactorMap.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return TRANSACTOR
	 */
	public static Transactor getTRANSACTORMAPKey(int value) {
		Transactor tmpKey = null;
		for (Transactor tmpEnum : Transactor.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}
		return tmpKey;
	}

	/**
	 * 返回TRANSACTOR对应的描述.
	 * 
	 * @param value
	 *            int.
	 * @return String
	 */
	public static String getTRANSACTORDesc(final int value) {
		return Transactor.transactorMap.get(Transactor
				.getTRANSACTORMAPKey(value));
	}
}