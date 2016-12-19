 /** @Description 
 * @project 	poss-withdraw
 * @file 		WithDrawBatchAccountFacadeService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-11		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.accountmdp;

import java.util.HashMap;

/**
 * <p>发送MQ批量记账</p>
 * @author Henry.Zeng
 * @since 2010-10-11
 * @see 
 */
public interface BatchSendAccountFacadeService {
	
	/**
	 * 【 workOrderId】: 工单编号
	 * 【 orderId】:     订单编号 
	 * 【 issucc】:      是否成功  1成功 0 失败
	 * @param params
	 */
	public void sendMq2BatchAccount(HashMap<String,String> params) ;
	
}
