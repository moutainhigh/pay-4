/**
 *  File: Batch2bankBusinessNoCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.UploadFileType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankTemplateUtils;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证批付到银行批次号
 */
public class Batch2bankBusinessNoCheckRule extends MessageRule {

	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	private BatchPaymentOrderService batchPaymentOrderService;

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		String batchId = "";
		if (UploadFileType.xls.getValue() == request.getFileType()) {
			Sheet sheet = request.getJxlSheet();
			Cell[] cells = sheet.getRow(0);
			if (cells != null) {
				batchId = BatchPay2BankTemplateUtils
						.parseCellToString(cells[2]);
			}
		} else if (UploadFileType.csv.getValue() == request.getFileType()) {
			List<String[]> csvList = request.getCsvList();
			if (null != csvList) {
				batchId = csvList.get(0)[2].toString();
			}
		}
		String businessNo = request.getBusinessNo();
		Long memberCode = request.getMemberCode();

		if (StringUtil.isEmpty(businessNo) || StringUtil.isEmpty(batchId)
				|| !batchId.equals(request.getBusinessNo())) {
			request.getBatchPaymentResponse().setErrorMsg(getMessageId());
			return false;
		} else {
			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService
					.getBatchPaymentReqBaseInfo(memberCode,
							OrderType.BATCHPAY2BANK.getValue(), businessNo);
			if (null != reqInfo) {
				request.getBatchPaymentResponse().setErrorMsg(
						"您填写的业务批次号已存在，请修改后再试");
				return false;
			} else {
				// 判断总订单表中批次是否存在
				BatchPaymentOrderDTO batchPaymentOrderDTO = batchPaymentOrderService
						.getOrder(memberCode,
								OrderType.BATCHPAY2BANK.getValue(), businessNo);
				if (null != batchPaymentOrderDTO) {
					request.getBatchPaymentResponse().setErrorMsg(
							"您填写的业务批次号已存在，请修改后再试");
					return false;
				}
				return true;
			}

		}
	}

	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}
}
