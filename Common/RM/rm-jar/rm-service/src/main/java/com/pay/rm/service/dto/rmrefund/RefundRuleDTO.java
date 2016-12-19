package com.pay.rm.service.dto.rmrefund;

import com.pay.inf.model.BaseObject;

/**
 * 充退规则
 */
public class RefundRuleDTO extends BaseObject {

	private static final long serialVersionUID = 3542702601172451615L;
	private int cbType;// 充值退回类型ID
	private String typeName;// 类型名称
	private String typeDesc;// 类型说明
	private int expireDays;// 有效期天数
	private int expireTimes;// 每天有效次数

	public int getCbType() {
		return cbType;
	}

	public void setCbType(int cbType) {
		this.cbType = cbType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public int getExpireDays() {
		return expireDays;
	}

	public void setExpireDays(int expireDays) {
		this.expireDays = expireDays;
	}

	public int getExpireTimes() {
		return expireTimes;
	}

	public void setExpireTimes(int expireTimes) {
		this.expireTimes = expireTimes;
	}
}
