/**
 * 
 */
package com.pay.ma.mdb.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.ma.mdb.ChargeupListener;
import com.pay.pe.service.PaymentResponseDto;

/**
 * @author Administrator
 * 
 */
public abstract class AbstractChargeupListener implements ChargeupListener {

	private Log log = LogFactory.getLog(AbstractChargeupListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		log.info("##################开始调用PE进行记账##################");
		if (message instanceof ObjectMessage) {
			log.info("解析监听消息对象，消息对象为：[ " + message.toString() + "]");
			ObjectMessage objectMessage = (ObjectMessage) message;
			PaymentResponseDto calFeeReponse = null;
			try {
				calFeeReponse = (PaymentResponseDto) objectMessage.getObject();
			} catch (JMSException e) {
				log.error("从MQ里面取出信息[" + objectMessage + "]序列化转换出错", e);
			}
			log.info("获取记账的流水号[order_id]为【" + calFeeReponse.getPaymentReq().getOrderId() + "】交易号[deal_code]为【"
					+ calFeeReponse.getPaymentReq().getDealCode() + "】");
			log.info("进行记账的信息为:【" + calFeeReponse.toString() + "】");
			// 进行记账操作
			try {
				this.doChargeup(calFeeReponse);
			} catch (Exception e) {
				log.info("获取记账的流水号[order_id]为【" + calFeeReponse.getPaymentReq().getOrderId() + "】交易号[deal_code]为【"
						+ calFeeReponse.getPaymentReq().getDealCode() + "】", e);
			}
		}
		log.info("##################结束调用PE记账##################");

	}

	/**
	 * 进行PE记账操作
	 * @param calFeeReponse
	 */
	protected abstract void doChargeup(PaymentResponseDto paymentResponseDto);

}



























