/**
 *  File: BackFundMentOrderRepairService.java
 *  Description:
 *  Copyright 2010-12-29 pay Corporation. All rights reserved.
 *  2010-12-29      Sean_yi      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;

public interface BackFundMentOrderRepairService {

	/**
	 * 查询没有生成退款订单的数据
	 * @return
	 */
	public List<WithdrawOrderAppDTO> getNoBackFundMentOrderData(Map<String, Object> map);
	
	/**
	 * 修复没有生成退款订单数据
	 * @param dto
	 */
	public boolean  repairBackFundMentOrder(WithdrawOrderAppDTO dto);
	
}
