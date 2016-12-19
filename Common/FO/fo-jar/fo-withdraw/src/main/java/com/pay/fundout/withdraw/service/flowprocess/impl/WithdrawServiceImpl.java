package com.pay.fundout.withdraw.service.flowprocess.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.service.fundoutprocess.FundoutProcessFactoryService;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.workflowlog.WithdrawWfLogDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.fundout.withdraw.service.workflowlog.WithdrawWfLogService;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

public class WithdrawServiceImpl implements WithdrawService {
	protected transient Log log = LogFactory.getLog(getClass());
	private WithdrawAuditWorkOrderDao withdrawWorkDao;

	private HashMap<String, OrderCallBackService> callBackServiceMap;
	private HashMap<String, AccountingService> accountingServiceMap;
	private HashMap<String, String> businessTypeMap;
	
	FundoutProcessFactoryService orderAfterServiceNew;

	private ProvinceCityFacadeService prvCityService;
	private OrderAfterProcService orderAfterService;
	private WithdrawWfLogService wfLogService;

	private static String WF_TASK_REMARK_COLUMN = "remark";
	private static String WF_TASK_STATE_COLUMN = "state";

	public void setOrderAfterServiceNew(
			FundoutProcessFactoryService orderAfterServiceNew) {
		this.orderAfterServiceNew = orderAfterServiceNew;
	}

	public void setWfLogService(WithdrawWfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public void setPrvCityService(ProvinceCityFacadeService prvCityService) {
		this.prvCityService = prvCityService;
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

	public void setCallBackServiceMap(HashMap<String, OrderCallBackService> callBackServiceMap) {
		this.callBackServiceMap = callBackServiceMap;
	}

	public void setAccountingServiceMap(HashMap<String, AccountingService> accountingServiceMap) {
		this.accountingServiceMap = accountingServiceMap;
	}

	public void setBusinessTypeMap(HashMap<String, String> businessTypeMap) {
		this.businessTypeMap = businessTypeMap;
	}

	public void setOrderAfterService(OrderAfterProcService orderAfterService) {
		this.orderAfterService = orderAfterService;
	}
	
	@Override
	public int firstAudit2DelayRdTx(WithdrawWorkorder workOrder, int auditStatus, String nodeName, Map<String, Object> map, String auditRemark) throws PossException {
		int count = 0;
		try{
			boolean b =updateWorkWorder(workOrder, auditStatus, WithDrawConstants.DELAY);
			if (!b){
				log.error("update workorderorder error...,maybe this order have been updated,workorderId is:" + workOrder.getWorkOrderky());
				return count;
			}
			//TODO  判断是否是滞留
			//boolean aRes = this.bpmService.completeTaskForGroup(map, WithDrawConstants.TO_DELAY);
			logWFLogger(true, map, workOrder);
			return 1;
		} catch (Exception e) {
			log.error("firstAudit2DelayRdTx--update workorder and send workflow error... workOrderId is: " + workOrder.getWorkOrderky(), e);
			throw new PossException("firstAudit2DelayRdTx--update workorder and send workflow is error...", ExceptionCodeEnum.SEND_WORKFLOW_AUDIT_EXCEPTION, e);
		}
	}
	

	// 审核流程处理
	@Override
	public int firstAuditProcessRdTx(WithdrawWorkorder workOrder, int auditStatus, String nodeName, Map<String, Object> map, String auditRemark) throws PossException {
		int count = 0;
		try {
			// 更新工单表
			boolean b= updateWorkWorder(workOrder, auditStatus, WithDrawConstants.AUDIT_NODE);
			if (!b){
				log.error("update workorderorder error...,maybe this order have been updated,workorderId is:" + workOrder.getWorkOrderky());
				return count;
			}
			//TODO  判断是否是滞留
//			boolean aRes = false;
//			if (auditStatus == WithDrawConstants.FIRST_AUDIT_WAIT) {
//				aRes = this.bpmService.completeTaskForGroup(map, "to_tempTask");
//			} else {
//				aRes = this.bpmService.completeTaskForGroup(map, "to_reAudit");
//			}
			logWFLogger(true, map, workOrder);
			return 1;
		} catch (Exception e) {
			log.error("firstAuditProcessRdtx--update workorder and send workflow error... workOrderId is: " + workOrder.getWorkOrderky(), e);
			throw new PossException("firstAuditProcessRdtx--update workorder and send workflow is error...", ExceptionCodeEnum.SEND_WORKFLOW_AUDIT_EXCEPTION, e);
		}
	}

	// 复核流程处理
	@Override
	public int secondAuditProcessRdTx(WithdrawWorkorder workOrder, String handleType, Map<String, Object> map, String auditRemark) throws PossException {
		try {
			int count = 0;
			// 更新工单
			boolean b = this.withdrawWorkDao.update("WF.auditUpdate_2", workOrder);
			if (!b){
				log.error("update workorderorder error...,maybe this order have been updated,workorderId is:" + workOrder.getWorkOrderky());
				return count;
			}
			//TODO 复核流程处理
//			boolean aRes = this.bpmService.completeTaskForGroup(map, handleType);
			logWFLogger(true, map, workOrder);
			return 1;
		} catch (Exception e) {
			log.error("secondAuditProcessRdtx--update workorder and send workflow error... workOrderId is: " + workOrder.getWorkOrderky(), e);
			throw new PossException("secondAuditProcessRdtx--update workorder and send workflow is error...", ExceptionCodeEnum.SEND_WORKFLOW_SECOND_AUDIT_EXCEPTION, e);
		}
	}

	// 清结算流程处理[不包括工作流]
	@Override
	public int liquidateProcessRdTx(WithdrawOrder order, boolean needAccount, WithdrawWorkorder workOrder, Map<String, Object> map, String handleType) throws PossException {
		int count = 0;
		try {
			// 更新工单
			boolean b = this.withdrawWorkDao.update("WF.auditUpdate_2", workOrder);
			if (!b){
				log.error("update workorderorder error...,maybe this order have been updated,workorderId is:" + workOrder.getWorkOrderky());
				return count;
			}
			if (needAccount) {
				if (!accountingNew(false, order))
					throw new PossException("记账失败 [" + order.getSequenceId() + "]", ExceptionCodeEnum.ACCTOUNTING_PROCESS_EXCEPTION);
			}
			return count;
		} catch (Exception e) {
			log.error("liquidateAuditRdTx-- update workorder and send workflow error... workOrderId is: " + workOrder.getWorkOrderky(), e);
			throw new PossException("liquidateAuditRdTx--update workorder and send workflow is error...", ExceptionCodeEnum.SEND_WORKFLOW_SECOND_AUDIT_EXCEPTION, e);

		}
	}

	// 手工处理提现结果复核流程处理
	@Override
	public boolean ResManualProcessReAuditRdTx(WithdrawWorkorder workOrder, WithdrawOrder order, boolean needAccount, boolean isSuccAccounting) throws PossException {
		// 更新工单状态
		boolean b = this.withdrawWorkDao.update("WF.auditUpdate_2", workOrder);
		// hlv修改：如果没有更新到工单，就不用记账
		if (!b) {
			log.error("update workorderorder error...,maybe this order have been updated,workorderId is:" + workOrder.getWorkOrderky());
			throw new PossException("更新工单失败 [" + order.getSequenceId() + "]", ExceptionCodeEnum.ACCTOUNTING_PROCESS_EXCEPTION);
		}
		if (needAccount)
			if (!accountingNew(isSuccAccounting, order)) {
				throw new PossException("记账失败 [" + order.getSequenceId() + "]", ExceptionCodeEnum.ACCTOUNTING_PROCESS_EXCEPTION);
			}
		return b;
	}

	private boolean updateWorkWorder(WithdrawWorkorder workOrder, int auditStatus, String nodeName) throws PossException {
		int status = 0;
		StringBuffer preStatus = new StringBuffer();
		if (nodeName.equals(WithDrawConstants.AUDIT_NODE)) {
			if (auditStatus == WithDrawConstants.FIRST_AUDIT_SUCCESS) {
				status = WithDrawConstants.AUDIT_SUCCESS;
				preStatus.append("a.status in(0,3)");
			} else if (auditStatus == WithDrawConstants.FIRST_AUDIT_REJECT) {
				status = WithDrawConstants.AUDIT_REJECT;
				preStatus.append("a.status in(0,3)");
			} else if (auditStatus == WithDrawConstants.FIRST_AUDIT_WAIT) {
				status = WithDrawConstants.AUDIT_WAIT;
				preStatus.append("a.status in(0)");
			}
		}else if(nodeName.equals(WithDrawConstants.DELAY)){
			if (auditStatus == WithDrawConstants.FIRST_AUDIT_SUCCESS) {
				status = WithDrawConstants.AUDIT_SUCCESS;
				preStatus.append("a.status in(0,3)");
			} else if (auditStatus == WithDrawConstants.FIRST_AUDIT_REJECT) {
				status = WithDrawConstants.AUDIT_REJECT;
				preStatus.append("a.status in(0,3)");
			} else if (auditStatus == WithDrawConstants.FIRST_AUDIT_WAIT) {
				status = WithDrawConstants.AUDIT_WAIT;
				preStatus.append("a.status in(0)");
			}
		}
		workOrder.setStatus(status);
		workOrder.setPreStatus(preStatus.toString());
		return this.withdrawWorkDao.update("WF.auditUpdate_2", workOrder);
	}

	@Override
	public CityDTO getCity(Integer cityId) {
		CityDTO cityDto = null;
		try {
			if (cityId != null)
				cityDto = prvCityService.getCity(cityId);
		} catch (Exception e) {
			log.error("find city error cityid:" + cityId);
		}
		return cityDto;
	}

	@Override
	public ProvinceDTO getProvince(Integer provinceId) {
		ProvinceDTO provinceDto = null;
		try {
			if (provinceId != null)
				provinceDto = prvCityService.getProvince(provinceId);
		} catch (Exception e) {
			log.error("find province error provinceid:" + provinceId);
		}
		return provinceDto;
	}

	/**
	 * 结束工作流
	 * 
	 * @param workFlowId
	 * @param wfParam
	 * @param handleType
	 * @author Jonathen Ni
	 * @return true or false
	 */
	@Override
	public boolean endWorkFlow(WithdrawWFParameter wfParam) {
		Map<String, Object> wfMap = new HashMap<String, Object>();
		if (wfParam != null) {
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_ASSIGNER, wfParam.getAssigner());
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_NODENAME, wfParam.getNodeName());
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_PREVIOUSUSER, wfParam.getPreviousUser());
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSINSTANCEID, wfParam.getProcessInstanceId());
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSKEY, wfParam.getProcessKey());
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_TASKMESSAGE, wfParam.getTaskMessage());
			wfMap.put(WithdrawWFParameter.WF_PARAMETER_KEY_USERID, wfParam.getUserId());

			//this.bpmService.completeTaskForGroup(wfMap, WithDrawConstants.LIQUIDATE_AUDIT_REJECT_HANDLE_TYPE);
			logWFLogger(wfMap);
			return true;
		}
		return false;
	} 

	@Override
	public boolean importConfirmProcessRdTx(WithdrawWorkorder workOrder, String isSucce) throws PossException {
		try {
			//TODO 更新状态时，需明确指定上一状态 
			boolean b = this.withdrawWorkDao.update("WF.auditUpdate", workOrder);
			// 如果更新成功，则进行记账
			if (b) {
				// 调用记账服务
				boolean isSuccAccounting = false;
				if (isSucce.equals("101"))
					isSuccAccounting = true;

				WithdrawOrder order = new WithdrawOrder();
				List<WithdrawOrder> orderList = this.withdrawWorkDao.findByQuery("WF.queryWithdrawOrderById", Long.valueOf(workOrder.getOrderSeq()));
				if (orderList != null && orderList.size() == 1) {
					order = orderList.get(0);
					order.setAmount(order.getAmount());
				}
				//TODO 记帐和更新订单状态异常未作处理
				accountingNew(isSuccAccounting, order);
				return true;
			} else {
				log.error("HandBatchFileServiceImpl.importConfirmRdTx:提现工单状态已经更新不需再进行记账! 提现工单编号=" + workOrder.getWorkOrderky());
			}
		} catch (Exception e) {
			log.error("importConfirmProcessRdtx-- update workorder and send workflow error... workOrderId is: " + workOrder.getWorkOrderky(), e);
			throw new PossException("importConfirmProcessRdtx--update workorder error...", ExceptionCodeEnum.SEND_WORKFLOW_SECOND_AUDIT_EXCEPTION, e);
		}
		return false;
	}
	
	@Override
	public boolean bankBackRdTx(Map<String,String> map) throws PossException {
		String orderId = "";
		String isSucce = "";
		String failReason = "";
		boolean needAccounting = false;
		try {
			if(map!=null){
				orderId = map.get("orderId");
				isSucce = map.get("isSuccess");
				failReason = map.get("failReason");
				
				WithdrawWorkorder workOrder  = (WithdrawWorkorder) this.withdrawWorkDao.findObjectByCriteria("WF.queryWorkOrderByOrderIdToBankBack", Long.valueOf(orderId));
				if(workOrder == null){
					if(log.isWarnEnabled()){
						log.warn("该笔交易做直连后处理时,工单状态不处于直连后处理的状态,正常直连后处理的工单状态应该是13: "+orderId);
					}
					return false;
				}
				if(isSucce.equals("1")){ //银行返回成功：成功记账
					workOrder.setStatus(12);
					needAccounting = true;
				}else if(isSucce.equals("2")){//银行返回失败，更新工单状态为直连失败
					workOrder.setStatus(14);
					workOrder.setAuditFailReason(failReason);
				}

				//更新工单以及记账处理
				//TODO 更新状态时，需明确指定上一状态 
				boolean b = this.withdrawWorkDao.update("WF.auditUpdate", workOrder);
				
				// 如果更新成功，则进行记账
				if (b) {
					if(needAccounting){
						// 调用记账服务
						boolean isSuccAccounting = true;
						
						WithdrawOrder order = new WithdrawOrder();
						List<WithdrawOrder> orderList = this.withdrawWorkDao.findByQuery("WF.queryWithdrawOrderById", Long.valueOf(workOrder.getOrderSeq()));
						if (orderList != null && orderList.size() == 1) {
							order = orderList.get(0);
						}
						boolean accountingOk = accountingNew(isSuccAccounting, order);
						if(!accountingOk){
							throw new PossException("记账失败，订单号="+order.getOrderSeqId(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
						}
						return true;
					} 
				}else {
					log.error("WithdrawServiceImpl.bankBackRdTx:提现工单状态已经更新不需再进行记账! 提现工单编号=" + workOrder.getWorkOrderky());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new PossException(e.getMessage(), ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return false;
	}

	// 记账
	private boolean accountingNew(boolean isSuccAccounting, WithdrawOrder order) {
		boolean aRes = true;
		if (order != null) {
			long orderId = order.getSequenceId();
			// 记账接口调用
			try{
				if (isSuccAccounting) {
					// 记账--中间账户到中间账户
					this.orderAfterServiceNew.foProcessSuccess(orderId);
				} else {
					// 记账--退回到会员账户
					this.orderAfterServiceNew.foProcessFail(orderId,
							order.getErrorMessage(), order.getPayerAcctType(),
							order.getPayeeAcctType(),
							order.getPayerCurrencyCode(),
							order.getPayeeCurrencyCode());
				}
			}catch(Throwable e){
				log.error("订单后处理失败 [" + orderId + "]", e);
				aRes = false;
			}
		}
		return aRes;
	}
	

	@Override
	public WithdrawWfLogDTO buildWFLogDTO(Map<String, Object> wfMap) {
		return generateWFLogDTO(wfMap);
	}

	private WithdrawWfLogDTO generateWFLogDTO(Map<String, Object> wfMap) {
		WithdrawWfLogDTO dto = null;
		if (wfMap != null) {
			dto = new WithdrawWfLogDTO();
			dto.setCreatedate(new Date());
			dto.setNode((String) wfMap.get(WithdrawWFParameter.WF_PARAMETER_KEY_NODENAME));
			dto.setOperator((String) wfMap.get(WithdrawWFParameter.WF_PARAMETER_KEY_USERID));
			dto.setRemark(this.parseWFTaskMessage((String) wfMap.get(WithdrawWFParameter.WF_PARAMETER_KEY_TASKMESSAGE), this.WF_TASK_REMARK_COLUMN));
			dto.setState(this.parseWFTaskMessage((String) wfMap.get(WithdrawWFParameter.WF_PARAMETER_KEY_TASKMESSAGE), this.WF_TASK_STATE_COLUMN));
			dto.setWorkflowId((String) wfMap.get(WithdrawWFParameter.WF_PARAMETER_KEY_PROCESSINSTANCEID));
			dto.setWorkOrderKy((Long)wfMap.get(WithdrawWFParameter.WF_PARAMETER_KEY_WORKORDERKY));
		}
		return dto;
	}

	@Override
	public List<WorkFlowHistory> transforHisToDisp(List<WorkFlowHistory> hisList) {
		List<WorkFlowHistory> list = new ArrayList<WorkFlowHistory>();
		if(hisList!=null){
			for (WorkFlowHistory his : hisList) {
				if (his.getNodeName() == null) {
					continue;
				}
					if (his.getNodeName().equals(WithDrawConstants.AUDIT_NODE)) {
						his.setNodeName("审核");
						if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.FIRST_AUDIT_HANDLE_AGREE)
							his.setHandleStatus("审核通过");
						else if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.FIRST_AUDIT_HANDLE_REJECT)
							his.setHandleStatus("审核拒绝");
						else if(Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SKIP_WORKFLOW)
							his.setHandleStatus("流程跳转修复");
						else
							his.setHandleStatus("审核滞留");
					} else if (his.getNodeName().equals(WithDrawConstants.SECOND_AUDIT_NODE)) {
						his.setNodeName("复核");
						if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SECOND_AUDIT_AGREE)
							his.setHandleStatus("复核同意");
						else if(Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SKIP_WORKFLOW)
							his.setHandleStatus("流程跳转修复");
						else
							his.setHandleStatus("复核退回");
					} else if (his.getNodeName().equals(WithDrawConstants.LIQUIDATE_NODE)) {
						his.setNodeName("结算");
						if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.LIQUIDATE_BACK)
							his.setHandleStatus("结算退回");
						else if(Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SKIP_WORKFLOW)
							his.setHandleStatus("流程跳转修复");
						else
							his.setHandleStatus("结算拒绝");
					} else if (his.getNodeName().equals(WithDrawConstants.MANUAL_FIRST_AUDIT_NODE)) {
						his.setNodeName("人工审核");
						if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.MANUAL_FITST_AUDIT_SUCC)
							his.setHandleStatus("人工审核成功");
						else if(Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SKIP_WORKFLOW)
							his.setHandleStatus("流程跳转修复");
						else
							his.setHandleStatus("人工审核失败");
					}else if (his.getNodeName().equals(WithDrawConstants.MANUAL_SECOND_AUDIT_NODE)) {
						his.setNodeName("人工复核");
						if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.MANUAL_SECOND_AUDIT_AGREE){
							his.setHandleStatus("人工复核同意");
						}else if(Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SKIP_WORKFLOW)
							his.setHandleStatus("流程跳转修复");
						else
						{
							his.setHandleStatus("人工复核退回");
						}
						
					}else if(WithDrawConstants.DELAY.equals(his.getNodeName())){
						his.setNodeName("滞留");
						if (Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SECOND_AUDIT_AGREE)
							his.setHandleStatus("审核同意");
						else if(Integer.parseInt(his.getHandleStatus()) == WithDrawConstants.SKIP_WORKFLOW)
							his.setHandleStatus("流程跳转修复");
						else
							his.setHandleStatus("审核拒绝");
					}else{
						his.setHandleStatus("");
						his.setNodeName("");
					}
				
				list.add(his);
			}
		}
		return list;
	}

	/**
	 * 解析工作流参数MAP中的TASKMESSAGE项 提交工作流的格式暂时是自定的,以nodename##0(通过)或者nodename##1(拒绝)
	 * audit 提交工作流的格式暂时是自定的,以nodename##1(退回)或者nodename##0(拒绝) liquidate
	 * 提交工作流的格式暂时是自定的,以nodename##0(同意)或者nodename##1(退回) secondAudit
	 */
	private String parseWFTaskMessage(String taskMsg, String columnName) {
		if (taskMsg != null && !taskMsg.equals("")) {
			String[] strs = taskMsg.split("##");
			if (strs != null) {
				if (columnName.equals(this.WF_TASK_REMARK_COLUMN)) {
					if (strs.length == 3)
						return strs[2];
					else
						return "";
				} else if (columnName.equals(this.WF_TASK_STATE_COLUMN)) {
					return strs[1];
				}

			} else
				return "";
			return "";
		} else
			return "";
	}

	public List<WorkFlowHistory> queryWorkFlowHistoryInfo(String workFlowId) {
		// 获得工作流历史数据
		List<WithdrawWfLogDTO> list = this.wfLogService.queryWithdrawWfLogList(workFlowId);
		List<WorkFlowHistory> wfHisList = parseWFHistory(list);
		return wfHisList;
	}
	
	public List<WorkFlowHistory> queryWorkFlowHistoryInfoByWorkKy(String workKy) {
		// 获得工作流历史数据
		List<WithdrawWfLogDTO> list = this.wfLogService.queryWithDrawWfLogByWorkOrderKy(Long.parseLong(workKy));
		List<WorkFlowHistory> wfHisList = parseWFHistory(list);
		return wfHisList;
	}
	
	public List<WorkFlowHistory> queryWorkFlowHistoryInfoByOrderKy(String orderKy) {
		// 获得工作流历史数据
		List<WithdrawWfLogDTO> list = this.wfLogService.queryWithDrawWfLogByOrderKy(Long.valueOf(orderKy));
		List<WorkFlowHistory> wfHisList = parseWFHistory(list);
		return wfHisList;
	}
	

	private List<WorkFlowHistory> parseWFHistory(List<WithdrawWfLogDTO> wfHisList) {
		List<WorkFlowHistory> list = new ArrayList<WorkFlowHistory>();
		if (wfHisList != null) {
			WithdrawWfLogDTO dto = null;
			for (int i = 0; i < wfHisList.size(); i++) {
				dto = wfHisList.get(i);
				if (dto != null) {
					WorkFlowHistory his = new WorkFlowHistory();
					his.setAssignee(dto.getOperator());
					his.setCreateTime(dto.getCreatedate());
					his.setHandleStatus(dto.getState());
					his.setTaskMessage(dto.getRemark());
					his.setNodeName(dto.getNode());
					list.add(his);
				}
			}
		}
		return list;
	}

	private void logWFLogger(boolean wfSucc, Map<String, Object> map, WithdrawWorkorder workOrder) {
		if (!wfSucc)
			log.error("send workflow error..." + "workOrderId: " + workOrder.getWorkOrderky());
		else{
			map.put("workOrderKy",workOrder.getWorkOrderky());
			logWFLogger(map);
		}
	}

	private void logWFLogger(Map<String, Object> map) {
		this.wfLogService.saveWithdrawWfLog(this.generateWFLogDTO(map));
	}

	@Override
	public void withdrawLogWFLogger(Map<String, Object> map) {
		logWFLogger(map);
	}

}
