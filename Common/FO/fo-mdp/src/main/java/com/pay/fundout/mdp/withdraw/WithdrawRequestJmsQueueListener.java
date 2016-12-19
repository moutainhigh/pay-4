package com.pay.fundout.mdp.withdraw;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.validate.ValidateService;
import com.pay.fundout.service.WorkOrderStatus;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.util.JSonUtil;

public class WithdrawRequestJmsQueueListener implements MessageListener {
	private WithdrawOrderAuditService wdOrdAuditService;
	private FundoutOrderService fundoutOrderService;
	private ValidateService autoRiskManageService;
			
	public void setWdOrdAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}
	
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	public void setAutoRiskManageService(ValidateService autoRiskManageService) {
		this.autoRiskManageService = autoRiskManageService;
	}

	private Log log = LogFactory.getLog(getClass());
	
	public void onMessage(Message message){
		if (message instanceof ActiveMQObjectMessage) {
			ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
			Notify2QueueRequest request;
			try {
				request = (Notify2QueueRequest) msg.getObject();
				log.info("消息名：" + request.getQueueName());
			} catch (JMSException e) {
				log.error(e);
			}
		}
		try {
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				Notify2QueueRequest request =  (Notify2QueueRequest) msg.getObject();
				String withdrawOrderId = JSonUtil.toObject((String)request.getTargetObject(),String.class);
				if(log.isInfoEnabled()) {
					log.info("------withdraw order receiver-----: " + withdrawOrderId);
				}
				//生成工单启动流程
				//added by Jonathen Ni 2010-09-19 
				if(withdrawOrderId!=null){
					//生成工单时会验证商户是否开通银企直连产品，如开通则更新订单渠道编号
					Integer workOrderStatus = wdOrdAuditService.startWorkFlowRdTx(withdrawOrderId);
					
					if(null != workOrderStatus && workOrderStatus == WorkOrderStatus.BANKCORPORATEEXPRESS_APPLYSUCCESS.getValue()){
						//直联通道限制、自动过风控
						FundoutOrderDTO order = (FundoutOrderDTO) fundoutOrderService.getOrder(Long.valueOf(withdrawOrderId));
						autoRiskManageService.validate(order);
					}
				}
			}
		} catch (Exception ex) {
			log.error(ex);
			throw new RuntimeException(ex);
		}
	}
}
