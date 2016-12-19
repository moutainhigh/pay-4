package com.pay.fundout.withdraw.service.autocheck.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.service.autocheck.AutoCheckService;
import com.pay.fundout.withdraw.service.autocheck.JudgeService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;

/**
 * 风控自动审核服务
 * @author meng.li
 *
 */
public class AutoCheckServiceImpl implements AutoCheckService {
	
	protected transient Log log = LogFactory.getLog(getClass());

	private JudgeService judgeService;
	
	private WithdrawOrderAuditService wdOrdAuditService;
	
	public void setJudgeService(JudgeService judgeService) {
		this.judgeService = judgeService;
	}
	
	public void setWithdrawOrderAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}
	
	@Override
	public int autocheckRnTx(WithdrawAuditDTO dto) throws Exception {
		int result = 0;
		if (log.isInfoEnabled()) {
			log.info("-----工单号为:" + dto.getWorkOrderky() + "的订单自动审核开始-----");
		}
		if (!(judgeService.methodA(dto)) && judgeService.methodB(dto)) {
			if (log.isInfoEnabled()) {
				log.info("-----工单号为:" + dto.getWorkOrderky() + "的订单状态为非滞留并且账户处于止出或冻结状态，风控将自动审核拒绝并且复核同意-----");
			}
			// 自动审批为风控初审拒绝并且复核同意
			this.processFirstAudit(dto, 2);
		}
		if (!(judgeService.methodA(dto)) && !(judgeService.methodB(dto)) && judgeService.methodC(dto)) {
			if (log.isInfoEnabled()) {
				log.info("-----工单号为:" + dto.getWorkOrderky() + "的订单状态为非滞留并且账户不处于止出或冻结状态并且已到结算周期，风控将自动审核通过并且复核同意-----");
			}
			// 自动审批为风控初审同意并且复核同意
			this.processFirstAudit(dto, 1);
			result = 1;
		}
		this.processSecondAudit(dto);
		if (log.isInfoEnabled()) {
			log.info("-----工单号为:" + dto.getWorkOrderky() + "的订单自动审核结束-----");
		}
		return result;
	}
	
	/**
	 * 初审处理订单数据
	 * @param dto 订单实体
	 * @param flag 标志，1为同意， 2为拒绝
	 */
	private void processFirstAudit(WithdrawAuditDTO dto, int flag) throws Exception {
		String[] wkKeys = {String.valueOf(dto.getWorkOrderky())};
		int auditStatus = flag;
		String auditRemark = "autocheck"; // 自动审核
		String userId = "adminA"; // 系统
		int aggreFlag = 0;
		if (auditStatus == WithDrawConstants.FIRST_AUDIT_REJECT) {
			aggreFlag = 1;
		} else if(auditStatus == WithDrawConstants.FIRST_AUDIT_WAIT) {
			aggreFlag = 2;
		}
		String taskMessage = WithDrawConstants.AUDIT_NODE + "##" + aggreFlag + "##";
		taskMessage += auditRemark;
		WithdrawWFParameter wfPara = new WithdrawWFParameter();
		wfPara.setAssigner("");
		wfPara.setNodeName(WithDrawConstants.AUDIT_NODE);
		wfPara.setProcessKey(WithDrawConstants.PROCESS_NAME);
		wfPara.setTaskMessage(taskMessage);
		wfPara.setUserId(userId);
		this.wdOrdAuditService.firstAudit(wfPara, wkKeys, auditStatus, auditRemark);
	}
	
	/**
	 * 复核同意处理订单数据
	 * @param dto 订单实体
	 */
	private void processSecondAudit(WithdrawAuditDTO dto) throws Exception {
		String[] wkKeys = {String.valueOf(dto.getWorkOrderky())};
		int auditStatus = 0;
		String auditRemark = "autocheck"; // 自动审核
		String userId = "adminA"; // 系统
		// 提交工作流的格式暂时是自定的,以nodename##0(同意)或者nodename##1(退回)
		String taskMessage = WithDrawConstants.SECOND_AUDIT_NODE + "##" + auditStatus + "##";
		taskMessage += auditRemark;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("nodeName", WithDrawConstants.SECOND_AUDIT_NODE);
		map.put("processKey", WithDrawConstants.PROCESS_NAME);
		map.put("previousUser", "");
		map.put("taskMessage", taskMessage);

		this.wdOrdAuditService.secondAudit(map, wkKeys, auditStatus, auditRemark);
	}
	
}
