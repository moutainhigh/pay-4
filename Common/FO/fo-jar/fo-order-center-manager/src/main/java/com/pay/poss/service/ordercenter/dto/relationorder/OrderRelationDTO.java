/**
 *  File: OrderRelationDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.dto.relationorder;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**<p>关联订单</p>
 * @author Sunsea.Li
 *
 */
public class OrderRelationDTO extends BaseObject {
	private static final long serialVersionUID = 1478013081118622837L;
	/**
	 * 系统交易号
	 */
	private Long orderKy;
	
	/**
	 * 订单类型
	 */
	private Integer orderType;
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 订单时间
	 */
	private Date orderDate;

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
