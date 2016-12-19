/**
 * 
 */
package com.pay.base.fi.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 网关订单表，昨日交易统计
 * @author PengJiangbo
 *
 */
public class TransactionYesterdaySum {

	/** 昨日交易总量 */
	private Long yesterdaySum ;
	
	/** 昨日交易成功量 */
	private Long yesterdaySuccess ;

	public Long getYesterdaySum() {
		return yesterdaySum;
	}

	public void setYesterdaySum(final Long yesterdaySum) {
		this.yesterdaySum = yesterdaySum;
	}

	public Long getYesterdaySuccess() {
		return yesterdaySuccess;
	}

	public void setYesterdaySuccess(final Long yesterdaySuccess) {
		this.yesterdaySuccess = yesterdaySuccess;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) ;
	}
	
}
