/**
 *  File: WithdrawOrderAuditServicce.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-29      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.inf.dao.Page;

/**
 * @Title: BatchPay2AccountConsistencyService
 * @Package com.pay.fundout.withdraw.service.orderconsistency
 * @Description: 批量付款的到账户补单接口
 * @author Jonathen Ni(Jonathen_Ni@staff.hnacom)
 * @date 2010-12-29 上午10:53:08
 * @version V1.0
 */
public interface BatchPay2AccountConsistencyService {
	/**
	 * 查询生成批量付款到账户子订单失败的明细文件记录
	 * 
	 * @param batchPay2AcctBatchNum
	 * @return List<MasspayImportRecord>
	 */
	public List<MasspayImportRecord> queryMassPayImpFileRecByGenOrderFail(
			String batchPay2AcctBatchNum, String payerMemberId);

	/**
	 * 根据批次号，将未生成子订单的明细记录重成子订单
	 * 
	 * @param batchPay2AcctBatchNum
	 * @param payerMemberId
	 */
	public void repairMassPay2AcctOrder(String batchPay2AcctBatchNum,
			String payerMemberId, String operator);

	/**
	 * 带分页的查询生成批量付款到账户子订单失败的明细文件记录
	 * 
	 * @param batchPay2AcctBatchNum
	 * @param payerMemberId
	 * @param page
	 * @return
	 */
	public Page<MasspayImportRecordDTO> search(Map paraMap,
			Page<MasspayImportRecordDTO> page);
}
