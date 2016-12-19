/**
 *  File: WithdrawExceptionServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawExceptionDAO;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawExceptionInfo;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawExceptionService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class WithdrawExceptionServiceImpl implements WithdrawExceptionService {
	private Log log = LogFactory.getLog(getClass());

	private WithdrawExceptionDAO withdrawExceptionDAO;

	private WithdrawAuditWorkOrderDao withdrawWorkDao;


	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}


	public void setWithdrawExceptionDAO(WithdrawExceptionDAO withdrawExceptionDAO) {
		this.withdrawExceptionDAO = withdrawExceptionDAO;
	}

	@Override
	public Page<WithdrawExceptionInfo> getWithDrawExceptionInfoList(Page<WithdrawExceptionInfo> page, Map params) {
		return withdrawExceptionDAO.getWithDrawExceptionInfoList(page, params);
	}

	@Override
	public void updateWorkOrderOfExceptionOrderRdTx(String orderId) throws PossException {
		try {
			// 启动工作流，得到工作流实例ID
			String workFlowKey = null;
			// 生成工单，并更新工单的工作流实例ID
			if (workFlowKey != null && orderId != null && !orderId.equals("")) {
				WithdrawWorkorder workOrder = new WithdrawWorkorder();

				workOrder.setOrderSeq(Long.valueOf(orderId));
				workOrder.setWorkflowKy(workFlowKey);
				workOrder.setStatus(WithDrawConstants.AUDIT_INIT);

				// 批次状态，插入默认为0
				workOrder.setBatchStatus(0);
				// 更新工单
				this.withdrawWorkDao.update("WF.auditUpdate_3", workOrder);
			} else {
				log.info("工作流失败或订单号为空");
			}
		} catch (Exception e) {
			log.error("工作流启动失败" + e);
			throw new PossException("update workorder error...", ExceptionCodeEnum.SEND_WORKFLOW_LIQUIDATE_EXCEPTION, e);
		}
	}
}
