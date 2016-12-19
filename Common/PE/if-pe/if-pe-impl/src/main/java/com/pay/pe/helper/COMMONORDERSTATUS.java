package com.pay.pe.helper;

import java.util.EnumMap;

public enum COMMONORDERSTATUS {
	
	/**
	 * 用户申请成功.
	 */
	UserAppSuccess(101),

	/**
	 * 银行订单申请成功.
	 */
	BankOrderAppSuccess(181),

	/**
	 * 银行订单申请失败.
	 */
	BankOrderAppFail(182),

	/**
	 * 银行订单取消申请成功.
	 */
	BankOrderCancelAppSuccess(183),

	/**
	 * 银行订单取消申请失败.
	 */
	BankOrderCancelAppFail(184),

	/**
	 * 银行订单取消成功.
	 */
	BankOrderCancelSuccess(185),

	/**
	 * 银行订单取消失败.
	 */
	BankOrderCancelFail(186),

	/**
	 * 交易成功.
	 */
	DealSuccess(111),

	/**
	 * 交易失败.
	 */
	DealFail(112),

	/**
	 * 订单过期取消.
	 */
	OrderExpireCancel(190),

	/**
	 * 交易异常.
	 */
	DealException(191),

	/**
	 * 交易后期处理完成.
	 */
	DealAfterEffectAccomplish(113);

	/**
	 * 数值.
	 */
	private int value;

	/**
	 * 构造函数.
	 * 
	 * @param value
	 */
	COMMONORDERSTATUS(int value) {
		this.value = value;
	}

	/**
	 * 有关状态(status name)描述的map.
	 */
	public final static EnumMap<COMMONORDERSTATUS, String> COMMONORDERSTATUSMAP;

	/**
	 * 有关状态（display status）描述的map
	 */
	public final static EnumMap<COMMONORDERSTATUS, String> ORDERSTATUSDISPLAYMAP;

	static {
		COMMONORDERSTATUSMAP = new EnumMap<COMMONORDERSTATUS, String>(COMMONORDERSTATUS.class);
		//COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.UserAppSuccess, "用户申请成功");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.UserAppSuccess, "交易申请成功");  //yongjie update
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.BankOrderAppSuccess, "银行订单申请成功");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.BankOrderAppFail, "银行订单申请失败");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.BankOrderCancelAppSuccess, "银行订单取消申请成功");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.BankOrderCancelAppFail, "银行订单取消申请失败");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.BankOrderCancelSuccess, "银行订单取消成功");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.BankOrderCancelFail, "银行订单取消失败");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.DealSuccess, "交易成功");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.DealFail, "交易失败");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.OrderExpireCancel, "订单过期取消");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.DealException, "交易异常");
		COMMONORDERSTATUSMAP.put(COMMONORDERSTATUS.DealAfterEffectAccomplish, "交易后期处理完成");

		ORDERSTATUSDISPLAYMAP = new EnumMap<COMMONORDERSTATUS, String>(
				COMMONORDERSTATUS.class);  
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.UserAppSuccess, "交易进行中");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.BankOrderAppSuccess, "交易进行中");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.BankOrderAppFail, "交易失败");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.BankOrderCancelAppSuccess, "交易进行中");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.BankOrderCancelAppFail, "交易进行中");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.BankOrderCancelSuccess, "交易失败");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.BankOrderCancelFail, "交易进行中");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.DealSuccess, "交易成功");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.DealFail, "交易失败");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.OrderExpireCancel, "交易失败");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.DealException, "交易失败");
		ORDERSTATUSDISPLAYMAP.put(COMMONORDERSTATUS.DealAfterEffectAccomplish, "交易成功");
	}

	/**
	 * 根据数值取得相应的汉字描述--status name.
	 * 
	 * @param value
	 * @return
	 */
	public static String getOrderStatusDesc(final int value) {
		return COMMONORDERSTATUSMAP.get(COMMONORDERSTATUS.getOrderStatus(value));
	}

	/**
	 * 根据数值取得相应的汉字描述--display status.
	 * 
	 * @param value
	 * @return
	 */
	public static String getOrderStatusDisplayDesc(final int value) {
		return ORDERSTATUSDISPLAYMAP.get(COMMONORDERSTATUS.getOrderStatus(value));
	}

	/**
	 * 根据数值取得相应的枚举.
	 * 
	 * @param value
	 * @return
	 */
	public static COMMONORDERSTATUS getOrderStatus(int value) {
		COMMONORDERSTATUS tempKey = null;
		for (COMMONORDERSTATUS tempEnum : COMMONORDERSTATUS.values()) {
			if (value == tempEnum.value) {
				tempKey = tempEnum;
				break;
			}
		}
		return tempKey;
	}

	/**
	 * @return Returns the value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}
    
}
