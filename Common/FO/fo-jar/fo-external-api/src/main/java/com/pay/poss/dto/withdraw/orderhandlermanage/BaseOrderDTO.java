/**
 *  File: BaseOrderDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.orderhandlermanage;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.pay.poss.service.accounting.Order;

/**
 * 通用订单DTO
 * @author zliner
 *
 */
public class BaseOrderDTO implements Serializable,Order {
	
	private int innerStatus; 
	
	
	public int getInnerStatus() {
		return innerStatus;
	}

	public void setInnerStatus(int innerStatus) {
		this.innerStatus = innerStatus;
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
