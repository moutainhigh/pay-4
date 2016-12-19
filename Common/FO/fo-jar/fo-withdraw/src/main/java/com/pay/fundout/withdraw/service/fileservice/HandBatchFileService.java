 /** @Description 
 * @project 	poss-withdraw
 * @file 		HandBatchFileService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-19		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.fileservice;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawAuditQuery;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchFileInfo;


/**
 * <p>手工生成批次文件</p>
 * @author Henry.Zeng
 * @since 2010-9-19
 * @see 
 */
public interface HandBatchFileService {
	/**
	 * <p>
	 * 【workorders=批量工单流水号,isSend=是否发送邮件标识,batchName=前台录入特殊规则名称】
	 * </p>
	 * @param orderSeqs
	 * @param isSend
	 * @param batchName
	 */
	public BatchFileInfo generateBatchInfoRdTx(String workorders , String isSend , String batchName,String withdrawBankCode,String userName) throws PossException;
	
	
	public Page<WithdrawAuditDTO> search(Page<WithdrawAuditDTO> page,
			WithdrawAuditQuery auditQueryDTO );
	
	/**
	 * <p>银行返回文件确认导入</p>
	 * @param mess 格式[
	 * ORDERID=12345678
	 * ISSUCC=1(1:成功,0：失败)
	 * ]
	 * @return true or false
	 * @author Jonathen Ni
	 * @throws PossException
	 */
	public boolean importConfirmRdTx(Map<String,String> map) throws PossException;
	
}
