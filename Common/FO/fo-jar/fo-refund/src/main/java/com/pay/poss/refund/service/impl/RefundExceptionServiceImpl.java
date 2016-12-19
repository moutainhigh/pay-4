/**
 *  File: RefundExceptionServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-7     darv      Changes
 *  
 *
 */
package com.pay.poss.refund.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.dao.RefundExceptionDao;
import com.pay.poss.refund.model.RefundExceptionInfo;
import com.pay.poss.refund.model.RefundWorkorder;
import com.pay.poss.refund.service.RefundExceptionService;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class RefundExceptionServiceImpl implements RefundExceptionService {
	private Log log = LogFactory.getLog(getClass());

	private transient BaseDAO<Object> baseDao;

	private RefundExceptionDao refundExceptionDao;

	public void setBaseDao(BaseDAO<Object> baseDao) {
		this.baseDao = baseDao;
	}

	public void setRefundExceptionDao(RefundExceptionDao refundExceptionDao) {
		this.refundExceptionDao = refundExceptionDao;
	}

	@Override
	public Page<RefundExceptionInfo> getRefundExceptionInfoList(
			Page<RefundExceptionInfo> page, Map params) {
		return refundExceptionDao.getRefundExceptionInfoList(page, params);
	}

	@Override
	public void updateWorkOrderRdTx(String orderKy) throws PossException {
		try {
			// String workFlowKy =
			// bpmService.startProcessInstanceByKey(RefundConstants.REFUND_WORKFLOW_NAME,
			// null);
			if (orderKy != null && !orderKy.equals("")) {
				RefundWorkorder workOrder = new RefundWorkorder();
				// workOrder.setWorkflowKy(workFlowKy);
				workOrder.setRefundMKy(Long.valueOf(orderKy));
				workOrder.setStatus(Integer
						.valueOf(RefundConstants.REFUND_STATUS_0));

				baseDao.update(RefundConstants.REFUND_KEY
						+ "updateWfByWorkOrder", workOrder);
			}
		} catch (Exception e) {
			log.error("关联工作流水出错:" + e);
			throw new PossException("关联工作流水出错",
					ExceptionCodeEnum.ALLOCATE_WORKFLOW_EXCEPTION);
		}
	}

	/*
	 * @Override public void assignOwnerRdTx(String workflowKy) throws
	 * PossException { try { List<String> userList =
	 * userService.findLoginIdByFlowName(RefundConstants.REFUND_WORKFLOW_NAME,
	 * RefundConstants.REFUND_WORKFLOW_NODE_ASSIGN_TASK); if (userList.size() ==
	 * 0) { throw new Exception("用户列表为空"); }
	 * //bpmService.assigneTask(workflowKy, userList.get(0)); } catch (Exception
	 * e) { log.error("分配人员出错:" + e); throw new PossException("分配人员出错",
	 * ExceptionCodeEnum.ALLOCATE_WORKFLOW_EXCEPTION); } }
	 */
}
