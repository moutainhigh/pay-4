package com.pay.rm.service.model.rmrefund;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 用户充退记录
 */
public class UserRefundRecord extends BaseObject {

	private static final long serialVersionUID = 848325533221353062L;
	private long userId;// 用户ID
	private Date recordDate;// 记录日期
	private int chargeBackTimes;// 充退次数

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public int getChargeBackTimes() {
		return chargeBackTimes;
	}

	public void setChargeBackTimes(int chargeBackTimes) {
		this.chargeBackTimes = chargeBackTimes;
	}
}
