package com.pay.pe.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameterValue;

import com.pay.inf.dto.DtoUtil;
import com.pay.inf.dto.MutableDto;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.pe.dao.order.PaymentOrderDAO;
import com.pay.pe.dto.PaymentOrderDto;
import com.pay.pe.model.PaymentOrder;
import com.pay.pe.service.order.PaymentOrderService;
import com.pay.util.StringUtil;

public class PaymentOrderServiceImpl extends BaseServiceImpl implements
		PaymentOrderService {

	private Log logger = LogFactory.getLog(getClass());
	private DtoUtil mainDtoUtil;

	public void setMainDtoUtil(DtoUtil mainDtoUtil) {
		this.mainDtoUtil = mainDtoUtil;
	}

	/**
	 * Default constructor.
	 */
	public PaymentOrderServiceImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentOrderDto> findAllPaymentOrder(final String payeeIdentity) {
		if (StringUtil.isEmpty(payeeIdentity)) {
			return null;
		}
		List models = ((PaymentOrderDAO) getMainDao())
				.findAllPaymentOrder(payeeIdentity);
		List<PaymentOrderDto> result = mainDtoUtil.convert2Dtos(models);
		return result;
	}

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentOrderDto> findAllRelatedOrder(final String orderId,
			final Integer relatedType) {

		if (StringUtil.isEmpty(orderId)) {
			return null;
		}
		List models = ((PaymentOrderDAO) getMainDao()).findAllRelatedOrder(
				orderId, relatedType);
		List<PaymentOrderDto> result = mainDtoUtil.convert2Dtos(models);
		return result;
	}

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentOrderDto> findAllPaymentOrder(final Integer orderStatus,
			final Integer orderType, final String orderNo) {
		List models = ((PaymentOrderDAO) getMainDao()).findAllPaymentOrder(
				orderStatus, orderType, orderNo);
		List<PaymentOrderDto> result = mainDtoUtil.convert2Dtos(models);
		return result;
	}

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentOrderDto> findAllPaymentOrder(final Integer orderStatus,
			final Integer orderType) {
		return findAllPaymentOrder(orderStatus, orderType, null);
	}

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentOrderDto> findAllPaymentOrder(
			final PaymentOrderDto orderCriteria) {
		List models = ((PaymentOrderDAO) getMainDao())
				.findAllPaymentOrder((PaymentOrder) mainDtoUtil
						.convert2Model(orderCriteria));
		List<PaymentOrderDto> result = mainDtoUtil.convert2Dtos(models);
		return result;
	}

	/*
	 * (non-Javadoc)
	 */
	public List<PaymentOrderDto> getAllPaymentOrders(final List<String> orderIds) {
		List<PaymentOrder> models = ((PaymentOrderDAO) getMainDao())
				.getAllPaymentOrders(orderIds);
		List<PaymentOrderDto> result = mainDtoUtil.convert2Dtos(models);
		return result;
	}

	/*
	 * (non-Javadoc)
	 */
	public int getOrderStatus(final String orderId) {
		PaymentOrder order = (PaymentOrder) getMainDao().findById(orderId);
		if (null == order) {
			return -1;
		}
		return order.getOrderStatus();
	}

	/*
	 * (non-Javadoc)
	 */
	public void changeOrderStatus(final String orderId, final int oldStatus,
			final int newStatus) {

		PaymentOrderDto order = (PaymentOrderDto) this.findById(orderId);
		order.setOrderStatus(newStatus);
		order.setLastUpdateTime(new Date());
		this.update(order);
	}

	/*
	 * (non-Javadoc)
	 */
	public void updateOrderDigest(final String orderId, final String digest) {
		((PaymentOrderDAO) getMainDao()).updateOrderDigest(orderId, digest);
	}

	public PaymentOrderDto findByReferenceNum(int orderCode, String referenceNum) {
		PaymentOrder order = ((PaymentOrderDAO) getMainDao())
				.findByReferenceNum(orderCode, referenceNum);
		if (order == null) {
			return null;
		}
		PaymentOrderDto paymentOrderDto = (PaymentOrderDto) mainDtoUtil
				.convert2Dto(order);
		return paymentOrderDto;
	}

	public PaymentOrderDto updatePayementOrderInIsolatedTx(
			PaymentOrderDto paymentOrder) {
		Object obj = this.update(paymentOrder);
		if (obj == null) {
			return null;
		}
		return (PaymentOrderDto) obj;
	}

	public MutableDto update(final MutableDto dto) {
		mainDao.update(dto);
		return dto;
	}

	public PaymentOrderDto getOerderAndLock(Object seqId,
			Map<String, SqlParameterValue> status) {
		return (PaymentOrderDto) mainDtoUtil
				.convert2Dto(((PaymentOrderDAO) getMainDao()).getOerderAndLock(
						seqId, status));
	}

	@Override
	public PaymentOrderDto findByOrderIdAndOrderCode(Integer orderCode,
			String orderid) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("orderCode", orderCode);
		paramMap.put("orderId", orderid);

		PaymentOrder order = ((PaymentOrder) this.getMainDao()
				.findObjectBySelective(paramMap));
		if (order == null) {
			return null;
		}
		PaymentOrderDto paymentOrderDto = (PaymentOrderDto) mainDtoUtil
				.convert2Dto(order);
		return paymentOrderDto;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return PaymentOrder.class;
	}

	@Override
	protected Class getDtoClass() {
		// TODO Auto-generated method stub
		return PaymentOrderDto.class;
	}
}
