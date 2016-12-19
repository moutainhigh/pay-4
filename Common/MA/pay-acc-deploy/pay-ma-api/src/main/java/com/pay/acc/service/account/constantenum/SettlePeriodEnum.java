package com.pay.acc.service.account.constantenum;

/** 
* @ClassName: SettlePeriodEnum 
* @Description:结算周期枚举
* @author cf
* @date 2011-11-7 下午02:58:49 
*  
*/

public enum SettlePeriodEnum {

	/***
	 * 结算周期：0为t+0工作日 1为 非t+0工作日  2为 t+1工作日  3为非t+1工作日 4为t+2工作日 5为t+2非工作日
	 * 2012-03-06
	 * 修改:
	 *  增加以下结算信息：T+3,T+3顺延,T+4,T+4顺延,T+5,T+5顺延,由于该商户目前无法进行配置，因此希望本周四晚上上线
	*/
	
	T_AND_0_WORK_DAY(0,"T+0"),
	T_AND_0_NOT_WOKE_DAY(1,"T+0顺延"),
	
	T_AND_1_WORK_DAY(2,"T+1"),
	T_AND_1_NOT_WORK_DAY(3,"T+1顺延"),
	
	T_AND_2_WORK_DAY(4,"T+2"),
	T_AND_2_NOT_WORK_DAY(5,"T+2顺延"),
	
	T_AND_3_WORK_DAY(6,"T+3"),
	T_AND_3_NOT_WORK_DAY(7,"T+3顺延"),
	
	T_AND_4_WORK_DAY(8,"T+4"),
	T_AND_4_NOT_WORK_DAY(9,"T+4顺延"),
	
	T_AND_5_WORK_DAY(10,"T+5"),
	T_AND_5_NOT_WORK_DAY(11,"T+5顺延");
	
	
	/*目前市场人员默认的结算方式说法为T+0以及T+0节假日顺延，故后台系统中结算方式修改为：“T+0工作日”改为“T+0”，“非T+0工作日”改为"T+0顺延"，"T+1工作日"改为"T+1"
	“非T+1工作日”改为"T+1顺延"，"T+2工作日"改为"T+2",“非T+2工作日”改为"T+2顺延"，
	*/
	
	private int code;
	private String message;

	private SettlePeriodEnum(int code, String message) {
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
