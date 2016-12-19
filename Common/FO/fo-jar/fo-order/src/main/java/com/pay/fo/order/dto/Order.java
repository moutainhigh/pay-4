/**
 * 
 */
package com.pay.fo.order.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.pay.fo.order.common.OrderProcessType;

/**
 * @author NEW
 *
 */
public abstract class Order {
	/**
	 * 订单处理类型
	 */
	private OrderProcessType processType;

	/**
	 * @return the processType
	 */
	public OrderProcessType getProcessType() {
		return processType;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(OrderProcessType processType) {
		this.processType = processType;
	}
	
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
