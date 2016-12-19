/**
 *
 */
package com.pay.credit.dto.dayrate;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.pay.inf.dto.MutableDto;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月1日
 */
public class DayRateDTO implements MutableDto {

	private static final long serialVersionUID = 3629270359422003196L;
	/**创建时间*/
	private Timestamp createTime;
	/** 更新时间*/
	private Timestamp updateTime;
	/**主键*/
	private long rateId;
	/** 操作人*/
	private String operator;
	/** 日利率*/
	private BigDecimal dayRate;

	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(final Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(final Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public long getRateId() {
		return rateId;
	}
	public void setRateId(final long rateId) {
		this.rateId = rateId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(final String operator) {
		this.operator = operator;
	}
	public BigDecimal getDayRate() {
		return dayRate;
	}
	public void setDayRate(final BigDecimal dayRate) {
		this.dayRate = dayRate;
	}

}
