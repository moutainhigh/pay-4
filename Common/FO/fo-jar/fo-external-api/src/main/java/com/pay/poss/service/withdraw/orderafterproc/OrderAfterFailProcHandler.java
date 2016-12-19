 /** @Description 
 * @project 	poss-external-api
 * @file 		OrderAfterFailProcHandler.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-25		Rick_lv			Create 
*/
package com.pay.poss.service.withdraw.orderafterproc;

import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;

/**
 * <p>
 * 订单后处理失败处理服务
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-25
 * @see
 */
public interface OrderAfterFailProcHandler {

	public void execute(OrderFailProcAlertModel alertModel);

}
