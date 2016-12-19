/**
 * 
 */
package com.pay.acc.commons;

/**
 * 支付类型（1：担保支付 2：直付 3：充值 4：交易退款 5：充值退款 6：提现7：冲正）
 * @author Administrator
 *
 */
@Deprecated
public enum PayForTypeEnum {
	
	ASSURE(1,"担保支付"),PAYFOR(2,"直付"),DEPOSIT(3,"充值"),REFUND(4,"交易退款"),DRAWBACK(5,"充值退款"),WITHDRAWAL(6,"提现");
	private int code;
	private String message;
	private PayForTypeEnum(int code,String message){
		this.code=code;
		this.message=message;
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
