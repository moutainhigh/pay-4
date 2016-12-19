package com.pay.txncore.service.refund.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.commons.ResultCode;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundTransactionResultBuilder;
import com.pay.util.DateStringUtil;

public class RefundTransactionResultBuilderImpl implements
		RefundTransactionResultBuilder {

	private RefundOrderService refundOrderService; // 退款订单服务类
	private TradeDataSingnatureService tradeSingnatureService;

	@Override
	public RefundTransactionServiceResultDTO buildFailResult(
			RefundTransactionServiceParamDTO paramDTO) throws Exception {
		RefundTransactionServiceResultDTO resultDTO = new RefundTransactionServiceResultDTO();
		if (paramDTO == null) {
			resultDTO.setResultCode(ResultCode.REQUIRES_PARAM_ISNULL.getCode());
			return resultDTO;
		}
		resultDTO.setRefundOrderId((paramDTO.getRefundOrderId() == null) ? ""
				: paramDTO.getRefundOrderId());
		resultDTO.setResultCode((paramDTO.getErrorCode() == null) ? ""
				: paramDTO.getErrorCode());
		resultDTO.setOrderId((paramDTO.getOrderId() == null) ? "" : paramDTO
				.getOrderId().trim());// 原始
		resultDTO.setRefundAmount((paramDTO.getRefundAmount() == null) ? ""
				: paramDTO.getRefundAmount().trim());
		resultDTO.setRefundTime((paramDTO.getRefundTime() == null) ? ""
				: paramDTO.getRefundTime().trim());
		resultDTO.setCompleteTime(DateStringUtil.date2String(new Date()));
		resultDTO.setRefundNo(""); // 此步没有产生
		resultDTO.setPartnerId((paramDTO.getPartnerId() == null) ? ""
				: paramDTO.getPartnerId().trim());
		resultDTO.setRemark((paramDTO.getRemark() == null) ? "" : paramDTO
				.getRemark().trim());
		resultDTO.setStateCode(String.valueOf(RefundStatusEnum.FAIL.getCode()));

		resultDTO.setNoticeUrl((paramDTO.getNoticeUrl() == null) ? ""
				: paramDTO.getNoticeUrl().trim());
		return resultDTO;
	}

	/**
	 * db
	 * 
	 * @throws Exception
	 */
	@Override
	public RefundTransactionServiceResultDTO buildSuccessResult(
			RefundTransactionServiceParamDTO paramDTO) throws Exception {
		Long refundOrder = paramDTO.getRefundOrderDTO().getRefundOrderNo();
		RefundOrderDTO refundDTO = refundOrderService.queryByPk(refundOrder);
		RefundTransactionServiceResultDTO resultDTO = new RefundTransactionServiceResultDTO();
		resultDTO
				.setRefundOrderId((refundDTO.getPartnerRefundOrderId() == null) ? ""
						: refundDTO.getPartnerRefundOrderId());
		resultDTO.setResultCode((refundDTO.getErrorCode() == null) ? ""
				: refundDTO.getErrorCode());
		if (refundDTO.getRefundAmount() != null) {
			BigDecimal refundAmount = new BigDecimal(
					refundDTO.getRefundAmount());
			resultDTO.setRefundAmount(refundAmount.divide(new BigDecimal(10),
					0, BigDecimal.ROUND_DOWN).toString());
		}
		resultDTO.setRefundTime((refundDTO.getPartnerRefundTime() == null) ? ""
				: DateStringUtil.date2String(refundDTO.getPartnerRefundTime()));
		resultDTO.setCompleteTime((refundDTO.getUpdateDate() == null) ? ""
				: DateStringUtil.date2String(refundDTO.getUpdateDate()));
		resultDTO.setRefundNo(refundDTO.getRefundOrderNo().toString());
		resultDTO.setPartnerId((refundDTO.getPartnerId() == null) ? ""
				: refundDTO.getPartnerId());
		resultDTO.setRemark((refundDTO.getRemark() == null) ? "" : refundDTO
				.getRemark());
		resultDTO.setStateCode(refundDTO.getStatus());
		// //原始
		resultDTO.setOrderId(paramDTO.getOrderId().trim());
		resultDTO.setNoticeUrl(paramDTO.getNoticeUrl().trim());
		resultDTO.setRemark(paramDTO.getRemark().trim());
		return resultDTO;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setTradeSingnatureService(
			TradeDataSingnatureService tradeSingnatureService) {
		this.tradeSingnatureService = tradeSingnatureService;
	}

	public RefundOrderService getRefundOrderService() {
		return refundOrderService;
	}

	public TradeDataSingnatureService getTradeSingnatureService() {
		return tradeSingnatureService;
	}

}
