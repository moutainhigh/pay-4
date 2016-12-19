/**
 *  File: MasspayBatchOrderService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount;

import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportFileDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayBatchOrder;
import com.pay.inf.exception.AppException;
import com.pay.poss.base.exception.PossException;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface MasspayBatchOrderService {
	/**
	 * 创建批量订单
	 * 
	 * @param masspayBatchOrder
	 * @return
	 */
	public long createMasspayBatchOrder(MasspayBatchOrder masspayBatchOrder);

	/**
	 * 产生批量订单号
	 * 
	 * @return
	 */
	public Long getSeqId();

	/**
	 * 当月金额
	 * 
	 * @return
	 */
	public Long getMonthTotalAmount(Long memberCode);

	/**
	 * 当日金额
	 * 
	 * @return
	 */
	public Long getDayTotalAmount(Long memberCode);

	/**
	 * 当月笔数
	 * 
	 * @return
	 */
	public Integer getMonthTotalCount(Long memberCode);

	/**
	 * 当日笔数
	 * 
	 * @return
	 */
	public Integer getDayTotalCount(Long memberCode);
	
	/**
	 * 创建总订单
	 */
	public void createBatchOrderRnTx(MasspayBatchOrder batchOrder,MasspayImportFileDTO importFile) throws PossException;

	/**
	 * 从付款方到中间科目
	 * @param batchOrder
	 * @param importFile
	 * @return
	 * @throws PossException
	 */
	public Integer updateAmountRdTx(MasspayBatchOrder batchOrder, MasspayImportFileDTO importFile) throws PossException;

	
	/**
	 * 复核通过请求信息
	 * @param importFile
	 * @param batchOrder
	 * @throws AppException
	 */
	public Integer passRequestRdTx(MasspayImportFileDTO importFile, MasspayBatchOrder batchOrder)throws AppException;
	
	/**
	 * 复核拒绝请求信息
	 *  @param importFile
	 * @return
	 * @throws AppException
	 */
	public boolean rejectRequestRdTx(MasspayImportFileDTO importFile)throws AppException;
	

	
	/**
	 * 生成明细订单
	 * @param batchOrderSeq
	 */
	public void createMassPayToAccountDetialOrder(Long batchOrderSeq);
	
	/**
	 * 处理完成的批量付款到账户的总订单信息
	 */
	public void processCompleteMassPaytoAcctOrder();
}
