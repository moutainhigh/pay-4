/**
 *
 */
package com.pay.credit.dto.dayrate;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月3日
 */
public class DayRateLogDTO {
	/** 更新时间*/
	private Timestamp updateTime;
	/** 操作人*/
	private String operator;
	/** 新日利率*/
	private BigDecimal newDayRate;
	/** 旧日利率*/
	private BigDecimal oldDayRate;
	/** 主键 */
	private long rateLogId;



	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(final Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(final String operator) {
		this.operator = operator;
	}
	public BigDecimal getNewDayRate() {
		return newDayRate;
	}
	public void setNewDayRate(final BigDecimal newDayRate) {
		this.newDayRate = newDayRate;
	}
	public BigDecimal getOldDayRate() {
		return oldDayRate;
	}
	public void setOldDayRate(final BigDecimal oldDayRate) {
		this.oldDayRate = oldDayRate;
	}
	public long getRateLogId() {
		return rateLogId;
	}
	public void setRateLogId(final long rateLogId) {
		this.rateLogId = rateLogId;
	}

}
