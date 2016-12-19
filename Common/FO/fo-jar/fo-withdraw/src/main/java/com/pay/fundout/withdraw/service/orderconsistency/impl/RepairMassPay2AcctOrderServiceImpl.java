package com.pay.fundout.withdraw.service.orderconsistency.impl;

import java.util.Date;

import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToAcctReqDetailService;
import com.pay.fundout.helper.BatchPaymenttoacctreqdetailStatus;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.fundout.withdraw.service.operatorlog.OperatorlogService;
import com.pay.fundout.withdraw.service.orderconsistency.RepairMassPay2AcctOrderService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class RepairMassPay2AcctOrderServiceImpl implements
		RepairMassPay2AcctOrderService {

	private OperatorlogService logService;
	private BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService;
	private BatchPaymentOrderService batchPaymentOrderService;

	public void setLogService(OperatorlogService logService) {
		this.logService = logService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	public void setBatchPaymentToAcctReqDetailService(
			BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService) {
		this.batchPaymentToAcctReqDetailService = batchPaymentToAcctReqDetailService;
	}

	@Override
	public void repairMassPay2AcctOrderRnTx(MasspayImportRecord impFilerecord,
			BatchPaymentOrderDTO batchPaymentOrderDTO, String operator)
			throws PossException {
		if (impFilerecord != null && batchPaymentOrderDTO != null) {
			try {
				
				BatchPaymentToAcctReqDetailDTO reqDetail = batchPaymentToAcctReqDetailService
						.getDetail(impFilerecord.getSequenceId());
				
				if (null == reqDetail
						|| reqDetail.getOrderStatus() == BatchPaymenttoacctreqdetailStatus.CREATED
								.getValue()) {
					return;
				}
				batchPaymentOrderService.createDetailOrder(batchPaymentOrderDTO, reqDetail);
				//repairOpLog(batchPaymentOrderDTO,impFilerecord,operator);
			} catch (Exception e) {
				throw new PossException("批量付款到账户修补子订单异常...,明细文件ID："
						+ impFilerecord.getSequenceId(),
						ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, e);
			}
		}
	}

	private void repairOpLog(BatchPaymentOrderDTO batchPaymentOrderDTO,
			MasspayImportRecord impFilerecord, String operator) {
		try {
			OperatorlogDTO logDto = new OperatorlogDTO();
			logDto.setBusiOrderId(batchPaymentOrderDTO.getBusinessBatchNo());
			logDto.setCreationDate(new Date());
			logDto.setLogType(11);
			logDto.setLogTypeDesc("批量付款到账户总订单生成后未生成子订单");
			logDto.setMark("总订单号：" + batchPaymentOrderDTO.getOrderId()
					+ "|批次号:" + batchPaymentOrderDTO.getBusinessBatchNo()
					+ "|付款方会员号:" + batchPaymentOrderDTO.getPayerMemberCode()
					+ "明细记录号:" + impFilerecord.getSequenceId());
			logDto.setOperator(operator);
			this.logService.saveOperatorLog(logDto);
		} catch (Exception e) {
			LogUtil.error(getClass(), "批量付款到账户补单日志记录异常", OPSTATUS.FAIL,
					batchPaymentOrderDTO.getBusinessBatchNo(), "", "", "", "");
		}
	}
}
