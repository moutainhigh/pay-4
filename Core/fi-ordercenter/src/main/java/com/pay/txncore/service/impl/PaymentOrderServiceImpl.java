/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.PaymentOrderDAO;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.PreauthOrderDTO;
import com.pay.txncore.model.PaymentOrder;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.util.BeanConvertUtil;

/**
 * 账户支付对外服务实现
 * 
 * @author huhb
 * 
 */
public class PaymentOrderServiceImpl implements PaymentOrderService {

	private PaymentOrderDAO paymentOrderDAO;

	public void setPaymentOrderDAO(PaymentOrderDAO paymentOrderDAO) {
		this.paymentOrderDAO = paymentOrderDAO;
	}

	@Override
	public Long savePaymentOrderRnTx(PaymentOrderDTO paymentOrderDTO) {

		PaymentOrder paymentOrder = BeanConvertUtil.convert(PaymentOrder.class,
				paymentOrderDTO);
		return (Long) paymentOrderDAO.create(paymentOrder);
	}

	@Override
	public boolean updatePaymentOrderRnTx(PaymentOrderDTO paymentOrderDTO) {
		PaymentOrder paymentOrder = BeanConvertUtil.convert(PaymentOrder.class,
				paymentOrderDTO);

		return paymentOrderDAO.update(paymentOrder);
	}

	@Override
	public boolean updatePaymentOrderRnTx(PaymentOrderDTO paymentOrderDTO,
			Integer oldStatus) {

		PaymentOrder paymentOrder = BeanConvertUtil.convert(PaymentOrder.class,
				paymentOrderDTO);
		paymentOrder.setOldStatus(oldStatus);
		return paymentOrderDAO.update(paymentOrder);
	}

	@Override
	public List<PaymentOrderDTO> queryPaymentOrderByTradeOrderNo(
			Long tradeOrderNo) {
		List<PaymentOrder> paymentOrders = paymentOrderDAO
				.queryByTradeOrderNo(tradeOrderNo);
		List<PaymentOrderDTO> paymentOrderDTOs = (List<PaymentOrderDTO>) BeanConvertUtil
				.convert(PaymentOrderDTO.class, paymentOrders);
		return paymentOrderDTOs;
	}

	@Override
	public PaymentOrderDTO queryByPaymentOrderNo(Long paymentOrderNo) {
		PaymentOrder paymentOrder = paymentOrderDAO.findById(paymentOrderNo);
		PaymentOrderDTO paymentOrderDTO = BeanConvertUtil.convert(
				PaymentOrderDTO.class, paymentOrder);
		return paymentOrderDTO;
	}

	@Override
	public List<PaymentOrderDTO> queryPaymentOrder(
			PaymentOrderDTO paymentOrderDTO, Page page) {
		PaymentOrder paymentOrder = BeanConvertUtil.convert(PaymentOrder.class,
				paymentOrderDTO);

		return (List<PaymentOrderDTO>) BeanConvertUtil.convert(
				PaymentOrderDTO.class, paymentOrderDAO.findByCriteria(
						"findByCriteria", paymentOrder, page));
	}

	@Override
	public boolean updateSettlementFlg(Long paymentOrderNo,
			Integer settlementFlg) {
		Map paraMap = new HashMap();
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("settlementFlg", settlementFlg);
		return paymentOrderDAO.updateByMap("updateSettlementFlg", paraMap);
	}

	@Override
	public List<PreauthOrderDTO> queryPreauthOrderByTradeOrderNo(
			Long tradeOrderNo) {
		List<PaymentOrder> paymentOrders = paymentOrderDAO
				.queryByTradeOrderNo(tradeOrderNo);
		List<PreauthOrderDTO> preauthOrderDTOs = (List<PreauthOrderDTO>) BeanConvertUtil
				.convert(PreauthOrderDTO.class, paymentOrders);
		return preauthOrderDTOs;
	}

	@Override
	public List<PaymentOrderDTO> queryByTradeOrderNo(Long tradeOrderNo) {
		// TODO Auto-generated method stub
		List<PaymentOrder> paymentOrders = paymentOrderDAO.findByCriteria(
				"queryByTradeOrderNo", tradeOrderNo);
		return (List<PaymentOrderDTO>) BeanConvertUtil.convert(
				PaymentOrderDTO.class, paymentOrders);
	}

}
