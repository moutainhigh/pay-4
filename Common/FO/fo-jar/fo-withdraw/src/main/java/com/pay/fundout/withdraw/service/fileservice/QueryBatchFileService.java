 /** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchFileService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-8		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.fileservice;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.fileservice.WebQueryWithDrawDTO;
import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawRcResultSummaryDTO;
import com.pay.fundout.withdraw.dto.ruleconfig.BatchRuleConfigDTO;
import com.pay.fundout.withdraw.model.fileservice.WithdrawBatchInfo;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchFileInfo;

/**
 * <p>查询批次文件Sercvice</p>
 * @author Henry.Zeng
 * @since 2010-9-8
 * @see 
 */
public interface QueryBatchFileService {
	/**
	 * 查询批次文件进行分页
	 * @param webQueryWithDrawDTO
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public Page<WithdrawBatchInfoDTO> queryBatchFile(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO);
	
	/**
	 * 【batchNum：批次号
	 * 	 userId：     登录人ID	
	 * 】
	 * 废除批次
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public void invalidBatchFileRdTx(Map<String,Object> outMap) throws PossException;
	
	/**
	 * 生成批次文件
	 * @param batchNum
	 * @return
	 */
	public void generateBatchFile(Map<String,Object> outMap) ;
	
	
	/**
	 * 重成批次
	 * @param oldBatchNum
	 * @return
	 */
	public void  regenerateBatchFile(Map<String,Object> outMap);
	
	/**
	 * 查询提交银行文件
	 * @param page
	 * @param webQueryWithDrawDTO
	 * @return
	 */
	public Page<WithdrawBatchInfoDTO> queryBankFile(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO) ;
	
	/**
	 * 查询批次信息
	 * @param page
	 * @param webQueryWithDrawDTO
	 * @return
	 */
	public Page<WithdrawBatchInfoDTO> queryBatchInfo(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO);
	/**
	 * 查询批次规则信息
	 * @return
	 */
	public List<BatchRuleConfigDTO> queryBatchRuleConfigList();
	/**
	 * 生成报警批次
	 * @param outMap
	 */
	public void regenerateBatch(Map<String,Object> outMap);
	
	/**
	 * 查询批次文件信息
	 * @param batchNum
	 * @param fileType
	 * @return
	 */
	public BatchFileInfo downloadCallBackRdtx(String batchNum,Long fileType,String channelCode,String bankCode,String fileKy,String fileBusiType)throws PossException ;
	/**
	 * 
	 * @param page
	 * @param webQueryWithDrawDTO
	 * @return
	 */
	public Map<String,Object> queryImportedFileList(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO);
	
	/**
	 * 查询导入批次详细
	 * @param batchNum
	 * @auther Jonathen Ni
	 */
	public WithdrawRcResultSummaryDTO showBatchDetail(String batchNum);
	
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public BatchFileInfo getPathByReviewFoFile(Map<String,String> paramMap) ;
	
	/**
	 * 处理复核操作
	 * @param paramMap
	 * @return
	 */
	public boolean processorReviewFoFile(WithdrawImportFileDTO importFileDto) throws Exception;

	public List<WithdrawBatchInfo> queryBatchInfo(
			WebQueryWithDrawDTO webQueryWithDrawDTO);
	
	
	
}
