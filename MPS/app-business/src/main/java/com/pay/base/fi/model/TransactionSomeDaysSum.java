/**
 * 
 */
package com.pay.base.fi.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 一段时间的成交量（近30日）
 * @author PengJiangbo
 *
 */
public class TransactionSomeDaysSum {

	/** 交易总量 */
	private Long tranSum ;
	private String createDate;
	
	public Long getTranSum() {
		return tranSum;
	}
	public void setTranSum(Long tranSum) {
		this.tranSum = tranSum;
	}
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) ;
	}
	
}
