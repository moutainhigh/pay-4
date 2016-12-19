package com.pay.acc.service.account.constantenum;

/** 
* @ClassName: RiskGradeEnum 
* @Description: 风险等级枚举
* @author cf
* @date 2011-11-7 下午02:58:30 
*  
*/

public enum RiskGradeEnum {


	RISK_ZERO(200, "5级"), 
	RISK_ONE(201,  "4级"),
	RISK_TWO(202, "3级"),
	RISK_THREE(203, "2级"), 
	RISK_FOUR(204, "1级")
	;
	
	private int code;
	private String message;

	private RiskGradeEnum(int code, String message) {
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
