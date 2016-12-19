package com.pay.txncore.service.refund.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.inf.dao.Page;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dao.RefundOrderDAO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.model.RefundExceptionMonitor;
import com.pay.txncore.model.RefundOrder;
import com.pay.txncore.model.RefundPayLinkOrder;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

/**
 * @author fred.feng 退款订单请求
 */
public class RefundOrderServiceImpl implements RefundOrderService {

	private final Log logger = LogFactory.getLog(getClass());
	private RefundOrderDAO refundOrderDAO;

	@Override
	public Long queryPartnerRefundAmount(Long paymentOrderNo) {
		return refundOrderDAO.queryPartnerRefundAmount(paymentOrderNo);
	}

	@Override
	public boolean isExistByPIdAndOrderId(String partnerId, String orderId) {
		Long count = 0L;
		if (StringUtil.isEmpty(partnerId) || StringUtil.isEmpty(orderId))
			return false;
		count = refundOrderDAO.countByPidAndOrderId(partnerId, orderId);
		return count > 0;
	}

	/**
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@Override
	public Map<String, Long> getRefundFeeByPaymentOrderNo(Long paymentOrderNo) {
		Map mapValue = refundOrderDAO
				.getRefundFeeByPaymentOrderNo(paymentOrderNo);
		Map<String, Long> mapFee = new HashMap<String, Long>();
		mapFee.put("TotalPayeeFee",
				Long.valueOf(mapValue.get("TOTAL_PAYEE_FEE").toString()));
		mapFee.put("TotalPayerFee",
				Long.valueOf(mapValue.get("TOTAL_PAYER_FEE").toString()));
		return mapFee;
	}

	@Override
	public Long getRefundPayeeFee(Long paymentOrderNo) {
		Map<String, Long> mapFee = this
				.getRefundFeeByPaymentOrderNo(paymentOrderNo);
		return mapFee.get("TotalPayeeFee");
	}

	@Override
	public Long getRefundPayerFee(Long paymentOrderNo) {
		Map<String, Long> mapFee = this
				.getRefundFeeByPaymentOrderNo(paymentOrderNo);
		return mapFee.get("TotalPayerFee");
	}

	@Override
	public boolean lockedRefundForUpdate(Long refundOrderNo) {
		return refundOrderDAO.lockedRefundForUpdate(refundOrderNo);
	}

	@Override
	public boolean updateRefundOrderState(Long refundOrderNo,
			RefundStatusEnum state, String errorCode, Long depositBackNo) {
		return refundOrderDAO.updateRefundOrderState(refundOrderNo,
				state.getCode(), errorCode, depositBackNo);
	}

	@Override
	public boolean updateRefundOrderState(Long refundOrderNo,
			RefundStatusEnum state, Long depositBackNo) {
		return updateRefundOrderState(refundOrderNo, state, null, depositBackNo);
	}

	public void setRefundOrderDAO(RefundOrderDAO refundOrderDAO) {
		this.refundOrderDAO = refundOrderDAO;
	}

	@Override
	public Long calculationRefundAmount(Long tradeOrderInfoId,
			RefundStatusEnum statusEnum) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long create(RefundOrderDTO refundOrderDTO) {
		RefundOrder refundModel = BeanConvertUtil.convert(RefundOrder.class,
				refundOrderDTO);
		return (Long) refundOrderDAO.create(refundModel);
	}

	@Override
	public Long createRefundOrderRnTx(RefundOrderDTO refundOrderDTO) {
		RefundOrder refundModel = BeanConvertUtil.convert(RefundOrder.class,
				refundOrderDTO);
		return (Long) refundOrderDAO.create(refundModel);
	}

	@Override
	public boolean updateRefundOrderByPk(RefundOrderDTO refundOrderDTO) {
		RefundOrder refundOrder = null;
		if (refundOrderDTO == null)
			return false;
		refundOrder = BeanConvertUtil
				.convert(RefundOrder.class, refundOrderDTO);
		return refundOrderDAO.update(refundOrder);
	}

	@Override
	public boolean updateRefundOrderByPkRnTx(RefundOrderDTO refundOrderDTO) {
		RefundOrder refundOrder = null;
		if (refundOrderDTO == null)
			return false;
		refundOrder = BeanConvertUtil
				.convert(RefundOrder.class, refundOrderDTO);
		return refundOrderDAO.update(refundOrder);
	}

	@Override
	public RefundOrderDTO queryByPk(Long id) {
		RefundOrder refundOrder = (RefundOrder) refundOrderDAO.findById(id);
		return BeanConvertUtil.convert(RefundOrderDTO.class, refundOrder);
	}

	@Override
	public List<RefundOrderDTO> findByTradeOrderNo(Long tradeOrderNo) {

		return (List<RefundOrderDTO>) BeanConvertUtil.convert(
				RefundOrderDTO.class,
				refundOrderDAO.findByTradeOrderNo(tradeOrderNo));
	}

	@Override
	public List<RefundOrderDTO> queryByPartner(String parnterID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefundOrderDTO queryByPartnerAndPartnerRefundOrderId(
			String parnterId, String partnerRefundOrderId) {
		Map paraMap = new HashMap();
		paraMap.put("partnerId", parnterId);
		paraMap.put("partnerRefundOrderId", partnerRefundOrderId);
		RefundOrder refundOrder = (RefundOrder) refundOrderDAO
				.findObjectByCriteria("findByCriteria", paraMap);
		RefundOrderDTO refundModelDto = BeanConvertUtil.convert(
				RefundOrderDTO.class, refundOrder);
		return refundModelDto;
	}

	@Override
	public List<RefundOrderDTO> queryRefundOrderInfosByTradeNoAndStatus(
			String tradeOrderInfoId, Integer status) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RefundOrderDTO> findRefundOrder(RefundOrderDTO refundOrderDTO,
			Page page) {

		// Map paraMap = MapUtil.bean2map(refundOrderDTO);
		return refundOrderDAO.findByCriteria(refundOrderDTO, page);
	}
	
	@Override
	public List<RefundOrderDTO> findRefundOrder(RefundOrderDTO refundOrderDTO) {

		// Map paraMap = MapUtil.bean2map(refundOrderDTO);
		return refundOrderDAO.findByCriteria(refundOrderDTO);
	}

	
	@Override
	public List<RefundOrderDTO> findRefundOrderDTOs(RefundOrderDTO refundOrderDTO) {

		return (List<RefundOrderDTO>) BeanConvertUtil.convert(
				RefundOrderDTO.class,
				refundOrderDAO.findByCriteria(refundOrderDTO));
	}
	
	@Override
	public long getMaxChannelSerialNo() {

		Map paraMap = new HashMap();
		return (Long) refundOrderDAO.findObjectByCriteria(
				"getMaxRefundSerialNo", paraMap);
	}

	@Override
	public boolean isLastRefundOrder(Long tradeOrderNo, Long refundOrderNo) {

		return refundOrderDAO.isLastRefundOrder(tradeOrderNo, refundOrderNo);
	}
	
	@Override
	public RefundOrderDTO queryRefundOrderByPaymentOrder(Long paymentOrderNo,
			Integer status) {
		
		Map paraMap = new HashMap();
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("status", status);
		RefundOrder refundOrder = (RefundOrder) refundOrderDAO
				.findObjectByCriteria("findByCriteria", paraMap);
		RefundOrderDTO refundModelDto = BeanConvertUtil.convert(
				RefundOrderDTO.class, refundOrder);
		return refundModelDto;
	}
	
	@Override
	public List<RefundExceptionMonitor> getRefundExceptionMonitorList(Map param,Page page) {
		return refundOrderDAO.getRefundExceptionMonitorList(param,page);
	}
	
	@Override
	public List<RefundPayLinkOrder> queryRefundPayLinkList(Map param, Page page) {
		return refundOrderDAO.queryRefundPayLinkList(param, page);
	}
}
