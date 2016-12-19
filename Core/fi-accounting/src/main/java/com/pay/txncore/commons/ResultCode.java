package com.pay.txncore.commons;


/**
 * 退款错误返回代码与商户接入规范统一
 * @author fred.feng
 */
public enum ResultCode {
	SUCCESS("0000","退款成功"),
	RECORD_HISTTORY_FAIL("0301","退款历史记录失败"),
	PARTER_NOT_EXIST("0302","退款商户不存在"),
	REQUIRES_PARAM_ISNULL("0303","报文必填参数为空"),
	PARTNER_REFUND_TIME_INCORRECT("0304","商户退款订单时间格式不正确"),
	TRADE_ORDER_NOT_EXIST("0305","退款对应的网关订单不存在"),
	PAYMENT_OREDER_NOT_EXIST("0306","退款对应的支付订单不存在"),
	TRANS_NOT_SUCCESS("0307","订单状态不为已成功,不能进行退款"),
	TRADE_ORDER_HAS_REFUND("0308","商户退款订单处理完成,请勿重复退款"),
	REFUND_AMOUNT_INCRORECT("0309","商户退款订单金额不正确"),
	FULL_REFUND_NOT_EQUAL("0310","全额退款类型,退款金额不符"),
	AMOUNT_LARGE_PAY_AMOUNT("0311","退款金额大于支付余额"),
	REQUEST_SERIAL_ID_EXIST("0312","退款请求序列号已经存在"),
	PARTNER_ORDER_ID_EXIST("0313","商户退款订单号已经存在"),
	VERIFY_SIGN_FAIL("0314","报文验签失败"),
	PAYER_NOT_ALLOW_IN("0315","收款方帐户止入"),
	PARTNER_NOT_ALLOWOUTANDIN("0316","商户止入或者止出"),
	VALIDATE_BUSSINESS_FAIL("0317","退款逻辑验证失败"),
	UPDATE_REFUND_AMOUNT_FAIL("0318","更新订单可退金额失败"),
	CALC_FEE_ERROR("0319","手续费用计算失败"),
	REFUND_ORDER_CREATE_ERROR("0320","退款订单创建失败 "),
	DEPOSIT_TRANSACTION_ERROR("0321","帐务退款失败"),
	TRADE_ORDER_FAIL("0322","网关订单异常"),
	DEPOSIT_BACK_FAIL("0323","充退异常"),
	CONSTRUCT_REFUND_FAIL("0324","构建退款订单异常"),
	UPDATE_RETURN_STATUS_FAIL("0325","修改退款订单失败"),
	PARAM_ERROR("0326","报文参数错误"),
	AMOUNT_LARGE_AVAILABLE_AMOUNT("0327","退款金额大于商户可用余额"),
	REFUND_AMOUNT_EQUAL_TO_ZERO("0328","退款金额等于0，或大于可退金额"),
	ORDER_REFUND_COMPLATE("0329","商户退款订单处理完成，请勿重复退款"),
	ORDER_STATUS_NOT_SUCCESS("0330","网关订单非交易完成状态,不可以进行退款"),
	ECARD_REFUND_PAYER_FEE_FAIL("0331","易卡退付款方手续费失败 "),
	VERSION_NOT_MATCH("0332","请求版本不正确"),
	DEPOSIT_BACK_CREATE_ERROR("0333","创建充退订单异常"),
	ERROR_DEST_TYPE("0334","退款目的地类型错误"),
	NOT_SUPPORT_E_CARD("0335","暂不支持易卡退款"),
	NOT_SUPPORT_SPLIT_REFUND("0336","普通网关API不支持分帐退款"),
	NOT_SUPPORT_CONSUME_CARD("0337","消费卡支付订单不支持退款"),
	VERIFY_UN_SIGN_FAIL("0338","消费卡支付订单不支持退款"),
	MORE_THAN_REFUND_AMOUNT("0339","超过可退金额"),
	CHARGEBACK_ORDER_NOT_ALLOWREFUND("0340","该订单存在拒付，不允许退款"),
	;


	private String code;
	private String description;
	

	private ResultCode(String code,String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return description;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static ResultCode getByCode(String code) {
		for (ResultCode status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * 通过枚举<code>description</code>获得枚举
	 * 
	 * @param code
	 * @return
	 */
	public static ResultCode getByDescription(String description) {
		for (ResultCode status : values()) {
			if (status.getDescription().equals(description)) {
				return status;
			}
		}
		return null;
	}
	
	public static String getDescription(String code){
		for (ResultCode status : values()) {
			if (status.getCode().equals(code)) {
				return status.getDescription();
			}
		}
		return null;
	}

}
