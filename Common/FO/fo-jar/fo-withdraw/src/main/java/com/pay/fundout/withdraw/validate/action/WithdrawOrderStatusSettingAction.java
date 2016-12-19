package com.pay.fundout.withdraw.validate.action;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fundout.service.WorkOrderStatus;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * 更新工单状态为直联申请成功
 */
public class WithdrawOrderStatusSettingAction extends AbstractAction {

	private FundoutOrderService fundoutOrderService;
	private WithdrawAuditWorkOrderDao withdrawWorkDao;

	@Override
	protected void doExecute(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		WithdrawWorkorder wo = withdrawWorkDao.queryWorkOrderById("WF.queryWorkOrderByOrderId", fundoutOrderDTO.getOrderId());
		wo.setStatus(WorkOrderStatus.BANKCORPORATEEXPRESS_APPLYSUCCESS.getValue());

		try {
			fundoutOrderDTO.setFundoutMode(0);
			fundoutOrderService.updateOrder(fundoutOrderDTO);

			withdrawWorkDao.update("WF.update", wo);
			fundoutOrderDTO.setWorkOrderStatus(wo.getStatus());
		} catch (Exception e) {
			throw new PossException("update WithdrawWorkorder Status error...", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

}
