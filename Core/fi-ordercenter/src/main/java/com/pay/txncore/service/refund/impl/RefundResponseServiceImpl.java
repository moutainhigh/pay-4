package com.pay.txncore.service.refund.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;
import com.pay.txncore.service.refund.RefundResponseService;
import com.pay.txncore.service.refund.RefundTransactionResultBuilder;
import com.pay.txncore.service.refund.SecuredPayRequestService;

public class RefundResponseServiceImpl implements RefundResponseService {

	private RefundTransactionResultBuilder refundBuilder;
	private SecuredPayRequestService securedPayRequestService;

	private Log logger = LogFactory.getLog(RefundResponseServiceImpl.class);

	@Override
	public RefundTransactionServiceResultDTO buildResult(
			RefundTransactionServiceParamDTO paramDTO) {

		RefundTransactionServiceResultDTO resultDTO = null;

		if (paramDTO.getStep() >= 1) {
			try {
				// 以退款订单创建是否成功角度来理解,构建的通知尽量从DB中获取.
				resultDTO = refundBuilder.buildSuccessResult(paramDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				resultDTO = refundBuilder.buildFailResult(paramDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultDTO;
	}

	public void setRefundBuilder(RefundTransactionResultBuilder refundBuilder) {
		this.refundBuilder = refundBuilder;
	}

	public void setSecuredPayRequestService(
			SecuredPayRequestService securedPayRequestService) {
		this.securedPayRequestService = securedPayRequestService;
	}

	@Override
	public void sendNoticeByResponseNo(Long responseNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean recordHisttory(RefundTransactionServiceParamDTO paramDTO) {
		// TODO Auto-generated method stub
		return false;
	}

}
