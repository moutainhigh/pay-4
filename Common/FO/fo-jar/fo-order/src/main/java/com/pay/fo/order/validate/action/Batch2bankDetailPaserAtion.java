/**
 *  File: Batch2bankRecordPaserAtion.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate.action;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.common.UploadFileType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankTemplateUtils;
import com.pay.fo.order.service.batchpay2bank.ConfigurationCalcuFeeService;
import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fo.order.validate.PaymentResponse;
import com.pay.fo.order.validate.ValidateService;
import com.pay.inf.rule.AbstractAction;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.StringUtil;

/**
 * 
 */
public class Batch2bankDetailPaserAtion extends AbstractAction {

	private ValidateService batch2bankValidateService;
	/**
	 * 风控限额限次查询服务类
	 */
	private FoRcLimitFacade foRcLimitFacade;

	// 算费服务
	private AccountingService reqAccountingService;
	
	private ConfigurationCalcuFeeService configurationCalcuFeeService ;

	@Override
	protected void doExecute(final Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		Long memberCode = request.getMemberCode();
		Integer isPayerPayFee = request.getIsPayerPayFee();
		//----------------added  PengJiangbo
		Integer payerAcctType = request.getPayerAcctType();
		Integer payeeAcctType = request.getPayeeAcctType();
		String payerCurrencyCode = request.getPayerCurrencyCode();
		String payeeCurrencyCode = request.getPayeeCurrencyCode();
		//----------------added  PengJiangbo end

		RCLimitResultDTO rule = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2P.getKey(), null, memberCode);
		BatchPaymentToBankReqDetailDTO detail = null;
		int totalNum = 0;
		int validNum = 0;
		long totalAmount = 0L;
		long validAmount = 0L;
		long fee = 0L;
		long totalFee = 0L;

		List<RequestDetail> details = new ArrayList<RequestDetail>();

		if (UploadFileType.xls.getValue() == request.getFileType()) {
			Sheet sheet = request.getJxlSheet();
			for (int i = 2; i < sheet.getRows(); i++) {
				Cell[] cells = sheet.getRow(i);
				if (cells != null) {
					// 解析并装配请求明细对象
					detail = BatchPay2BankTemplateUtils.getBatchPaymentToBankReqDetail(cells);

					// 跳过空行
					if (null == detail) {
						continue;
					}

					PaymentRequest detailRequest = new PaymentRequest();
					BeanUtils.copyProperties(detail, detailRequest);
					detailRequest.setRiskRule(rule);
					detailRequest.setRow(i + 1);
					batch2bankValidateService.validate(detailRequest);

					PaymentResponse detailResponse = detailRequest.getPaymentResponse();

					String errorMsg = detailResponse.getErrorMsg();

					detail.setPaymentAmount(detailResponse.getPaymentAmount());
					detail.setIsPayerPayFee(isPayerPayFee);

					if (StringUtil.isEmpty(errorMsg)) {
						detail.setValidStatus(1);
						detail.setBankNumber(detailResponse.getBankNumber());
						//-----------------added  PengJiangbo
						detail.setPayerAcctType(payerAcctType);
						detail.setPayeeAcctType(payeeAcctType);
						detail.setPayerCurrencyCode(payerCurrencyCode);
						detail.setPayeeCurrencyCode(payeeCurrencyCode);
						//-----------------added  PengJiangbo end
						// 统计有效记录和有效金额
						caculateFee(memberCode, isPayerPayFee, detail);
						fee = detail.getFee();
						validNum = validNum + 1;
						validAmount = validAmount + detail.getPaymentAmount().longValue();
						totalFee = totalFee + Math.abs(fee);

					} else {
						detail.setErrorMsg(errorMsg);
						detail.setValidStatus(0);
						detail.setFee(0L);
						detail.setRealpayAmount(0L);
						detail.setRealoutAmount(0L);
					}
					BeanUtils.copyProperties(detailResponse, detail);
					details.add(detail);

					// 统计总记录数 和金额
					totalNum = totalNum + 1;
					totalAmount = totalAmount + detail.getPaymentAmount().longValue();
				}
			}
		} else if (UploadFileType.csv.getValue() == request.getFileType()) {
			List<String[]> csvList = request.getCsvList();
			for (int i = 2; i < csvList.size(); i++) {
				String[] row = csvList.get(i);
				if (row != null) {
					// 解析并装配请求明细对象
					
					detail = new BatchPaymentToBankReqDetailDTO();
					detail.setForeignOrderId(row[9].toString()); // 商户订单号
					detail.setPayeeName(row[5].toString()); // 收款方名称
					detail.setPayeeBankAcctCode(row[6].toString()); // 收款方银行账号
					detail.setTradeType(row[4].toString()); // 收款方账户类型（交易类型）
					detail.setPayeeBankName(row[2].toString()); // 收款行银行名称
					detail.setPayeeOpeningBankName(row[3].toString()); // 收款方开户行名称

					detail.setPayeeBankProvinceName(row[0].toString()); // 收款行所在省份名称
					detail.setPayeeBankCityName(row[1].toString()); // 收款行所在城市名称
					detail.setRequestAmount(row[7].toString()); // 请求付款金额

					// 跳过空行
					if (null == detail) {
						continue;
					}

					PaymentRequest detailRequest = new PaymentRequest();
					BeanUtils.copyProperties(detail, detailRequest);
					detailRequest.setRiskRule(rule);
					detailRequest.setRow(i + 1);
					batch2bankValidateService.validate(detailRequest);

					PaymentResponse detailResponse = detailRequest.getPaymentResponse();

					String errorMsg = detailResponse.getErrorMsg();

					detail.setPaymentAmount(detailResponse.getPaymentAmount());
					detail.setIsPayerPayFee(isPayerPayFee);

					if (StringUtil.isEmpty(errorMsg)) {
						detail.setValidStatus(1);
						detail.setBankNumber(detailResponse.getBankNumber());
						//-----------------added  PengJiangbo
						detail.setPayerAcctType(payerAcctType);
						detail.setPayeeAcctType(payeeAcctType);
						detail.setPayerCurrencyCode(payerCurrencyCode);
						detail.setPayeeCurrencyCode(payeeCurrencyCode);
						//-----------------added  PengJiangbo end
						// 统计有效记录和有效金额
						caculateFee(memberCode, isPayerPayFee, detail);
						fee = detail.getFee();
						validNum = validNum + 1;
						validAmount = validAmount + detail.getPaymentAmount().longValue();
						totalFee = totalFee + Math.abs(fee);

					} else {
						detail.setErrorMsg(errorMsg);
						detail.setValidStatus(0);
						detail.setFee(0L);
						detail.setRealpayAmount(0L);
						detail.setRealoutAmount(0L);
					}
					BeanUtils.copyProperties(detailResponse, detail);
					details.add(detail);

					// 统计总记录数 和金额
					totalNum = totalNum + 1;
					totalAmount = totalAmount + detail.getPaymentAmount().longValue();
				}
			}
		}

		request.getBatchPaymentResponse().setDetails(details);
		request.getBatchPaymentResponse().setTotalFee(totalFee);
		request.getBatchPaymentResponse().setTotalNum(totalNum);
		request.getBatchPaymentResponse().setTotalAmount(totalAmount);
		request.getBatchPaymentResponse().setValidNum(validNum);
		request.getBatchPaymentResponse().setValidAmount(validAmount);

		if (isPayerPayFee == 1) {
			request.getBatchPaymentResponse().setRealpayAmount(fee + validAmount);
			request.getBatchPaymentResponse().setRealoutAmount(validAmount);
		} else {
			request.getBatchPaymentResponse().setRealpayAmount(validAmount);
			request.getBatchPaymentResponse().setRealoutAmount(validAmount - fee);
		}
	}

	public void setBatch2bankValidateService(final ValidateService batch2bankValidateService) {
		this.batch2bankValidateService = batch2bankValidateService;
	}

	public void setFoRcLimitFacade(final FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	public void setReqAccountingService(final AccountingService reqAccountingService) {
		this.reqAccountingService = reqAccountingService;
	}

	/**
	 * 获取费用
	 * 
	 * @param mass
	 * @param detail
	 */
	private void caculateFee(final Long memberCode, final Integer isPayerPayFee, final BatchPaymentToBankReqDetailDTO detail) {

		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(detail.getPaymentAmount());
		accountingFeeRe.setPayer(memberCode);
		
		//--------------------added  PengJiangbo
		accountingFeeRe.setPayerAcctType(detail.getPayerAcctType());
		accountingFeeRe.setPayeeAcctType(detail.getPayeeAcctType());
		accountingFeeRe.setPayerCurrencyCode(detail.getPayerCurrencyCode());
		accountingFeeRe.setPayeeCurrencyCode(detail.getPayeeCurrencyCode());
		//--------------------added  PengJiangbo end
		//====================added  PengJiangbo 2015-12-24 修改批量付款到银行手续费的收取方式 Start
		accountingFeeRe.setHasCaculatedPrice(true);
		Long payerFee = this.configurationCalcuFeeService.calcuFee(memberCode, detail.getPayerCurrencyCode()) ;
		accountingFeeRe.setPayerFee(payerFee);
		//System.out.println("手续费：" + payerFee);
		//====================added  PengJiangbo 2015-12-24 修改批量付款到银行手续费的收取方式 End
		
		AccountingFeeRes dealResponse = null;
		try {
			dealResponse = reqAccountingService.caculateFee(accountingFeeRe);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Long fee = null;
		// 收款方付费与付款方付费都从payerFee中取值
		fee = dealResponse.getPayerFee();
		if (fee == null) {
			fee = 0L;
		}
		detail.setFee(fee);

		if (isPayerPayFee == 1) {
			detail.setRealpayAmount(fee + detail.getPaymentAmount());
			detail.setRealoutAmount(detail.getPaymentAmount());
		} else {
			detail.setRealpayAmount(detail.getPaymentAmount());
			detail.setRealoutAmount(detail.getPaymentAmount() - fee);
		}
	}

	public void setConfigurationCalcuFeeService(
			final ConfigurationCalcuFeeService configurationCalcuFeeService) {
		this.configurationCalcuFeeService = configurationCalcuFeeService;
	}

	
}
