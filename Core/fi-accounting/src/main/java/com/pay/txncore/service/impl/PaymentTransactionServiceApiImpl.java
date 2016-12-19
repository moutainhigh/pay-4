package com.pay.txncore.service.impl;

import java.util.List;

import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.dao.PaymentOrderDAO;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.model.PaymentOrder;
import com.pay.txncore.service.PaymentTransactionServiceApi;

public class PaymentTransactionServiceApiImpl implements
		PaymentTransactionServiceApi {

	private PaymentOrderDAO paymentOrderDAO ;
	@Override
	public PaymentOrderDTO queryPaymentByTradeOrderNo(Long tradeOrderNo,
			PaymentOrderStatusEnum paymentStatus) {
		String status ;
		if(tradeOrderNo == null)
			return null;
		if(paymentStatus == null){
			status = "";
		}
		else{
			 status = String.valueOf( paymentStatus.getCode());
		}
		PaymentOrder paymentOrder = null;
		paymentOrder = paymentOrderDAO.queryByTradeOrderAndStatus(tradeOrderNo,status);
		if(null == paymentOrder || paymentOrder.getPaymentOrderNo()<= 0)
			return null;
		return convertModel2PaymentOrderDTO(paymentOrder);
	}

	private PaymentOrderDTO convertModel2PaymentOrderDTO(PaymentOrder payment){
		PaymentOrderDTO dto = new PaymentOrderDTO();
		dto.setPaymentAmount(payment.getPaymentAmount());
		dto.setPayeeFee(payment.getPayeeFee() == null ? 0L:payment.getPayeeFee());
		dto.setPayerFee(payment.getPayerFee() == null ? 0L:payment.getPayerFee());
		dto.setPayee(payment.getPayee());
		dto.setPayer(payment.getPayer());
		dto.setPayerType(payment.getPayerType());
		dto.setPayeeType(payment.getPayeeType());
		dto.setPayType(payment.getPayType());
		dto.setOrgCode(payment.getOrgCode());
		dto.setTradeOrderNo(payment.getTradeOrderNo());
		dto.setPaymentOrderNo(payment.getPaymentOrderNo());
		return dto;
	}
	
	@Override
	public void revisePaymentStatus(List<PaymentOrderDTO> paymentOrders) {
		

	}

	public void setPaymentOrderDAO(PaymentOrderDAO paymentOrderDAO) {
		this.paymentOrderDAO = paymentOrderDAO;
	}

	
}
