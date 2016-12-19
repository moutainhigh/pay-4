package com.pay.fundout.withdraw.validate.action;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.service.WorkOrderStatus;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * 更新工单状态为审核滞留
 */
public class WorkOrderStatusSettingAction extends AbstractAction {

	private WithdrawAuditWorkOrderDao withdrawWorkDao;

	@Override
	protected void doExecute(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		WithdrawWorkorder wo = withdrawWorkDao.queryWorkOrderById("WF.queryWorkOrderByOrderId", fundoutOrderDTO.getOrderId());
		wo.setStatus(WorkOrderStatus.AUDIT_HELD_UP.getValue());
		try {
			withdrawWorkDao.update("WF.update",wo);
		} catch (Exception e) {
			throw new PossException("update WithdrawWorkorder Status error...", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

}
