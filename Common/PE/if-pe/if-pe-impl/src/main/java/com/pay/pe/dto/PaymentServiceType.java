package com.pay.pe.dto;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付服务类型
 */
public enum PaymentServiceType {

	TRANSACTION(1), BILLING(2), COSTING(3), EXCHANGE(4);

	private int value;

	public int getValue() {
		return value;
	}

	PaymentServiceType(int value) {
		this.value = value;
	}

	public final static Map<PaymentServiceType, String> paymentServiceTypeMap;

	static {
		paymentServiceTypeMap = new EnumMap<PaymentServiceType, String>(
				PaymentServiceType.class);
		paymentServiceTypeMap.put(PaymentServiceType.TRANSACTION, "交易支付服务");
		paymentServiceTypeMap.put(PaymentServiceType.BILLING, "计费支付服务");
		paymentServiceTypeMap.put(PaymentServiceType.COSTING, "成本支付服务");
		paymentServiceTypeMap.put(PaymentServiceType.EXCHANGE, "兑换支付服务");
	}

	/**
	 * 返回PAYMENTSERVICETYPE对应的值 Example getValue(PAYMENTSERVICETYPE.BILLING);
	 * 
	 * @param key
	 * @return int
	 */
	public static int getValue(PaymentServiceType key) {
		return key.getValue();
	}

	/**
	 * 返回PAYMENTSERVICETYPETYPE对应的值 Example
	 * getPAYMENTSERVICETYPEValue("BILLING");
	 * 
	 * @param key
	 * @return int
	 */
	public static int getPAYMENTSERVICETYPEValue(String key) {
		return PaymentServiceType.valueOf(key).getValue();
	}

	/**
	 * 返回PAYMENTSERVICETYPEMAP Entry list
	 * 
	 * @return Iterator
	 */
	public static Iterator getPAYMENTSERVICETYPEMAPList() {
		return paymentServiceTypeMap.entrySet().iterator();
	}

	/**
	 * 跟据value返回枚举对应的key
	 * 
	 * @param value
	 * @return PAYMENTSERVICETYPE
	 */
	public static PaymentServiceType getPAYMENTSERVICETYPEMAPKey(int value) {
		PaymentServiceType tmpKey = null;
		for (PaymentServiceType tmpEnum : PaymentServiceType.values()) {
			if (tmpEnum.value == value) {
				tmpKey = tmpEnum;
				break;
			}
		}
		return tmpKey;
	}

	/**
	 * 返回PAYMENTSERVICETYPE对应的描述.
	 * 
	 * @param value
	 *            int.
	 * @return String
	 */
	public static String getPAYMENTSERVICETYPEDesc(final int value) {
		return PaymentServiceType.paymentServiceTypeMap.get(PaymentServiceType
				.getPAYMENTSERVICETYPEMAPKey(value));
	}
}