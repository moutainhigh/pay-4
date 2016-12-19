/**@Description 
 * @project 	fo-accounting
 * @file 		AccountingServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		terry_ma		Create 
 */
package com.pay.pe.service.accounting.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.pe.dao.BankOrgCodeMappingDAO;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingEntryDetailDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.PayMethod;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

public class AccountingServiceImpl implements AccountingService {

	private Integer orderCode;
	private Integer dealCode;
	private Integer payerOrgType;
	private Integer payeeOrgType;
	private Map<Integer, Integer> dealTypeMap;
	private PEService peService;
	private AccountQueryService accountQueryService;
	protected MemberQueryService memberQueryService;
	private AccountBalanceHandlerService accountBalanceHandlerService;
	private final Log logger = LogFactory.getLog(AccountingServiceImpl.class);

	@Override
	public List<AccountingEntryDetailDto> generateDealEntry(
			AccountingDto accountingDto) throws Exception {

		List<AccountingEntryDetailDto> entrys = null;

		PaymentResponseDto calFeeRespone = callFee(accountingDto);

		Assert.notNull(calFeeRespone, "calFeeRespone must not be noll ");

		List<PaymentDetailDto> detailList = calFeeRespone.getPaymentDetails();
		if (null != detailList && !detailList.isEmpty()) {
			entrys = new ArrayList<AccountingEntryDetailDto>();
			for (PaymentDetailDto detail : detailList) {
				AccountingEntryDetailDto entry = new AccountingEntryDetailDto();
				BeanUtils.copyProperties(detail, entry);
				entrys.add(entry);
			}
		}

		return entrys;
	}

	@Override
	public AccountingFeeRes caculateFee(final AccountingFeeRe accountingFeeRe)
			throws MaAccountQueryUntxException {

		if (logger.isInfoEnabled()) {
			logger.info("accounting orderCode:" + orderCode);
			logger.info("accounting dealCode:" + dealCode);
			logger.info("accounting vo:" + accountingFeeRe);
		}

		Long amount = accountingFeeRe.getAmount();
		Assert.notNull(amount, "caculateFee amount must be not null");

		PaymentReqDto calFeeRequest = contructFeeRequest(accountingFeeRe);
		PaymentResponseDto calFeeRespone = null ;
		if(!calFeeRequest.isHasCaculatedPrice()){
			calFeeRespone = peService.caculateFee(calFeeRequest);
		}
		AccountingFeeRes accountingFeeRes = new AccountingFeeRes();
		if (null != calFeeRespone) {
			accountingFeeRes.setPayeeFee(calFeeRespone.getPayeeFee());
			accountingFeeRes.setPayerFee(calFeeRespone.getPayerFee());
			accountingFeeRes.setAmount(accountingFeeRe.getAmount());
		}else{
			if(null != calFeeRequest){
				accountingFeeRes.setPayeeFee(calFeeRequest.getPayeeFee());
				accountingFeeRes.setPayerFee(calFeeRequest.getPayerFee());
				accountingFeeRes.setAmount(calFeeRequest.getAmount());
			}
		}
		return accountingFeeRes;
	}

	/**
	 * 记账
	 * 
	 * @param accountingDto
	 */
	@Override
	public void doAccounting(final AccountingDto accountingDto)
			throws MaAcctBalanceException, IllegalArgumentException,
			MaAccountQueryUntxException {
		logger.info(" in doAccounting");
		if (logger.isInfoEnabled()) {
			logger.info("accounting orderCode:" + orderCode);
			logger.info("accounting dealCode:" + dealCode);
			logger.info("accounting info:" + accountingDto.toString());
		}

		Long amount = accountingDto.getAmount();
		logger.info(" in second doAccounting");
		if (null != amount && amount > 0) {

			PaymentResponseDto calFeeRespone = callFee(accountingDto);
			if (null != calFeeRespone) {
				PaymentReqDto calFeeRequest = calFeeRespone.getPaymentReq();
				calFeeRequest.setMerchantOrderId(accountingDto.getMerchantOrderId());
				int result = doUpdateBalance(calFeeRequest, calFeeRespone,
						dealTypeMap.get(dealCode));

				if (logger.isInfoEnabled()) {
					logger.info("update balance result:" + result);
				}

				Assert.isTrue(1 == result, "do update acct balance exception");
			}
		}
	}

	@Override
	public int doAccountingReturn(AccountingDto accountingDto) {

		if (logger.isInfoEnabled()) {
			logger.info("accounting orderCode:" + orderCode);
			logger.info("accounting dealCode:" + dealCode);
			logger.info("accounting info:" + accountingDto.toString());
		}

		Long amount = accountingDto.getAmount();

		int result = 0;
		if (amount > 0) {

			try {
				PaymentResponseDto calFeeRespone = callFee(accountingDto);
				if (logger.isInfoEnabled()) {
					logger.info("call fee response:" + calFeeRespone);
				}

				if (null != calFeeRespone) {
					PaymentReqDto calFeeRequest = calFeeRespone.getPaymentReq();
					String merchantOrderId = accountingDto.getMerchantOrderId();
					calFeeRequest.setMerchantOrderId(merchantOrderId);
					result = doUpdateBalance(calFeeRequest, calFeeRespone,
							dealTypeMap.get(dealCode));
					if (logger.isInfoEnabled()) {
						logger.info("update balance result:" + result);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}

	public void setPayerOrgType(Integer payerOrgType) {
		this.payerOrgType = payerOrgType;
	}

	public void setPayeeOrgType(Integer payeeOrgType) {
		this.payeeOrgType = payeeOrgType;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setAccountBalanceHandlerService(
			AccountBalanceHandlerService accountBalanceHandlerService) {
		this.accountBalanceHandlerService = accountBalanceHandlerService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setDealTypeMap(Map<Integer, Integer> dealTypeMap) {
		this.dealTypeMap = dealTypeMap;
	}

	private PaymentResponseDto callFee(AccountingDto accountingDto)
			throws MaAcctBalanceException, MaAccountQueryUntxException {

		Long amount = accountingDto.getAmount();
		String orderId = accountingDto.getOrderId();
		Long orderAmount = accountingDto.getOrderAmount();

		Assert.notNull(amount, "do accounting amount must not be null");
		Assert.notNull(orderId, "do accounting orderId must not be null");
		Assert.notNull(orderAmount,
				"do accounting orderAmount must not be null");

		PaymentReqDto calFeeRequest = contructFeeRequest(accountingDto);
		PaymentResponseDto calFeeRespone = peService.processPayment(calFeeRequest);
		return calFeeRespone;
	}

	/**
	 * 算好费后更新余额
	 * 
	 * @param calFeeRespone
	 */
	private int doUpdateBalance(PaymentReqDto calFeeRequest,
			PaymentResponseDto calFeeRespone, Integer businessType)
			throws MaAcctBalanceException {

		CalFeeReponseDto calFeeReponseDto = buildUpdateBalanceRequest(
				calFeeRequest, calFeeRespone);

		int result = accountBalanceHandlerService.doUpdateAcctBalanceRntx(
				calFeeReponseDto, businessType);

		return result;
	}

	private CalFeeReponseDto buildUpdateBalanceRequest(
			PaymentReqDto calFeeRequest, PaymentResponseDto calFeeRespone) {
		CalFeeReponseDto calFeeReponseDto = BeanConvertUtil.convert(
				CalFeeReponseDto.class, calFeeRespone);
		CalFeeRequestDto calFeeRequestDto = BeanConvertUtil.convert(
				CalFeeRequestDto.class, calFeeRequest);
		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
		calFeeReponseDto.setMerchantOrderId(calFeeRequest.getMerchantOrderId());

		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
		List<PaymentDetailDto> calFeeDetails = calFeeRespone
				.getPaymentDetails();
		if (null != calFeeDetails && !calFeeDetails.isEmpty()) {
			for (PaymentDetailDto calFeeDetail : calFeeDetails) {
				CalFeeDetailDto calFeeDetailDto = BeanConvertUtil.convert(
						CalFeeDetailDto.class, calFeeDetail);
				calFeeDetailDtos.add(calFeeDetailDto);
			}
		}
		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
		return calFeeReponseDto;
	}

	private PaymentReqDto contructFeeRequest(AccountingDto accountingDto)
			throws MaAccountQueryUntxException {

		Long amount = accountingDto.getAmount();
		String orderId = accountingDto.getOrderId();
		String bankCode = accountingDto.getBankCode();
		String payerCurrencyCode = accountingDto.getPayerCurrencyCode();
		String payeeCurrencyCode = accountingDto.getPayeeCurrencyCode();
		Long payer = accountingDto.getPayer();
		Long payee = accountingDto.getPayee();
		Integer payerAcctType = accountingDto.getPayerAcctType();
		Integer payeeAcctType = accountingDto.getPayeeAcctType();
		Long exchangeRate = accountingDto.getExchangeRate();

		PaymentReqDto calFeeRequest = new PaymentReqDto();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setAmount(amount);
		calFeeRequest.setMerchantOrderId(accountingDto.getMerchantOrderId());
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setOrderAmount(accountingDto.getOrderAmount());
		calFeeRequest.setPayerOrgType(payerOrgType);
		calFeeRequest.setPayeeOrgType(payeeOrgType);
		calFeeRequest.setPayMethod(PayMethod.DIRECTACCOUNT.getValue());
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setExchangeRate(exchangeRate);
		calFeeRequest.setTerminalType(TerminalType.WEB.getValue());
		calFeeRequest.setPayerCurrencyCode(payerCurrencyCode);
		calFeeRequest.setPayeeCurrencyCode(payeeCurrencyCode);
		calFeeRequest.setBankCode(bankCode);
		Assert.notNull(payerCurrencyCode, "payerCurrencyCode must be not null");
		Assert.notNull(payeeCurrencyCode, "payeeCurrencyCode must be not null");

		// payer info
		if (null != payerOrgType) {
			if (OrgType.MEMBER.getValue() == payerOrgType) {
				Assert.notNull(payer, "payer can not be null");
				Assert.notNull(payerAcctType, "payerAcctType must be not null");
				setPayerInfo(accountingDto, calFeeRequest);
			} else if (OrgType.BANK.getValue() == payerOrgType) {
				//Assert.notNull(bankCode, "bankCode must be not null");
			}
		}

		// payee info
		if (null != payeeOrgType) {
			if (OrgType.MEMBER.getValue() == payeeOrgType) {
				Assert.notNull(payee, "payee can not be null");
				Assert.notNull(payeeAcctType, "payeeAcctType must be not null");
				setPayeeInfo(accountingDto, calFeeRequest);
			} else if (OrgType.BANK.getValue() == payeeOrgType) {
				//Assert.notNull(bankCode, "bankCode must be not null");
			}
		}

		// outside calculate fee
		if (accountingDto.isHasCaculatedPrice()) {
			calFeeRequest.setHasCaculatedPrice(true);
			calFeeRequest.setHasCaculatedPayerPrice(true);
			calFeeRequest.setHasCaculatedPayeePrice(true);
			calFeeRequest.setPayerFee(accountingDto.getPayerFee());
			calFeeRequest.setPayeeFee(accountingDto.getPayeeFee());
		}
		return calFeeRequest;
	}

	private void setPayerInfo(AccountingDto accountingDto,
			PaymentReqDto calFeeRequest) throws MaAccountQueryUntxException,
			IllegalArgumentException {

		String payerFullMemberAcctCode = accountingDto
				.getPayerFullMemberAcctCode();

		Long payer = accountingDto.getPayer();
		Integer acctType = accountingDto.getPayerAcctType();

		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(payer);

		if (StringUtil.isEmpty(payerFullMemberAcctCode)) {
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(payer, acctType);
			Assert.notNull(acctAttribDto, "acctAttribDto must not be null");
			payerFullMemberAcctCode = acctAttribDto.getAcctCode();
		}

		calFeeRequest.setPayerMemberType(memberBaseInfoBO.getMemberType());
		calFeeRequest.setPayer(String.valueOf(payer));
		calFeeRequest.setPayerAcctType(acctType);
		calFeeRequest.setPayerFullMemberAcctCode(payerFullMemberAcctCode);
	}

	private void setPayeeInfo(AccountingDto accountingDto,
			PaymentReqDto calFeeRequest) throws MaAccountQueryUntxException {

		String payeeFullMemberAcctCode = accountingDto
				.getPayeeFullMemberAcctCode();

		Long payee = accountingDto.getPayee();
		Integer acctType = accountingDto.getPayeeAcctType();

		if (StringUtil.isEmpty(payeeFullMemberAcctCode)) {
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(payee, acctType);
			Assert.notNull(acctAttribDto, "acctAttribDto must not be null");
			payeeFullMemberAcctCode = acctAttribDto.getAcctCode();
		}

		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(payee);
		calFeeRequest.setPayeeMemberType(memberBaseInfoBO.getMemberType());
		calFeeRequest.setPayee(String.valueOf(payee));
		calFeeRequest.setPayeeAcctType(acctType);

		calFeeRequest.setPayeeFullMemberAcctCode(payeeFullMemberAcctCode);
	}

}
