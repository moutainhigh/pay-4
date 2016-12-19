package com.pay.fundout.withdraw.service.bankrefund.ordercallback;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditOrderDao;
import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

public class BankRefundOrderCallBack extends AbstractOrderCallBackServiceImpl {

	private BankRefundOrderService bankRefundOrderService;
	private WithdrawAuditOrderDao withdrawOrderDao;
	private NotifyFacadeService notifyFacadeService;
	private String queueName;

	public void setBankRefundOrderService(BankRefundOrderService bankRefundOrderService) {
		this.bankRefundOrderService = bankRefundOrderService;
	}

	public void setWithdrawOrderDao(WithdrawAuditOrderDao withdrawOrderDao) {
		this.withdrawOrderDao = withdrawOrderDao;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		BaseOrderDTO order = param.getBaseOrderDto();
		BankRefundOrderDTO dto = null;
		if (order instanceof BankRefundOrderDTO) {
			dto = (BankRefundOrderDTO) order;
			if (WithdrawOrderStatus.INIT.getValue() == dto.getStatus().intValue()) {
				return true;
			}
			dto.setUpdateDate(new Date(System.currentTimeMillis()));
			return bankRefundOrderService.updateOrder(dto);
		}
		return false;
	}

	@Override
	protected void doCancelAccounting(BackFundmentOrder backFundOrder) throws PossException {
	}

	@Override
	public void notify(HandlerParam param) {
		// 如果退款成功，且原交易是外部系统发起的，需要通知外部系统
		String busiType = param.getWithdrawBusinessType();
		BaseOrderDTO order = param.getBaseOrderDto();
		if ((order instanceof BankRefundOrderDTO) == false) {
			return;
		}
		BankRefundOrderDTO bankRefundOrder = (BankRefundOrderDTO) order;
		// 申请和失败2个阶段不发消息
		if (WithdrawBusinessType.BANKREFUND_ORDER_FAIL.getBusinessType().equals(busiType) == false && WithdrawBusinessType.BANKREFUND_ORDER_REQ.getBusinessType().equals(busiType) == false) {
			// 查找原交易记录
			WithdrawOrder orderModel = null;
			try {
				orderModel = withdrawOrderDao.queryOrderById("WF.queryWithdrawOrderById", bankRefundOrder.getTradeOrderId());
			} catch (PlatformDaoException e) {
				log.error("查询原出款交易信息错误 [bankSeq=" + bankRefundOrder.getSequenceId() + ",orderSrc=" + bankRefundOrder.getTradeOrderId() + "]", e);
				return;
			}

			if (StringUtils.isNotEmpty(orderModel.getOrderSeqId())) {
				HashMap<String, String> paramMap = new HashMap<String, String>(3);
				paramMap.put("backSeqId", bankRefundOrder.getSequenceId().toString());// 退款订单号
				paramMap.put("innerOrderId", bankRefundOrder.getTradeOrderId().toString());// 内部原订单号
				paramMap.put("outerOrderId", orderModel.getOrderSeqId());// 外部原订单号
				paramMap.put("status", bankRefundOrder.getStatus().toString());// 退款订单状态
				Notify2QueueRequest request = new Notify2QueueRequest();
				request.setQueueName(queueName);
				request.setTargetObject(paramMap);
				request.setIsOuter(1);
				notifyFacadeService.sendRequest(request);
				log.debug("发送外系统通知 [" + paramMap + "]");
			}
		}
	}
}
