 /** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterCommonService.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-10		Sunsea.Li			Create 
*/
package com.pay.poss.service.ordercenter.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.service.PEService;
import com.pay.poss.service.ordercenter.OrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.entrie.OrderCenterEntrieDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.workorderhistory.OrderHistoryDTO;

/**
 * <p>订单查询抽象公共实现</p>
 * @author Sunsea.Li
 * @since 2010-11-10
 * @see 
 */
public abstract class AbstractOrderCenterCommonService implements OrderCenterCommonService{
	protected transient Log log = LogFactory.getLog(getClass());

	private PEService peService;
	protected MemberQueryService  memberQueryService;
	protected AccountQueryService accountQueryService;
	protected WithdrawOrderAuditService withdrawOrderAuditService;
	
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setWithdrawOrderAuditService(WithdrawOrderAuditService withdrawOrderAuditService) {
		this.withdrawOrderAuditService = withdrawOrderAuditService;
	}

	public PEService getPeService() {
		return peService;
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}


	@Override
	public Map<String, Object> queryOrderHistory(OrderCenterQueryDTO queryDto) {
		List<OrderHistoryDTO> historyList = new ArrayList<OrderHistoryDTO>();
		Map<String,Object> result = new HashMap<String,Object>();
		OrderHistoryDTO history = null;
		String orderType = queryDto.getOrderType();
		//查询风控操作记录
		if("1004".equals(orderType) || "1011".equals(orderType) || "1012".equals(orderType) || "1013".equals(orderType) || "1008".equals(orderType)){
			List<WorkFlowHistory> workFlowHistoryList =  withdrawOrderAuditService.queryWorkFlowHisInfoByorderkKy(queryDto.getOrderKy());
			for (WorkFlowHistory workFlowHistory : workFlowHistoryList) {
				history = new OrderHistoryDTO();
				history.setAssignee(workFlowHistory.getAssignee());
				history.setCreateTime(workFlowHistory.getCreateTime());
				history.setHandleStatus((String)workFlowHistory.getHandleStatus());
				history.setNodeName((String)workFlowHistory.getNodeName());
				history.setTaskMessage((String)workFlowHistory.getTaskMessage());
				historyList.add(history);
			}
			
		}else{
			//TODO 工作流操作历史
			List<Map<String,Object>> logList = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:logList){
				history = new OrderHistoryDTO();
				history.setAssignee((String)map.get("assignee"));
				//充退
				if("1005".equals(orderType)){
					history.setCreateTime((Date)map.get("endTime")); 
				}else{
					history.setCreateTime((Date)map.get("createTime"));
				}
				history.setHandleStatus((String)map.get("handleStatus"));
				history.setNodeName((String)map.get("nodeName"));
				history.setTaskMessage((String)map.get("taskMessage"));
				historyList.add(history);
			}
		}
		result.put("historyList", historyList);
		return result;
	}

	public Map<String, Object> queryOrderEntrie(OrderCenterQueryDTO queryDto) {
		List<OrderCenterEntrieDTO> entrieList = new ArrayList<OrderCenterEntrieDTO>();
		//此处需要调用魏峰接口取得分录列表
		List<AccountEntryDTO> peEntryResultList = new ArrayList<AccountEntryDTO>();
		String peKy=queryDto.getOrderKy();
		/*if("1005".equals(queryDto.getOrderType())){//充退
			if("101".equals(queryDto.getOrderStatus())){//状态为进行中
				peKy = queryDto.getOrderMky();
			}
		}*/
		peEntryResultList = peService.getAccountEntryByOrderId(peKy);
		for(AccountEntryDTO peDto:peEntryResultList){
			OrderCenterEntrieDTO entrie = new OrderCenterEntrieDTO();
			entrie.setAccoutCode(peDto.getAcctcode());
			entrie.setAmount(new BigDecimal(peDto.getValue()));
			entrie.setCertificateNo(peDto.getVouchercode().toString());
			entrie.setCreateDate(peDto.getTransactiondate());
			entrie.setDrMark(peDto.getCrdr().toString());
			entrie.setStatus(peDto.getStatus().toString());
			entrie.setEntrieNo(peDto.getEntrycode().toString());
			entrieList.add(entrie);
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("entrieList", entrieList);
		return result;
	}
	
}
