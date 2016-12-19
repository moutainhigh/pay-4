/**
 * 
 */
package com.pay.fundout.mdp.withdraw;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.util.JSonUtil;

/**
 * @author NEW
 * 
 */
public class BatchPaymentProcessJmsQueueListener implements MessageListener {

	private Log log = LogFactory.getLog(getClass());

	private BatchPaymentOrderService batchPaymentOrderService;

	private BatchPay2BankRequestService batchPay2BankRequestService;

	/**
	 * @param batchPay2BankRequestService
	 *            the batchPay2BankRequestService to set
	 */
	public void setBatchPay2BankRequestService(
			BatchPay2BankRequestService batchPay2BankRequestService) {
		this.batchPay2BankRequestService = batchPay2BankRequestService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				Notify2QueueRequest request = (Notify2QueueRequest) msg
						.getObject();
				String massOrderSeq = JSonUtil.toObject((String) request
						.getTargetObject(), String.class);
				log
						.info("BatchPaymentProcessJmsQueueListener: "
								+ massOrderSeq);
				// 生成明细订单
				if (massOrderSeq != null) {
					String[] arrayMsg = massOrderSeq.split("_");
					if ("toBank".equalsIgnoreCase(arrayMsg[1])) {
						batchPaymentOrderService.createDetailOrder(Long
								.valueOf(arrayMsg[0]), OrderType.BATCHPAY2BANK
								.getValue());
					} else if ("toAcct".equalsIgnoreCase(arrayMsg[1])) {
						// 生成批量付款到账户明细订单
						batchPaymentOrderService.createDetailOrder(Long
								.valueOf(arrayMsg[0]), OrderType.BATCHPAY2ACCT
								.getValue());
					}

				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

}
