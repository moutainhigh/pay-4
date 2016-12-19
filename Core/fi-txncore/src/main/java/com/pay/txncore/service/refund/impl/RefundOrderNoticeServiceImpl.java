package com.pay.txncore.service.refund.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.refund.RefundNoticeDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.service.refund.RefundOrderNoticeService;
import com.pay.txncore.service.refund.RefundOrderService;

public class RefundOrderNoticeServiceImpl implements RefundOrderNoticeService {

	private final Log log = LogFactory
			.getLog(RefundOrderNoticeServiceImpl.class);

	private RefundOrderService refundOrderService;

	@Override
	public void noticeRefundOrderRnTx(RefundNoticeDTO refundNoticeDTO) {
		log.info("@FI-RefundOrderNoticeServiceImpl-noticeRefundOrder-充退通知处理开始");
		if (refundNoticeDTO == null) {
			log.error("@FI-RefundOrderNoticeServiceImpl-noticeRefundOrder-refundNoticeDTO为空");
			return;
		}
		boolean isLocked = false;

		isLocked = refundOrderService.lockedRefundForUpdate(refundNoticeDTO
				.getRefundOrderNo());

		if (!isLocked) {
			log.error("@FI-RefundOrderNoticeServiceImpl-noticeRefundOrder-锁退款订单异常"
					+ refundNoticeDTO.getRefundOrderNo());
			return;
		}

		RefundOrderDTO refundOrderDTO = refundOrderService
				.queryByPk(refundNoticeDTO.getRefundOrderNo());

		// 判断退款金额与通知金额是否一致
		if (refundOrderDTO.getRefundAmount().compareTo(
				refundNoticeDTO.getRefundAmount()) == 0) {
			if (refundOrderDTO.getStatus().equals(
					String.valueOf(RefundStatusEnum.SUCCESS.getCode()))) {
				log.info("@FI-RefundOrderNoticeServiceImpl-noticeRefundOrder-充退状态已经成功,通知重复到达,系统不做处理."
						+ refundOrderDTO);
				return;
			}
			if (refundNoticeDTO.getStatus() == 1) {
				refundOrderService.updateRefundOrderState(
						refundNoticeDTO.getRefundOrderNo(),
						RefundStatusEnum.SUCCESS,
						refundNoticeDTO.getDepositBackNo());
			} else if (refundNoticeDTO.getStatus() == 0) {
				refundOrderService.updateRefundOrderState(
						refundNoticeDTO.getRefundOrderNo(),
						RefundStatusEnum.FAIL,
						refundNoticeDTO.getDepositBackNo());
			}
		} else
			log.error("@FI-RefundOrderNoticeServiceImpl-noticeRefundOrder-refundNoticeDTO通知异常"
					+ refundNoticeDTO);
		log.info("@FI-RefundOrderNoticeServiceImpl-noticeRefundOrder-充退通知处理结束");
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}
}
