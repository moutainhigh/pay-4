/**
 *  File: ReconcileServiceImpl.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.service.rcresult.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.reconcile.dao.rcresult.QueryReconcileResultDao;
import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultDTO;
import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultSummaryDTO;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResult;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResultSummary;
import com.pay.fundout.reconcile.service.rcresult.QueryReconcileResultService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.tags.codetable.dto.CodeTableDTO;
import com.pay.poss.base.tags.codetable.service.CodeTableService;

/**
 * 对账Service实现
 * @author Sandy_Yang
 */
public class QueryReconcileResultServiceImpl implements QueryReconcileResultService {
	protected transient  Log logger = LogFactory.getLog(getClass());
	
	private QueryReconcileResultDao queryReconcileResultDao;

	@SuppressWarnings("unchecked")
	@Override
	public Page<ReconcileResultSummaryDTO> queryReconcileList(Map<String,Object> map,Page<ReconcileResultSummaryDTO> page) {
		
		Page<ReconcileResultSummary> pageModel = new Page<ReconcileResultSummary>();
		
		PageUtils.setServicePage(pageModel, page);
		
		pageModel = queryReconcileResultDao.queryReconcileList(map, pageModel);
		
		page.setResult((List<ReconcileResultSummaryDTO>) PageUtils.changePageList(pageModel.getResult(), new ReconcileResultSummaryDTO(), null));
		
		PageUtils.setServicePage(page, pageModel);
		
		return page ;
	}
	
	@SuppressWarnings("unchecked")
	public Page<ReconcileResultDTO> queryReconcileDetail(Map<String, Object> map,Page<ReconcileResultDTO> page) {
		
		Page<ReconcileResult> pageModel = new Page<ReconcileResult>();
		
		PageUtils.setServicePage(pageModel, page);
		//{startDate=2010-11-10 00:00:00.0, countFlag=1, allCount=16, busiFlag=0, endDate=2010-11-10 00:00:00.0, withdrawBankId=800}
		if("1".equals(map.get("countFlag")) || "200".equals(String.valueOf(map.get("busiFlag")))){
			pageModel = queryReconcileResultDao.queryReconcileDetailByBank(map, pageModel);
		}else{
			pageModel = queryReconcileResultDao.queryReconcileDetailBySys(map, pageModel);
		}
		page.setResult((List<ReconcileResultDTO>) PageUtils.changePageList(pageModel.getResult(), new ReconcileResultDTO(), null));
		PageUtils.setServicePage(page, pageModel);
		return page ;
	}
	
	public void setQueryReconcileResultDao(
			QueryReconcileResultDao queryReconcileResultDao) {
		this.queryReconcileResultDao = queryReconcileResultDao;
	}

	private CodeTableService codeTableService;
	
	public void setCodeTableService(CodeTableService codeTableService) {
		this.codeTableService = codeTableService;
	}
	
	@Override
	public List<ReconcileResultDTO> queryReconcileDetail2Excel(
			Map<String, Object> map) {
		
		List<ReconcileResult> reconcileResults = null ;
		
		if("1".equals(map.get("countFlag")) || "200".equals(String.valueOf(map.get("busiFlag")))){
			reconcileResults = queryReconcileResultDao.queryRcResultDetailByBank(map);
		}else{
			reconcileResults = queryReconcileResultDao.queryRcResultDetailBySys(map);
		}
		
		List<ReconcileResultDTO> reconcileResultDtos = null;
		
		ReconcileResultDTO reconcileResultDTO = null;
		if(reconcileResults != null && reconcileResults.size() > 0){
			reconcileResultDtos = new ArrayList<ReconcileResultDTO>(reconcileResults.size()) ;
			for(ReconcileResult reconcileResult : reconcileResults ){
				reconcileResultDTO = new ReconcileResultDTO();
				BeanUtils.copyProperties(reconcileResult, reconcileResultDTO);
				CodeTableDTO codeTableDTO;
				try {
					codeTableDTO = codeTableService.getCodeTable(CodeTableService.CODE_TABLE_WITHDRAW_BANK_NAME, reconcileResultDTO.getWithdrawBankId());
					if(null != codeTableDTO){
						reconcileResultDTO.setWithdrawBankId(codeTableDTO.getDescription());
					}
					codeTableDTO = codeTableService.getCodeTable(CodeTableService.CODE_TABLE_WITHDRAW_BUSI_NAME, String.valueOf(reconcileResultDTO.getWithdrawBusiType()));
					if(null != codeTableDTO){
						reconcileResultDTO.setWithdrawBusiTypeDesc(codeTableDTO.getDescription());
					}
					reconcileResultDTO.setTradeAmount(reconcileResultDTO.getTradeAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
				} catch (PossUntxException e) {
					logger.error(e.getMessage(),e);
				}
				
				
				reconcileResultDtos.add(reconcileResultDTO);
			}
		}
		
		return reconcileResultDtos;
	}

	@Override
	public List<ReconcileResultSummaryDTO> queryReconcileList2Excel(
			Map<String, Object> map) {
		
		List<ReconcileResultSummary> summaries = this.queryReconcileResultDao.queryReconcileSummaryList(map);
		
		List<ReconcileResultSummaryDTO> summaryDTOs = null;
		
		ReconcileResultSummaryDTO summaryDTO = null;
		if(summaries != null && summaries.size() > 0){
			summaryDTOs = new ArrayList<ReconcileResultSummaryDTO>(summaries.size());
			for(ReconcileResultSummary summary : summaries ){
				summaryDTO = new ReconcileResultSummaryDTO();
				BeanUtils.copyProperties(summary, summaryDTO);
				
				CodeTableDTO codeTableDTO;
				try {
					codeTableDTO = codeTableService.getCodeTable(CodeTableService.CODE_TABLE_WITHDRAW_BANK_NAME, summaryDTO.getWithdrawBankId());
					if(null != codeTableDTO){
						summaryDTO.setWithdrawBankId(codeTableDTO.getDescription());
					}
					summaryDTO.setAllAccountAmount(summaryDTO.getAllAccountAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					summaryDTO.setAllBankAmount(summaryDTO.getAllBankAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					summaryDTO.setDifferenceAmount(summaryDTO.getDifferenceAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					summaryDTOs.add(summaryDTO);
				} catch (PossUntxException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		return summaryDTOs;
	}
	
	

}
