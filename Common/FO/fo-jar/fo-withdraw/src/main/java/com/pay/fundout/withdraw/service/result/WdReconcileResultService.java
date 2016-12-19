 /** @Description 
 * @project 	poss-withdraw
 * @file 		WdReconcileResultService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-23		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.result;

import java.util.Map;

import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawRcResultSummaryDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawReconcileResultDTO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * <p>银行返回结果文件对账service</p>
 * @author Henry.Zeng
 * @since 2010-9-23
 * @see 
 */
public interface WdReconcileResultService {
	/**
	 * 查询银行返回对账结果文件信息汇总
	 * 
	 * @param param
	 * @return
	 */
	public WithdrawRcResultSummaryDTO queryRcResultSummaryInfo(Map<String,Object> param) ;
	
	/**
	 * 查询对账结果明细列表
	 * @param param
	 * @return
	 */
	public Page<WithdrawReconcileResultDTO> queryRcResultListInfo(Page<WithdrawReconcileResultDTO> page, Map<String,Object> param) ;
	
	/**
	 * refuse import
	 * @param params
	 * @throws PossException
	 */
	public void refuseImportRdTx(Map<String,String> params) throws PossException;
	
	/**
	 * refuse import
	 * @param params
	 * @throws PossException
	 */
	public void sureImportRdTx(Map<String,String> params) throws PossException;
	
	/**
	 *  单条确认成功、失败
	 *  @author Jonathen Ni
	 */
	public void singleImportConfirmRdTx(String orderId,String issucc,String resultKy);
	
	/**
	 * 批量确认导入
	 * @param batImportPrms[batchNum1::bankCode1||batchNum2::bankCode2]
	 * @auther Jonathen Ni
	 */
	public void batchSureImport(String batImportPrms) throws PossException;
	
	/**
	 * 退回复核导入文件
	 * @param params
	 * @throws PossException
	 */
	public void refuseReviewImportRdTx(WithdrawImportFileDTO importFileDTO ) throws PossException;
	
	
	
}
