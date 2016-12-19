/**
 *  File: RefundWorkOrderAndM.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15    jason_wang      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class RefundWorkOrderAndM extends BaseObject {

	private static final long serialVersionUID = -5339559451209019793L;

	private RefundWorkorder refundWorkorder;	//充退工单表
	
	private RefundOrderM refundOrderM;			//充退订单主表

	public RefundWorkorder getRefundWorkorder() {
		return refundWorkorder;
	}

	public void setRefundWorkorder(RefundWorkorder refundWorkorder) {
		this.refundWorkorder = refundWorkorder;
	}

	public RefundOrderM getRefundOrderM() {
		return refundOrderM;
	}

	public void setRefundOrderM(RefundOrderM refundOrderM) {
		this.refundOrderM = refundOrderM;
	}
	
	
}

