package com.pay.txncore.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.dao.DepositOrderDAO;
import com.pay.txncore.dto.DepositOrderDTO;
import com.pay.txncore.model.DepositOrder;
import com.pay.txncore.service.DepositOrderService;
import com.pay.util.BeanConvertUtil;

public class DepositOrderServiceImpl implements DepositOrderService {

	private final Log logger = LogFactory.getLog(DepositOrderServiceImpl.class);

	private DepositOrderDAO depositOrderDAO;

	public void setDepositOrderDAO(DepositOrderDAO depositOrderDAO) {
		this.depositOrderDAO = depositOrderDAO;
	}

	@Override
	public Long saveDepositOrderRnTx(DepositOrderDTO depositOrderDTO) {

		DepositOrder depositOrder = BeanConvertUtil.convert(DepositOrder.class,
				depositOrderDTO);
		return (Long) depositOrderDAO.create(depositOrder);
	}

	@Override
	public boolean updateDepositOrderRnTx(DepositOrderDTO depositOrderDTO) {
		DepositOrder depositOrder = BeanConvertUtil.convert(DepositOrder.class,
				depositOrderDTO);
		return depositOrderDAO.update(depositOrder);
	}

	@Override
	public DepositOrderDTO queryByDepositOrderNo(Long depositOrderNo) {
		DepositOrder depositOrder = (DepositOrder) depositOrderDAO
				.findById(depositOrderNo);

		DepositOrderDTO depositOrderDTO = BeanConvertUtil.convert(
				DepositOrderDTO.class, depositOrder);
		return depositOrderDTO;
	}

	@Override
	public DepositOrderDTO queryByPaymentOrderNo(Long paymentOrderNo) {
		DepositOrder depositOrder = depositOrderDAO
				.findByPaymentOrderNo(paymentOrderNo);

		DepositOrderDTO depositOrderDTO = BeanConvertUtil.convert(
				DepositOrderDTO.class, depositOrder);
		return depositOrderDTO;
	}

}
