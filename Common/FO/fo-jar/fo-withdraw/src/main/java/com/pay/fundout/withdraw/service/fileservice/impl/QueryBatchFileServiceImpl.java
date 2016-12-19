 /** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchFileServiceImpl.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-9		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.fileservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.dao.result.WdReconcileResultDao;
import com.pay.fundout.withdraw.dto.fileservice.WebQueryWithDrawDTO;
import com.pay.fundout.withdraw.dto.fileservice.WithdrawBatchInfoDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.dto.result.WithdrawRcResultSummaryDTO;
import com.pay.fundout.withdraw.dto.ruleconfig.BatchRuleConfigDTO;
import com.pay.fundout.withdraw.model.fileservice.QueryBatchWithDraw;
import com.pay.fundout.withdraw.model.fileservice.WithdrawBatchInfo;
import com.pay.fundout.withdraw.model.result.WithdrawRcResultSummary;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.schedule.StartTask;
import com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;

/**
 * <p>查询批次文件Service</p>
 * @author Henry.Zeng
 * @since 2010-9-9
 * @see 
 */
public class QueryBatchFileServiceImpl implements QueryBatchFileService {
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	private QueryBatchFileDao queryBatchFileDao;
	
	private WdReconcileResultDao recResultDao;
	
	

	public void setQueryBatchFileDao(QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}
	
	public void setRecResultDao(WdReconcileResultDao recResultDao) {
		this.recResultDao = recResultDao;
	}

	@SuppressWarnings("unchecked")
	public Page<WithdrawBatchInfoDTO> queryBankFile(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO) {
		
		final QueryBatchWithDraw withdrawBatchInfo = new QueryBatchWithDraw();
		
		Page<WithdrawBatchInfo> pageService = new Page<WithdrawBatchInfo>();
		
		PageUtils.setServicePage(pageService, page);
		
		BeanUtils.copyProperties(webQueryWithDrawDTO, withdrawBatchInfo);
		
		pageService = queryBatchFileDao.findWdSubmitBank4Page(pageService, withdrawBatchInfo);
		
		WithdrawBatchInfoDTO batchInfoDTO = new WithdrawBatchInfoDTO();
		
		page.setResult((List<WithdrawBatchInfoDTO>) PageUtils.changePageList(pageService.getResult(), batchInfoDTO,null));
		
		PageUtils.setServicePage(page, pageService);
		
		return page;
	}
	
	
	public Map<String,Object> queryImportedFileList(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO){
		
		Map<String,Object> map = new HashMap<String, Object>();
//		String paramStatus = WithDrawConstants.BATCH_FILE_STATUS_4 + "," + WithDrawConstants.BATCH_FILE_STATUS_5;
//		webQueryWithDrawDTO.setParamStatus(paramStatus);
		page = queryBankFile(page, webQueryWithDrawDTO);
		map.put("page", page);
		return map;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<WithdrawBatchInfoDTO> queryBatchFile(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO) {
		
		final QueryBatchWithDraw withdrawBatchInfo = new QueryBatchWithDraw();
		
		Page<WithdrawBatchInfo> pageService = new Page<WithdrawBatchInfo>();
		
		PageUtils.setServicePage(pageService, page);
		
		BeanUtils.copyProperties(webQueryWithDrawDTO, withdrawBatchInfo);
		
		withdrawBatchInfo.setBusiType("1");
		
		pageService = queryBatchFileDao.findWdBatchInfo4Page( pageService, withdrawBatchInfo);
		
		WithdrawBatchInfoDTO batchInfoDTO = new WithdrawBatchInfoDTO();
		
		
		page.setResult((List<WithdrawBatchInfoDTO>) PageUtils.changePageList(pageService.getResult(), batchInfoDTO,null));
		
		PageUtils.setServicePage(page, pageService);
		
		return page;
	}
	
	/**
	 * 是否废除批次文件状态
	 * @param batchFileStatus
	 * @return
	 */
	private boolean isInvalidBatchFile(Long batchFileStatus) {
		if(4 == batchFileStatus ||
				5 == batchFileStatus ||
					6 == batchFileStatus || 
						7 == batchFileStatus || 
							8 == batchFileStatus ||
								9 == batchFileStatus){
			return true;
		}else {
			return false;
		}
	}
	
	private boolean hasCheckFile(String flag){
		if(null == flag || "0".equals(flag) || "2".equals(flag)){//没有复核文件
			return false;
		}else{//有复核文件
			return true;
		}
	}
	
	@Override
	public void invalidBatchFileRdTx(Map<String,Object> outMap) throws PossException{
		
		try{
			String batchNum = (String)outMap.get("batchNum");
			BatchFileInfo batchFileInfo = new BatchFileInfo();
			batchFileInfo.setBatchNum(batchNum);
			batchFileInfo.setFileType(22L);
			
			List<BatchFileInfo> batchFileInfos = queryBatchFileDao.queryBatchFileInfos(batchFileInfo);
			if(null != batchFileInfos){
				Map<String,Object> params =  new HashMap<String,Object>();
				for(BatchFileInfo temp:batchFileInfos){ 
					//对于没有复核文件的,下载之后即不能被废除
					params.put("bankCodeRule", temp.getBankCode()+"_"+temp.getFileBusiType());
					String flag = (String) queryBatchFileDao.findObjectByCriteria("fundoutBatch.fundout-batch-flow-query", params);
					if( (!hasCheckFile(flag) && (3 == temp.getBatchFileStatus() || isInvalidBatchFile(temp.getBatchFileStatus())) ) 
							|| ( hasCheckFile(flag) && isInvalidBatchFile(temp.getBatchFileStatus()) ) ){
						log.warn("该银行文件"+temp.getBatchFileStatusDesc()+"状态不可被废除：==="+temp.getBatchFileStatus()+","+temp.getBankCode()+","+flag);
						outMap.put("infos", "该银行文件"+temp.getBatchFileStatusDesc()+"状态不可被废除");
						return ;
					}
				}
			}
			
			//更新工单表状态为已废除
			WithdrawWorkorder withdrawWorkorder = new WithdrawWorkorder();
			withdrawWorkorder.setBatchNum(batchNum);
			withdrawWorkorder.setBatchStatus(2);
			withdrawWorkorder.setStatus(0);
			boolean intStatusFetch = queryBatchFileDao.update("wdfileservice.fundout-withdraw-update-workorder-fetch", withdrawWorkorder);
			
			//更新批次信息为已废除并且是提现
			BatchInfo batchInfoParam = new BatchInfo();
			batchInfoParam.setBatchNum(batchNum);
			batchInfoParam.setBatchType("200002");
			batchInfoParam.setStatus(3L);
			intStatusFetch = queryBatchFileDao.update("schedule.fo-UpdateBatchInfo-fetch24", batchInfoParam);
			if(intStatusFetch){
				log.error("批次状态修改失败,返回结果0条,批次号:"+batchNum);
				throw new Exception("批次状态修改失败,返回结果0条,批次号:"+batchNum);
			}
			
			//更新批次文件信息表为已废除
			BatchFileInfo batchFileInfoParam = new BatchFileInfo();
			batchFileInfoParam.setBatchNum(batchNum);
			batchFileInfoParam.setBatchFileStatus(6L);
			intStatusFetch = queryBatchFileDao.update("schedule.fo-UpdateBatchFileInfo-fetch", batchFileInfoParam);
			
			//删除复核导入的数据
			queryBatchFileDao.delete("wdfileservice.deleteBatchFileRecordforFileKy", batchNum);			
			
			outMap.put("infos", WithDrawConstants.SCUESS_INVAILD_BATCH_INFO);
		}catch(Exception exception){
			log.error("invalidBatchFileRdTx is error..."+exception.getMessage(),exception);
			throw new PossException("invalidBatchFileRdTx is error...", ExceptionCodeEnum.TASK_EXCEPTION);
		}
		
	}

	@Override
	public void generateBatchFile(Map<String,Object> outMap) {
		String batchNum = (String)outMap.get("batchNum");
		try{
			//调用生成批次接口 
			batchNum = StartTask.getInstance().reBuildFile(batchNum);		
			outMap.put("infos", "手工生成批次文件成功");
		}catch(Exception e) {
			outMap.put("infos", WithDrawConstants.FAILURE_GENERATE_BATCH_FILE);
		}
	}


	@Override
	public void regenerateBatchFile(Map<String,Object> outMap) {
		String oldBatchNum = (String)outMap.get("batchNum") ;
		try{
			//调用重成批次接口 
			String newBatchNum = StartTask.getInstance().reBuildBatch(oldBatchNum);
			BatchInfo batchInfo = new BatchInfo();
			batchInfo.setBatchNum(newBatchNum);
			batchInfo.setBatchName(outMap.get("batchName").toString());
			queryBatchFileDao.updateWdBatchInfo(batchInfo);
			
			if(newBatchNum == null) outMap.put("infos", "重成批次失败");
			outMap.put("batchNum", newBatchNum);
			outMap.put("infos", "重成批次成功");
		}catch(Exception e) {
			outMap.put("infos", WithDrawConstants.FAILURE_GENERATE_BATCH_FILE);
		}
	}
	@Override
	public void regenerateBatch(Map<String,Object> outMap) {
//		String ruleId = (String)outMap.get("ruleId") ;
		try{
			//调用重成批次接口 
			//String newBatchNum = StartTask.getInstance().(oldBatchNum);
			//if(newBatchNum == null) outMap.put("infos", "重成批次失败");
			//outMap.put("batchNum", newBatchNum);
			outMap.put("infos", "重成批次成功");
		}catch(Exception e) {
			outMap.put("infos", WithDrawConstants.FAILURE_GENERATE_BATCH_FILE);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchRuleConfigDTO> queryBatchRuleConfigList(){
		BatchRuleConfigDTO batchRuleConfigDTO = new BatchRuleConfigDTO();
		List<BatchRuleConfigDTO> ruleConfigDTOs = (List<BatchRuleConfigDTO>) PageUtils.changePageList(queryBatchFileDao.queryBatchRuleConfig(), batchRuleConfigDTO, null);
		return ruleConfigDTOs;
	}

	@Override
	public BatchFileInfo downloadCallBackRdtx(String batchNum, Long fileType,String channelCode,String bankCode,String fileKy,String fileBusiType)throws PossException {
		try{
			//下载银行明细 更新batchFileInfo状态为已下载。下载时间更新
			if(12 == fileType || 22 == fileType){
				BatchFileInfo batchFileInfoParam = new BatchFileInfo();  /**** 下载文件  ****/
				batchFileInfoParam.setBatchFileStatus(3L);
				batchFileInfoParam.setFileType(fileType);
				batchFileInfoParam.setBatchNum(batchNum);
				batchFileInfoParam.setChannelCode(channelCode);
				batchFileInfoParam.setDownloadTime(new Date()); 
				//queryBatchFileDao.updateBatchFileInfo(batchFileInfoParam);
				/**
				 * FOPJ-975 下载批次文件：批次文件状态 2--->3 防并发处理 by jason_li 2011.1.24
				 */
				boolean intFetchRow = queryBatchFileDao.update("schedule.fo-UpdateBatchFileInfo23", batchFileInfoParam);
				
				if(!intFetchRow){
					log.warn("FOPJ-975 下载批次文件：批次文件状态 2--->3 防并发处理,更新失败batchNum:"+batchNum);
//					throw new Exception("FOPJ-975 下载批次文件：批次文件状态 2--->3 防并发处理,更新失败batchNum:"+batchNum);
				}
			
				//对于没有复核文件的，下载后即可手工处理结果了
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("bankCodeRule", bankCode+"_"+fileBusiType);
				String flag = (String) queryBatchFileDao.findObjectByCriteria("fundoutBatch.fundout-batch-flow-query", params);
				if(null==flag || "0".equals(flag) || "2".equals(flag)){
					WithdrawWorkorder withdrawWorkorder = new WithdrawWorkorder();
					withdrawWorkorder.setBatchNum(batchNum);
					withdrawWorkorder.setFileKy(Long.valueOf(fileKy));
					withdrawWorkorder.setStatus(7);
					
					intFetchRow = queryBatchFileDao.update("wdfileservice.fundout-withdraw-update-workorder47", withdrawWorkorder);
				}
				
			}
			BatchFileInfo batchFileInfo = new BatchFileInfo();
			batchFileInfo.setFileType(fileType);
			batchFileInfo.setBatchNum(batchNum);
			batchFileInfo.setChannelCode(channelCode);
			 BatchFileInfo queryBatchFileInfo = queryBatchFileDao.queryBatchFileInfo(batchFileInfo);
			 return queryBatchFileInfo;
		}catch(Exception e){
			log.error("downloadCallBackRdtx error..."+e.getMessage(),e);
			throw new PossException("downloadCallBackRdtx error...", ExceptionCodeEnum.TASK_EXCEPTION);
		}
	}
	
	public WithdrawRcResultSummaryDTO showBatchDetail(String batchNum) {
		WithdrawRcResultSummary wdRcResSummary = null;
		WithdrawRcResultSummaryDTO wdRcResSummaryDto = new WithdrawRcResultSummaryDTO();
		if(batchNum!=null&&!batchNum.equals("")){
			Map<String,String> parMap = new HashMap<String,String>();
			parMap.put("batchNum", batchNum);
			parMap.put("category", "2");
			wdRcResSummary = this.recResultDao.showBatchDetail(parMap);
			if(wdRcResSummary != null){
				BeanUtils.copyProperties(wdRcResSummary, wdRcResSummaryDto);
			}
		}
		return wdRcResSummaryDto;
	}

	/**
	 * 查询批次信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<WithdrawBatchInfoDTO> queryBatchInfo(Page<WithdrawBatchInfoDTO> page , WebQueryWithDrawDTO webQueryWithDrawDTO) {
		
		final QueryBatchWithDraw withdrawBatchInfo = new QueryBatchWithDraw();
		
		Page<WithdrawBatchInfo> pageService = new Page<WithdrawBatchInfo>();
		
		PageUtils.setServicePage(pageService, page);
		
		BeanUtils.copyProperties(webQueryWithDrawDTO, withdrawBatchInfo);
		
		withdrawBatchInfo.setBusiType("1");
		
		pageService = queryBatchFileDao.findBatchInfoPage( pageService, withdrawBatchInfo);
		
		WithdrawBatchInfoDTO batchInfoDTO = new WithdrawBatchInfoDTO();
		
		page.setResult((List<WithdrawBatchInfoDTO>) PageUtils.changePageList(pageService.getResult(), batchInfoDTO,null));
		
		PageUtils.setServicePage(page, pageService);
		
		return page;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService#getPathByReviewFoFile(java.util.Map)
	 */
	@Override
	public BatchFileInfo getPathByReviewFoFile(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		
		BatchFileInfo batchFileInfo = new BatchFileInfo();
		batchFileInfo.setFileType(Long.valueOf(paramMap.get("fileType")));
		batchFileInfo.setBatchNum(paramMap.get("batchNum"));
		batchFileInfo.setChannelCode(paramMap.get("channelCode"));
	   
		batchFileInfo = queryBatchFileDao.queryBatchFileInfo(batchFileInfo);
		
		return batchFileInfo;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService#processorReviewFoFile(java.util.Map)
	 */
	@Override
	public boolean processorReviewFoFile(WithdrawImportFileDTO importFileDto) throws Exception {
		
		BatchFileInfo batchFileInfoParam = new BatchFileInfo();
		//修改批次文件表为以复核
		batchFileInfoParam.setBatchFileStatus(8L);
		batchFileInfoParam.setFileKy(importFileDto.getFileKy());
		batchFileInfoParam.setFoReviewTime(new Date());
		batchFileInfoParam.setOldBatchFileStatus(9);
		
		boolean intFetchRow = queryBatchFileDao.update("fundoutStatus.fo_updateBatchFileInfoByFileky",batchFileInfoParam);
		if(intFetchRow){
			log.error("复核批次文件：批次文件状态9--->8 防并发处理,更新失败batchNum:"+importFileDto.getBatchNum());
			throw new Exception("操作失败,该批次可能已经被他人处理,请重新查询列表,batchNum:"+importFileDto.getBatchNum());
		}
	
		//更新工单表为已复核操作 
		WithdrawWorkorder withdrawWorkorder = new WithdrawWorkorder();
		withdrawWorkorder.setBatchNum(importFileDto.getBatchNum());
		withdrawWorkorder.setFileKy(importFileDto.getFileKy());
		withdrawWorkorder.setStatus(7);
		intFetchRow = queryBatchFileDao.update("wdfileservice.fundout-withdraw-update-workorder47", withdrawWorkorder);
		if(intFetchRow){
			log.error("FOPJ-975 下载批次文件：工单状态 4--->7或者7-->7  防并发处理,更新失败batchNum:"+importFileDto.getBatchNum());
			throw new Exception("未更新到工单,操作失败,该批次可能已经被他人处理,请重新查询列表,batchNum:"+importFileDto.getBatchNum());
		}
		return true;
	}

	@Override
	public List<WithdrawBatchInfo> queryBatchInfo(
			WebQueryWithDrawDTO webQueryWithDrawDTO) {
		
	final QueryBatchWithDraw withdrawBatchInfo = new QueryBatchWithDraw();
		BeanUtils.copyProperties(webQueryWithDrawDTO, withdrawBatchInfo);
		withdrawBatchInfo.setBusiType("1");
	//	List<WithdrawBatchInfoDTO> batchInfoDTO = new ArrayList<WithdrawBatchInfoDTO>();
		List<WithdrawBatchInfo> findBatchInfos = queryBatchFileDao.findBatchInfo(withdrawBatchInfo);
		//BeanUtils.copyProperties(batchInfoDTO,findBatchInfos);
		
		return findBatchInfos;
	}
}
