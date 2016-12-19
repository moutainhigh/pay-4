 /** @Description 
 * @project 	poss-withdraw
 * @file 		HandBatchFileServiceImpl.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-19		Henry.Zeng			Create 
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
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawAuditQuery;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.schedule.StartTask;
import com.pay.fundout.withdraw.service.fileservice.HandBatchFileService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleChannelResService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchFileRecord;
import com.pay.poss.base.model.BatchInfo;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

/**
 * <p>手工生成批次文件</p>
 * @author Henry.Zeng
 * @since 2010-9-19
 * @see 
 */
public class HandBatchFileServiceImpl implements HandBatchFileService {
	protected Log log = LogFactory.getLog(getClass());
	private QueryBatchFileDao queryBatchFileDao;
	
	private WithdrawAuditDao withdrawAuditDao;
	
	private WithdrawAuditWorkOrderDao withdrawWorkDao;
	
	private BatchRuleChannelResService batRulChResService;
	
	private WithdrawService withdrawService;
	private Long emailTemplateId;
	
	private NotifyFacadeService notifyFacadeService;
	private List<String> mailAddr;
	
	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}
	public void setMailAddr(final List<String> mailAddr) {
		this.mailAddr = mailAddr;
	}
	public void setNotifyFacadeService(final NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}
	public void setEmailTemplateId(Long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}
	//set注入
	public void setBatRulChResService(BatchRuleChannelResService batRulChResService) {
		this.batRulChResService = batRulChResService;
	}
	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

	public void setWithdrawAuditDao(final WithdrawAuditDao withdrawAuditDao) {
		this.withdrawAuditDao = withdrawAuditDao;
	}

	public void setQueryBatchFileDao(final QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}
	
	private Long[] queryBusiTypeByWkId(String[] workorderArray){
		// 获得业务类型
		if(workorderArray!=null){
			List<String> subList = new ArrayList<String>();
			for(String wKy : workorderArray)
				subList.add(wKy);
			
			WithdrawWorkorder wrkorder = new WithdrawWorkorder();
			wrkorder.setSubList(subList);
			List<Long> busiTypeList = queryBatchFileDao.findByQuery("WF.queryWithdrawOrderBusiTypeByWorkKy", wrkorder);
			return busiTypeList.toArray(new Long[busiTypeList.size()]);
		}
		return null;
	}
	
	@Override
	public BatchFileInfo generateBatchInfoRdTx(String workorders , String isSend , String batchName,String withdrawBankCode,String userName) throws PossException {
		try{
			String [] workorderArray = workorders.split(",");
			
			if(workorderArray != null && workorderArray.length > 0 ){
				Long[] busiType =  queryBusiTypeByWkId(workorderArray);
				List<String> workorderList = new ArrayList<String>(workorderArray.length);
				
				WithdrawAuditQuery orderParam = new WithdrawAuditQuery();
				
				for(String workorder : workorderArray ){
					workorderList.add(workorder);
				}
				orderParam.setSubList(workorderList);
				orderParam.setWithdrawBankCode(withdrawBankCode);
				
				//获得订单,并且更新订单的出款银行
				this.queryBatchFileDao.update("wdfileservice.fundout-withdraw-update-withdraw-order", orderParam);
				String ruleKy = null;
				// 生成RULYKY
				Long seqId = (Long) this.queryBatchFileDao
						.findObjectByCriteria("wdfileservice.generateRulyKy",
								null);
				if (seqId != null) {
					// ruleKy的格式：busiType+1+withdrawBankCode+seq
					StringBuffer sb = new StringBuffer();
					for (Long type : busiType)
						sb.append(type.toString());
					ruleKy = sb.append("1").append(withdrawBankCode)
							.append(seqId.toString()).toString();
				}
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("busiType", busiType);
				paraMap.put("withdrawType", "1"); // 暂定手工方式
				paraMap.put("withdrawBankCode", withdrawBankCode);
				paraMap.put("ruleKy", ruleKy);
				paraMap.put("userName", userName);
				// 调用戴哥的接口
				this.batRulChResService.generatorRuleConfigChannel(paraMap);
		
				// 生成批次文件			
				String batchNum = StartTask.getInstance().manualBuildBatch(
						workorderList, ruleKy);

				if ("ignor".equals(batchNum))
					return null;

				// 3把批次名称更新批次表
				BatchInfo batchInfo = new BatchInfo();
				batchInfo.setBatchNum(batchNum);
				// batchInfo.setBatchType("200002");
				batchInfo.setBatchName(batchName);

				queryBatchFileDao.updateWdBatchInfo(batchInfo);

				if ("0".equals(isSend)) {
					// 发送邮件
					mailNotify(batchNum, batchName);
				}

				BatchFileInfo batchFileInfo = new BatchFileInfo();

				batchFileInfo.setFileType(11L);
				batchFileInfo.setBatchNum(batchNum);

				batchFileInfo = queryBatchFileDao
						.queryBatchFileInfo(batchFileInfo);

				if (batchFileInfo == null)
					batchFileInfo = new BatchFileInfo();

				batchFileInfo.setBatchName(batchName);
				return batchFileInfo;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return null;
	}
	
	
	/**
	 * 邮件通知
	 * @param batchNo
	 * @param batchName
	 */
	private void mailNotify(String batchNo,String batchName) {
		try{
			NotifyTargetRequest request = new NotifyTargetRequest();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("batchNo", batchNo);
			map.put("batchName", batchName);
			request.setData(map);
			request.setRequestTime(new Date());
			request.setRetryTimes(3);
			request.setTemplateId(emailTemplateId);//模板ID
			request.setRecAddress(mailAddr);
			request.setSubject("批次文件生成通知");
			request.setNotifyType(RequestType.EMAIL.value());
			request.setFromAddress(Constants.SYSTEM_MAIL);
			notifyFacadeService.notifyRequest2(request);
		}catch (Exception e) {
			log.error("生成批次文件,邮件通知失败",e);
		}
	}

	@Override
	public Page<WithdrawAuditDTO> search(Page<WithdrawAuditDTO> page,
			WithdrawAuditQuery auditQueryDTO ) {
		final WithdrawAuditQuery auditQuery = new WithdrawAuditQuery();
		Page<WithdrawQueryOrder> pageService = new Page<WithdrawQueryOrder>();
		PageUtils.setServicePage(pageService, page);
		BeanUtils.copyProperties(auditQueryDTO, auditQuery);
		
		pageService = withdrawAuditDao.findWdOrderPage("WF.withdrawAuditQuery", pageService, auditQuery);
		
		PageUtils.setServicePage(page,pageService);
		List<WithdrawQueryOrder> resultList = pageService.getResult();
		List<WithdrawAuditDTO> list = null;
		
		if(resultList!=null){
			list = new ArrayList<WithdrawAuditDTO>();
			for(int i=0;i<resultList.size();i++){
				WithdrawAuditDTO dto = new WithdrawAuditDTO();
				WithdrawQueryOrder order = resultList.get(i);
				BeanUtils.copyProperties(order.getOrderDto(), dto);
				BeanUtils.copyProperties(order.getWorkOrderDto(), dto);
				
				list.add(dto);
			}
		}
		if(list!=null)
			page.setResult(list);
		return page;
	}

	/*[
	 * ORDERID=12345678
	 * ISSUCC=1(1:成功,0：失败)
	 * ]
	*/
	@Override
	public boolean importConfirmRdTx(Map<String,String> map) throws PossException {
		String orderId = "";
		String isSucce = "";
		try{
			if(map!=null){
				orderId = map.get("orderId");
				isSucce = map.get("issucc");
				
				WithdrawWorkorder workOrder  = (WithdrawWorkorder) this.withdrawWorkDao.findObjectByCriteria("WF.queryWorkOrderByOrderIdToAcc", Long.valueOf(orderId));
				if(workOrder == null){
					if(log.isWarnEnabled()){
						log.warn("该笔交易做记账时,工单状态不处于该记账的状态,正常记账的工单状态应该是7或者9: "+orderId);
					}
					return false;
				}
				if(isSucce.equals("101")){ //成功记账
					workOrder.setStatus(12);
				}else{
					workOrder.setStatus(10);
				}

				workOrder.setWorkOrderky(Long.valueOf(workOrder.getWorkOrderky()));
				//更新工单以及记账处理
				boolean aRes = this.withdrawService.importConfirmProcessRdTx(workOrder, isSucce);
				if(aRes){	
					//结束工作流
					WithdrawWFParameter wfParam = new WithdrawWFParameter();
					wfParam.setAssigner("");
					wfParam.setNodeName(WithDrawConstants.LIQUIDATE_NODE);
					wfParam.setPreviousUser("");
					wfParam.setProcessInstanceId(workOrder.getWorkflowKy());
					wfParam.setProcessKey(WithDrawConstants.PROCESS_NAME);
					wfParam.setTaskMessage("");
					wfParam.setUserId("adminA");
					this.withdrawService.endWorkFlow(wfParam);
				}else{
					log.error("HandBatchFileServiceImpl.importConfirmRdTx:提现工单状态已经更新不需再进行记账! 提现工单编号=" + orderId);
					return false;
				}
				
					return true;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return false;
	}
	
/*	*//***
	 * 插入数据到BatchFileRecord
	 *//*
	public List<BatchFileRecord> saveBatchFileRecord(BatchFileInfo batchFileInfo) {
		String batchNum = batchFileInfo.getBatchNum();//批次号
		
		WithdrawAuditQuery auditQuery = new WithdrawAuditQuery();
		auditQuery.setStatus("4"); // 复核通过
		auditQuery.setBatchStatus2("1");
		auditQuery.setBatchNo(batchNum);
		List<WithdrawQueryOrder> wq = withdrawAuditDao.findWdOrder(
				"WF.withdrawAuditQueryByBatchNum", auditQuery);
	
		List<BatchFileRecord> batchFileRecords=new ArrayList<BatchFileRecord>();
		for (WithdrawQueryOrder withdrawQueryOrder : wq) {
			BatchFileRecord bf = new BatchFileRecord();
			bf.setgFileKy(batchFileInfo.getFileKy());//主外键
			bf.setTradeSeq(withdrawQueryOrder.getOrderDto().getSequenceId());
			bf.setTradeAmount(withdrawQueryOrder.getOrderDto().getPayerAmount());

			bf.setAccountNo(withdrawQueryOrder.getOrderDto().getBankAcct());
			bf.setAccountName(withdrawQueryOrder.getOrderDto().getAccountName());
			bf.setTradeDate(withdrawQueryOrder.getOrderDto().getCreateTime());
			bf.setRemark(withdrawQueryOrder.getOrderDto().getOrderRemarks());
			bf.setStauts(1);
			bf.setBankBranch(withdrawQueryOrder.getOrderDto()
					.getPayeeBankName());
			batchFileRecords.add(bf);
		//	withdrawWorkDao.saveBatchFileRecord("WF.saveBatchFileRecord", bf);
		}
		return batchFileRecords;
	}*/
}
