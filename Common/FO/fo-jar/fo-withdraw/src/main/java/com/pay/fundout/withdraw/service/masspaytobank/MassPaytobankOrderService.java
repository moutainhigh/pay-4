/**
 *  File: MassPaytobankOrderService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-6      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank;

import java.util.List;

import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankOrderDTO;
import com.pay.fundout.withdraw.model.masspaytobank.MassPaytobankOrderTotalInfo;

/**
 * @author bill_peng
 *
 */
public interface MassPaytobankOrderService {
	/**
	 * 存储付款到银行批量订单信息
	 * @param dto
	 */
	Long saveMassPaytobankOrderInfoRnTx(MassPaytobankOrderDTO dto);
	
	/**
	 * 根据批量订单号获取订单信息
	 * @param massOrderSeq
	 * @return
	 */
	MassPaytobankOrderDTO getMassPaytobankOrderInfo(Long massOrderSeq);
	/**
	 * 根据业务参考号获取订单信息
	 * @param massOrderSeq
	 * @return
	 */
	MassPaytobankOrderDTO getMassPaytobankOrderInfo(String businessNo,Long memberCode);
	/**
	 * 更新批量付款到银行订单信息
	 * @param dto
	 * @return
	 */
	boolean updateMassPaytobankOrderInfo(MassPaytobankOrderDTO dto);
	
	/**
	 * 获取完成的批量付款到银行订单信息
	 * @param status
	 */
	List<MassPaytobankOrderDTO> getCompleteMassPaytobankOrder();
	
	/**
	 * 统计完成的批量订单付款到银行的信息
	 * @param massOrderSeq
	 * @return
	 */
	MassPaytobankOrderTotalInfo totalComplateMassPaytobankOrderInfo(Long massOrderSeq);
	
}
