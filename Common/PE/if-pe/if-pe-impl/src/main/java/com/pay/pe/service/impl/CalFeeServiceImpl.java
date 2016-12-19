package com.pay.pe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.dto.AcctSpecDTO;
import com.pay.pe.dto.DealDto;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.exception.ErrorCodeType;
import com.pay.pe.exception.PEBisnessRuntimeException;
import com.pay.pe.helper.AcctType;
import com.pay.pe.helper.BalanceByEnum;
import com.pay.pe.helper.CRDRType;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.RESERVEDCODETYPE;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.helper.Transactor;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.CalFeeService;
import com.pay.pe.service.account.AcctSpecService;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.pe.service.order.DealService;
import com.pay.pe.service.payment.Payment;
import com.pay.pe.service.payment.common.PaymentRequest;
import com.pay.pe.service.payment.common.PaymentResponse;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pricingstrategy.service.CalPricingStrategyParam;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;
import com.pay.util.MfDateTime;
import com.pay.util.Money;
import com.pay.util.StringUtil;

public class CalFeeServiceImpl implements CalFeeService {

	private final Log logger = LogFactory.getLog(getClass());
	private CurrencyService currencyService;
	private AcctSpecService acctSpecService;
	/** 分录产生服务 */
	private Payment payment;
	private PaymentServiceService paymentServiceService;
	private PricingStrategyService pricingService;
	private DealService dealService;
	private boolean isPreCall = true;

	/** 验证请求对象 */
	private boolean validRequest(CalFeeRequest calFeeRequest) {
		if (calFeeRequest == null) {
			logger.error("calFeeRequest is null !");
			return false;
		}

		if (calFeeRequest.getOrderAmount() == null
				|| calFeeRequest.getAmount() == null
				|| calFeeRequest.getDealCode() == null
				|| calFeeRequest.getOrderCode() == null) {
			logger.error("calFeeRequest.getOrderAmount()==null || calFeeRequest.getAmount()==null || calFeeRequest.getDealCode()==null || calFeeRequest.getOrderCode()==null !");
			return false;
		}
		if (calFeeRequest.getRequestDate() == null) {
			logger.error("calFeeRequest.getRequestDate() == null!");
			return false;
		}

		if (calFeeRequest.getPayerOrgType() == null
				|| calFeeRequest.getPayeeOrgType() == null) {
			logger.error("calFeeRequest.getPayerOrgType()==null ||  calFeeRequest.getPayeeOrgType()==null  !");
			return false;
		}

		if (calFeeRequest.getPayerOrgType().equals(
				"" + OrgType.MEMBER.getValue())) {
			if (calFeeRequest.getPayer() == null
					|| calFeeRequest.getPayerMemberAcctCode() == null
					|| calFeeRequest.getPayerFullMemberAcctCode() == null) {
				logger.error("calFeeRequest.getPayer()==null || calFeeRequest.getPayerMemberAcctCode()==null||calFeeRequest.getPayerFullMemberAcctCode()==null  !");
				return false;
			}

			if (calFeeRequest.getPayer().equals(
					calFeeRequest.getPayerMemberAcctCode())
					|| calFeeRequest.getPayer().equals(
							calFeeRequest.getPayerFullMemberAcctCode())) {
				logger.error("calFeeRequest.getPayer().equals(calFeeRequest.getPayerMemberAcctCode())  || calFeeRequest.getPayer().equals(calFeeRequest.getPayerFullMemberAcctCode()) !");
			}

		} else if (calFeeRequest.getPayerOrgType().equals(
				"" + OrgType.BANK.getValue())) {
			if (calFeeRequest.getPayerOrgCode() == null) {
				logger.error("calFeeRequest.getPayerOrgCode()==null    !");
				return false;
			}
		}

		if (calFeeRequest.getPayeeOrgType().equals(
				"" + OrgType.MEMBER.getValue())) {
			if (calFeeRequest.getPayee() == null
					|| calFeeRequest.getPayeeMemberAcctCode() == null
					|| calFeeRequest.getPayeeFullMemberAcctCode() == null) {
				logger.error("calFeeRequest.getPayee()==null || calFeeRequest.getPayeeMemberAcctCode()==null||calFeeRequest.getPayeeFullMemberAcctCode()==null  !");
				return false;
			}
			if (calFeeRequest.getPayee().equals(
					calFeeRequest.getPayeeMemberAcctCode())
					|| calFeeRequest.getPayee().equals(
							calFeeRequest.getPayeeFullMemberAcctCode())) {
				logger.error("calFeeRequest.getPayer().equals(calFeeRequest.getPayerMemberAcctCode())  || calFeeRequest.getPayer().equals(calFeeRequest.getPayerFullMemberAcctCode()) !");
			}

		} else if (calFeeRequest.getPayeeOrgType().equals(
				"" + OrgType.BANK.getValue())) {
			if (calFeeRequest.getPayeeOrgCode() == null) {
				logger.error("calFeeRequest.getPayeeOrgCode()==null    !");
				return false;
			}
		}

		/**
		 * 暂时屏蔽ServiceLevel
		 * */
		// calFeeRequest.setPayerServiceLevel(null);
		// calFeeRequest.setPayeeServiceLevel(null);
		return true;
	}

	@Override
	public CalFeeReponse calculateFeeDetail(CalFeeRequest calFeeRequest)
			throws PEBisnessRuntimeException {

		calFeeRequest.setPaymentServicePkgCode(null);
		if (!validRequest(calFeeRequest)) {
			logger.error("validRequest fail ! calFeeRequest=" + calFeeRequest);
			return null;
		}

		logger.info("calFeeRequest=" + calFeeRequest);
		beforeCalFee(calFeeRequest, null);

		if (isPreCall) {
			isPreCallFellAndPrice(calFeeRequest);
		}
		CalFeeReponse calFeeReponse = generateCalFeeReponse(calFeeRequest);
		afterCalFee(calFeeRequest, calFeeReponse);

		// 生成dealDto对象
		DealDto deal = dealService.generateDealDto(calFeeRequest);

		// 设置费用信息
		// deal.setHasCaculatedPrice(calFeeReponse.isHasCaculatedPrice());

		deal.setHasCaculatedPayeePrice(calFeeReponse.isHasCaculatedPayeePrice());
		deal.setHasCaculatedPayerPrice(calFeeReponse.isHasCaculatedPayerPrice());
		deal.setPayeeFee(calFeeReponse.getPayeeFee());
		deal.setPayerFee(calFeeReponse.getPayerFee());
		deal.setPriceStrategyCode(calFeeReponse.getPriceStrategyCode());
		deal.setPayeePriceStrategy(calFeeReponse.getPayeePriceStrategyCode());
		deal.setPayerPriceStrategy(calFeeReponse.getPayerPriceStrategyCode());
		// 保存deal信息
		try {
			deal = dealService.saveDealInIsolatedTx(deal);
		} catch (Exception e) {
			logger.error("calculateFeeDetail error:", e);
			if (e.getCause().getMessage().indexOf("ORA-00001") > 0) {
				this.logger.info("订单重复提交......");
			} else {
				// 保存失败
				this.logger.error("产生会计分录error: " + e);
				throw new PEBisnessRuntimeException(
						ErrorCodeType.SAVE_DEAL_ERROR);
			}
		}
		// 设置分录的信息
		List<CalFeeDetail> calFeeDetailList = calFeeReponse.getCalFeeDetails();
		for (CalFeeDetail detail : calFeeDetailList) {
			detail.setVouchercode(deal.getVoucherCode());
		}
		calFeeReponse.setCalFeeDetails(calFeeDetailList);
		calFeeReponse.setVoucherCode(deal.getVoucherCode());
		logger.info("calFeeReponse=" + calFeeReponse);
		return calFeeReponse;
	}

	/**
	 * 金额为负,认为是冲正 , 为了不影响系统,先转为正, 最后取反
	 * */
	private void beforeCalFee(CalFeeRequest calFeeRequest,
			CalFeeReponse calFeeReponse) {
		if (calFeeRequest.getAmount() < 0) {
			calFeeRequest.setReverse(true);
			calFeeRequest.setAmount(-calFeeRequest.getAmount());
			if (calFeeRequest.isHasCaculatedPayeePrice()) {
				if (calFeeRequest.getPayerFee() != null) {
					calFeeRequest.setPayerFee(calFeeRequest.getPayerFee() * -1);
				}
			}
			if (calFeeRequest.isHasCaculatedPayerPrice()) {
				if (calFeeRequest.getPayeeFee() != null) {
					calFeeRequest.setPayeeFee(calFeeRequest.getPayeeFee() * -1);
				}
			}
			// if(calFeeRequest.isHasCaculatedPrice()){
			// if(calFeeRequest.getPayerFee()!=null){
			// calFeeRequest.setPayerFee(calFeeRequest.getPayerFee()*-1);
			// }
			// if(calFeeRequest.getPayeeFee()!=null){
			// calFeeRequest.setPayeeFee(calFeeRequest.getPayeeFee()*-1);
			// }
			// }
		} else {
			calFeeRequest.setReverse(false);
		}

		// 为请求参数加上会员服务等级
		beforeCalFeeServiceLevel(calFeeRequest);
	}

	private JdbcTemplate infMaJdbcTemplate;
	private String serviceLevelSql = "select service_level_code from t_member where member_code = ?";

	/**
	 * 为请求参数加上会员服务等级
	 * */
	private void beforeCalFeeServiceLevel(CalFeeRequest calFeeRequest) {
		// if(calFeeRequest.getPayer()!=null &&
		// calFeeRequest.getPayer().length()==11 &&
		// calFeeRequest.getPayer().startsWith("1000") )
		// {
		if (calFeeRequest.getPayerOrgType() != null
				&& isMember(calFeeRequest.getPayerOrgType())
				&& calFeeRequest.getPayer() != null) {
			int serviceLevelCode = infMaJdbcTemplate.queryForInt(
					serviceLevelSql, new String[] { calFeeRequest.getPayer() });
			logger.info("calFeeRequest.getPayer()=" + calFeeRequest.getPayer()
					+ " serviceLevelCode=" + serviceLevelCode);
			logger.info("payer orderCode=" + calFeeRequest.getOrderCode()
					+ " old serviceLevelCode="
					+ calFeeRequest.getPayerServiceLevel()
					+ " new serviceLevelCode=" + serviceLevelCode
					+ "   dealCode=" + calFeeRequest.getDealCode()
					+ "   orderId" + calFeeRequest.getOrderId() + "  payer="
					+ calFeeRequest.getPayer());
			calFeeRequest.setPayerServiceLevel(serviceLevelCode);
			// if(calFeeRequest.getPayerFullMemberAcctCode().startsWith("200101")){
			// //企业
			// calFeeRequest.setPayerServiceLevel(200) ;
			// }else
			// if(calFeeRequest.getPayerFullMemberAcctCode().startsWith("200102")){//个人
			// calFeeRequest.setPayerServiceLevel(100) ;
			// }
		} else {
			calFeeRequest.setPayerServiceLevel(null);
		}

		// if(calFeeRequest.getPayee()!=null &&
		// calFeeRequest.getPayee().length()==11 &&
		// calFeeRequest.getPayee().startsWith("1000") )
		// {
		if (calFeeRequest.getPayeeOrgType() != null
				&& isMember(calFeeRequest.getPayeeOrgType())
				&& calFeeRequest.getPayee() != null) {
			int serviceLevelCode = infMaJdbcTemplate.queryForInt(
					serviceLevelSql, new String[] { calFeeRequest.getPayee() });
			logger.info("calFeeRequest.getPayee()=" + calFeeRequest.getPayee()
					+ " serviceLevelCode=" + serviceLevelCode);
			logger.info("payee orderCode=" + calFeeRequest.getOrderCode()
					+ " old serviceLevelCode="
					+ calFeeRequest.getPayeeServiceLevel()
					+ " new serviceLevelCode=" + serviceLevelCode
					+ "   dealCode=" + calFeeRequest.getDealCode()
					+ "   orderId" + calFeeRequest.getOrderId() + "  payee="
					+ calFeeRequest.getPayee());
			calFeeRequest.setPayeeServiceLevel(serviceLevelCode);
			// if(calFeeRequest.getPayeeFullMemberAcctCode().startsWith("200101")){//企业
			// calFeeRequest.setPayeeServiceLevel(200) ;
			// }else
			// if(calFeeRequest.getPayeeFullMemberAcctCode().startsWith("200102")){//个人
			// calFeeRequest.setPayeeServiceLevel(100) ;
			// }
		} else {
			calFeeRequest.setPayeeServiceLevel(null);
		}
	}

	/**
	 * 金额为负,认为是冲正 , 为了不影响系统,先转为正, 最后取反
	 * */
	private void afterCalFee(CalFeeRequest calFeeRequest,
			CalFeeReponse calFeeReponse) {
		if (calFeeReponse != null) {
			if (calFeeRequest.isReverse()) {
				calFeeRequest.setAmount(-calFeeRequest.getAmount());
				calFeeReponse.getCalFeeRequest().setAmount(
						-calFeeReponse.getCalFeeRequest().getAmount());
				// TODO 双向收费
				if (calFeeReponse.isHasCaculatedPayeePrice()) {
					calFeeReponse.setPayeeFee(-calFeeReponse.getPayeeFee());
				}
				if (calFeeReponse.isHasCaculatedPayeePrice()) {
					calFeeReponse.setPayerFee(-calFeeReponse.getPayerFee());
				}

				List<CalFeeDetail> calFeeDetails = calFeeReponse
						.getCalFeeDetails();
				if (calFeeDetails != null) {
					for (CalFeeDetail calFeeDetail : calFeeDetails) {
						calFeeDetail.setValue(-calFeeDetail.getValue());
					}
				}
			}
		}
		if (calFeeRequest.isReverse()) {
			calFeeRequest.setAmount(-calFeeRequest.getAmount());
		}
	}

	// 处理需要合并分录的请求，也就是需要 100 要分为95+5
	private void isPreCallFellAndPrice(CalFeeRequest calFeeRequest) {
		if (calFeeRequest.getPaymentServicePkgCode() == null) {
			assert calFeeRequest.getDealCode() != null;
			assert calFeeRequest.getOrderCode() != null;
			assert calFeeRequest.getPayMethod() != null;
			Integer pkg = this.paymentServiceService.getPymtpkgcodeByMatrix(
					calFeeRequest.getDealCode(), calFeeRequest.getOrderCode(),
					calFeeRequest.getPayMethod());
			assert pkg != null;
			calFeeRequest.setPaymentServicePkgCode(pkg);
		}
		Integer autoMergeFlag = this.paymentServiceService
				.getPaymentServicePKG(calFeeRequest.getPaymentServicePkgCode())
				.getAutoMergeEntries();

		if (null != autoMergeFlag && autoMergeFlag == 1) {
			// 请求已经算好费，认为不需要处理
			if (!calFeeRequest.isHasCaculatedPayeePrice()) {
				calFeeRequest.setPayeeFee(null);
				calFeeRequest.setPayerFee(null);

				CalFeeReponse calFeeReponse = this.caculateFee(calFeeRequest);
				// TODO
				// calFeeRequest.setHasCaculatedPrice(true);
				calFeeRequest.setPayerFee(calFeeReponse.getPayerFee());
				calFeeRequest.setPayeeFee(calFeeReponse.getPayeeFee());

				// update by ch-ma
				if (calFeeRequest.getPayerFee() != null) {
					// calFeeRequest.setAmount(calFeeRequest.getAmount()-calFeeRequest.getPayerFee());
				} else if (calFeeRequest.getPayeeFee() != null) {
					// calFeeRequest.setAmount(calFeeRequest.getAmount()-calFeeRequest.getPayeeFee());
				}
			}

		}

	}

	public CalFeeReponse generateCalFeeReponse(CalFeeRequest calFeeRequest)
			throws PEBisnessRuntimeException {

		CalFeeReponse calFeeReponse = new CalFeeReponse();
		PaymentRequest request = createPaymentRequest(calFeeRequest);
		if (null == request) {
			throw new IllegalArgumentException("Can not create PaymentRequest.");
		}

		/**
		 * 创建分录
		 */
		PaymentResponse response = payment.generateEntries(request);

		calFeeReponse.setPayerFee(response.getPayerFee());
		calFeeReponse.setPayeeFee(response.getPayeeFee());

		calFeeReponse.setHasCaculatedPayeePrice(response
				.isHasCaculatedPayeePrice());
		calFeeReponse.setHasCaculatedPayerPrice(response
				.isHasCaculatedPayerPrice());

		calFeeReponse.setPayerPriceStrategyCode(response
				.getPayerFeePriceStrategyCode());
		calFeeReponse.setPayeePriceStrategyCode(response
				.getPayeeFeePriceStrategyCode());

		List<CalFeeDetail> calFeeDetails = new ArrayList<CalFeeDetail>();
		calFeeReponse.setPriceStrategyCode(response.getPriceStrategyCode());
		List<AccountEntryDTO> entries = response.getEntries();

		long crTotal = 0L;
		long drTotal = 0L;
		for (AccountEntryDTO accountEntryDTO : entries) {
			CalFeeDetail detail = new CalFeeDetail();
			BeanUtils.copyProperties(accountEntryDTO, detail);
			if (detail.getCrdr().intValue() == 1) {
				drTotal = drTotal + detail.getValue();
			}
			if (detail.getCrdr().intValue() == 2) {
				crTotal = crTotal + detail.getValue();
			}

			// 设置ma账户 余额更新方向
			detail.setMaBlanceBy(getMaBalanceBy(calFeeRequest, accountEntryDTO));
			detail.setDealType(accountEntryDTO.getDealType());
			calFeeDetails.add(detail);
		}

		calFeeReponse.setCalFeeDetails(calFeeDetails);
		if (crTotal != drTotal) {
			logger.error("accountingCalFeeReponse crTotal = " + crTotal
					+ "  drTotal=  " + drTotal + " [crTotal != drTotal]  "
					+ calFeeReponse.toString());
			throw new PEBisnessRuntimeException(
					ErrorCodeType.DRTOTAL_CRTOTAL_ERROR);
		}
		calFeeReponse.setCalFeeRequest(calFeeRequest);
		return calFeeReponse;
	}

	private PaymentRequest createPaymentRequest(CalFeeRequest calFeeRequest) {
		PaymentRequest request = new PaymentRequest();
		// 金额 时间 dealid 服务包 终端类型 付款方fee 首款方fee 是否计费
		// amount
		request.setAmount(Money.rmb(calFeeRequest.getAmount()));//
		request.setDate(new MfDateTime(calFeeRequest.getRequestDate()));

		if (calFeeRequest.getPaymentServicePkgCode() == null) {
			assert calFeeRequest.getDealCode() != null;
			assert calFeeRequest.getOrderCode() != null;
			assert calFeeRequest.getPayMethod() != null;

			Integer pkg = this.paymentServiceService.getPymtpkgcodeByMatrix(
					calFeeRequest.getDealCode(), calFeeRequest.getOrderCode(),
					calFeeRequest.getPayMethod());
			request.setPaymentServicePkgCode(pkg);
			calFeeRequest.setPaymentServicePkgCode(pkg);

		} else {
			request.setPaymentServicePkgCode(calFeeRequest
					.getPaymentServicePkgCode());

		}
		// Integer autoMergeFlag =
		// this.paymentServiceService.getPaymentServicePKG(request.getPaymentServicePkgCode()).getAutoMergeEntries();

		request.setPaymentServicePkgCode(calFeeRequest
				.getPaymentServicePkgCode());
		request.setTerminalType(TerminalType.WEB.getValue());

		// 是否已经算过费了
		// request.setHasCaculatedPrice(calFeeRequest.isHasCaculatedPrice());
		request.setHasCaculatedPayeePrice(calFeeRequest
				.isHasCaculatedPayeePrice());
		request.setHasCaculatedPayerPrice(calFeeRequest
				.isHasCaculatedPayerPrice());

		request.setPayeeFee(calFeeRequest.getPayeeFee());
		request.setPayerFee(calFeeRequest.getPayerFee());

		// 因为deal中的payer/payee的orgCode/orgType可能为空，所以要从AcctSpec中取.
		Integer payeeOrgType = calFeeRequest.getPayeeOrgType();
		String payeeOrgCode = calFeeRequest.getPayeeOrgCode();

		if (calFeeRequest.getPayeeServiceLevel() != null)
			request.setPayeeServiceLevel(calFeeRequest.getPayeeServiceLevel());

		if (isMember(payeeOrgType)) {
			// payee account type:负债类
			// request.setPayeeAccountType(coa.getAcctType());
			request.setPayee(calFeeRequest.getPayee());
			request.setPayeeMemberAccCode(calFeeRequest
					.getPayeeMemberAcctCode());
			request.setPayeeFullMemberAcctCode(calFeeRequest
					.getPayeeFullMemberAcctCode());
			// request.setPayeeOrgCode(String.valueOf(payeeOrgCode));
			request.setPayeeOrgTypeCode(payeeOrgType);

		}
		request.setPayeeOrgCode(null == payeeOrgCode ? null : "" + payeeOrgCode);
		request.setPayeeOrgTypeCode(payeeOrgType);

		String payerMemberAcctCode = calFeeRequest.getPayerMemberAcctCode();
		Integer payerOrgType = calFeeRequest.getPayerOrgType();
		String payerOrgCode = calFeeRequest.getPayerOrgCode();

		if (calFeeRequest.getPayerServiceLevel() != null)
			request.setPayerServiceLevel(calFeeRequest.getPayerServiceLevel());
		if (isMember(payerOrgType)) {
			request.setPayer(calFeeRequest.getPayer());
			request.setPayerMemberAccCode(payerMemberAcctCode);
			request.setPayerFullMemberAcctCode(calFeeRequest
					.getPayerFullMemberAcctCode());
			request.setPayerOrgTypeCode(payerOrgType);

		}
		request.setPayerOrgCode(null == payerOrgCode ? null : "" + payerOrgCode);
		request.setPayerOrgTypeCode(payerOrgType);

		// 设置币种缩写，如果deal中没有设置币种则取默认币种
		String payerCurrencyCode = calFeeRequest.getPayerCurrencyCode() == null ? currencyService
				.getDefaultCurrencyCode() : calFeeRequest
				.getPayerCurrencyCode();
		String payeeCurrencyCode = calFeeRequest.getPayeeCurrencyCode() == null ? currencyService
				.getDefaultCurrencyCode() : calFeeRequest
				.getPayeeCurrencyCode();

		request.setPayerCurrencyCode(payerCurrencyCode);

		// TODO
		String payerCurrencyNum = currencyService
				.findCurrencyNumByCode(payerCurrencyCode);
		if (null == payerCurrencyNum) {
			payerCurrencyNum = "";
		}
		request.setPayerCurrencyNum(payerCurrencyNum);

		request.setPayeeCurrencyCode(payeeCurrencyCode);

		String payeeCurrencyNum = currencyService
				.findCurrencyNumByCode(payeeCurrencyCode);
		if (null == payeeCurrencyNum) {
			payeeCurrencyNum = "";
		}
		request.setPayeeCurrencyNum(payeeCurrencyNum);

		request.setExchangeRate(calFeeRequest.getExchangeRate());
		request.setPriceStrategyCode(calFeeRequest.getPriceStrategyCode());
		request.setBankCode(calFeeRequest.getBankCode());
		return request;
	}

	/**
	 * 判断付款方/收款方是否为个人或者企业会员.
	 * 
	 * @param partyOrgType
	 *            付款方/收款方的机构类型代码
	 * 
	 * @return boolean
	 */
	private boolean isMember(final Integer partyOrgType) {
		return OrgType.MEMBER.getValue() == partyOrgType;
	}

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PaymentServiceService getPaymentServiceService() {
		return paymentServiceService;
	}

	public void setPaymentServiceService(
			PaymentServiceService paymentServiceService) {
		this.paymentServiceService = paymentServiceService;
	}

	/**
	 * 只算费，不包括分录
	 * */
	public CalFeeReponse caculateFee(CalFeeRequest calFeeRequest) {

		CalFeeReponse calFeeReponse = new CalFeeReponse();
		// 初始化BILLING付款服务DTO
		if (calFeeRequest == null) {
			return null;
		}
		calFeeRequest.setPaymentServicePkgCode(null);
		logger.info("caculateFee calFeeRequest=" + calFeeRequest);
		beforeCalFee(calFeeRequest, null);

		if (calFeeRequest.getPaymentServicePkgCode() == null) {
			assert calFeeRequest.getDealCode() != null;
			assert calFeeRequest.getOrderCode() != null;
			assert calFeeRequest.getPayMethod() != null;
			Integer pkg = this.paymentServiceService.getPymtpkgcodeByMatrix(
					calFeeRequest.getDealCode(), calFeeRequest.getOrderCode(),
					calFeeRequest.getPayMethod());
			assert pkg != null;
			calFeeRequest.setPaymentServicePkgCode(pkg);
		}

		// calFeeReponse.setPayeeFee(0L);
		// calFeeReponse.setPayerFee(0L);

		List<PaymentServiceDTO> billPss = initPaymentServiceDto(calFeeRequest);
		if (null == billPss) {
			logger.info("BillPaymentServiceList is null");
			return calFeeReponse;
		}
		for (PaymentServiceDTO ps : billPss) {
			Integer dependOn = ps.getDependOn();
			// 如果没有作用方，刚直接
			if (null == dependOn) {
				continue;
			}
			// 创建记费对象.
			CalPricingStrategyParam para = new CalPricingStrategyParam();
			para.setTransactionAmount(calFeeRequest.getAmount());
			para.setTerminaltype(calFeeRequest.getTerminalType());
			para.setMfDatetime(calFeeRequest.getRequestDate());
			// para.setReservedCode(calFeeRequest.getReservedCode());
			// 根据支付服务中设置的记费作用方，设置对应的会员号和服务等级.
			if (dependOn.equals(Transactor.PAYEE.getValue())) {
				if (!StringUtil.isEmpty(calFeeRequest.getPayee())) {

					para.setMemberCode(Long.valueOf(calFeeRequest.getPayee()));
					para.setServiceLevelCode(calFeeRequest
							.getPayeeServiceLevel());
					// 设置RESERVEDCODE
					if (null != ps.getReservedCodeType()) {
						if (ps.getReservedCodeType() == RESERVEDCODETYPE.OPPOSINGACCTCODE
								.getValue())
							para.setReservedCode(calFeeRequest
									.getPayerMemberAcctCode());
						else
							para.setReservedCode(calFeeRequest
									.getPayerOrgCode());
					}
				}
			} else {
				if (!StringUtil.isEmpty(calFeeRequest.getPayer())) {
					// para.setMemberCode(Long.valueOf(request.getPayer()));
					para.setMemberCode(Long.valueOf(calFeeRequest.getPayer()));
					para.setServiceLevelCode(calFeeRequest
							.getPayerServiceLevel());
					// 设置RESERVEDCODE
					if (null != ps.getReservedCodeType()) {
						if (ps.getReservedCodeType() == RESERVEDCODETYPE.OPPOSINGACCTCODE
								.getValue())
							para.setReservedCode(calFeeRequest
									.getPayeeMemberAcctCode());
						else
							para.setReservedCode(calFeeRequest
									.getPayeeOrgCode());
					}
				}
			}
			para.setPaymentServiceCode(ps.getPaymentservicecode());
			CalPriceFeeResponse calCalPriceFeeResponse = getPricingService()
					.calculatePriceToResponse(para);
			// Long price = getPricingService().calculatePrice(para);
			Long price = calCalPriceFeeResponse.getFee();

			if (null != ps.getTakeon()) {
				if (ps.getTakeon().equals(Transactor.PAYEE.getValue())) {
					calFeeReponse
							.setPayeeFee((calFeeReponse.getPayeeFee() == null ? 0L
									: calFeeReponse.getPayeeFee())
									+ price);
					// 设置收款方价格策略CODE
					calFeeReponse
							.setPayeePriceStrategyCode(calCalPriceFeeResponse
									.getPriceStrategyCode());
					calFeeReponse.setHasCaculatedPayeePrice(true);
				} else {
					// 设 置付款方价格策略CODE
					calFeeReponse
							.setPayerPriceStrategyCode(calCalPriceFeeResponse
									.getPriceStrategyCode());
					calFeeReponse
							.setPayerFee((calFeeReponse.getPayerFee() == null ? 0L
									: calFeeReponse.getPayerFee())
									+ price);
					calFeeReponse.setHasCaculatedPayerPrice(true);

				}
				// calFeeReponse.setHasCaculatedPrice(Boolean.TRUE);
				// 设置计费价格策略明细ID
				// calFeeReponse.setPriceStrategyCode(calCalPriceFeeResponse.getPriceStrategyCode());
			}
		}

		afterCalFee(calFeeRequest, calFeeReponse);
		logger.info("caculateFee calFeeReponse=" + calFeeReponse);
		return calFeeReponse;
	}

	// 获取同一组的所有bill类型的服务列表
	private List<PaymentServiceDTO> initPaymentServiceDto(
			final CalFeeRequest calFeeRequest) {

		List<PaymentServiceDTO> result = paymentServiceService
				.getPaymentServiceDtos(
						calFeeRequest.getPaymentServicePkgCode(), 2);
		if (null == result || result.size() == 0) {
			return null;
		}
		return result;
	}

	public PricingStrategyService getPricingService() {
		return pricingService;
	}

	public void setPricingService(PricingStrategyService pricingService) {
		this.pricingService = pricingService;
	}

	public void setDealService(DealService dealService) {
		this.dealService = dealService;
	}

	public boolean isPreCall() {
		return isPreCall;
	}

	public void setPreCall(boolean isPreCall) {
		this.isPreCall = isPreCall;
	}

	// 得到ma更新余额， + - 方向， 1 + 2 -
	private int getMaBalanceBy(CalFeeRequest calFeeRequest,
			AccountEntryDTO accountEntryDTO) {
		return getAcctBalanceBy(accountEntryDTO.getAcctcode(),
				accountEntryDTO.getCrdr());
	}

	// 得到ma更新余额， + - 方向， 1 + 2 -
	public int getMaBalanceBy(String acctCode, Integer crdr) {
		return getAcctBalanceBy(acctCode, crdr);
	}

	/**
	 * 得到帐号余额方向
	 */
	private int getAcctBalanceBy(String acctCode, Integer crdr) {

		AcctSpecDTO acctSpecDTO = acctSpecService.getAcctSpec(acctCode);

		if (null != acctSpecDTO) {
			if (acctSpecDTO.getAcctType() == AcctType.ASSERT.getValue()) {
				if (CRDRType.DEBIT.getValue() == crdr) {
					return BalanceByEnum.ADD.getValue();
				} else {
					return BalanceByEnum.DIVE.getValue();
				}
			} else if (acctSpecDTO.getAcctType() == AcctType.LIABILITY
					.getValue()) {
				if (CRDRType.DEBIT.getValue() == crdr) {
					return BalanceByEnum.DIVE.getValue();
				} else {
					return BalanceByEnum.ADD.getValue();
				}
			} else if (acctSpecDTO.getAcctType() == AcctType.OWNERSEQUIRY
					.getValue()) {
				return BalanceByEnum.ADD.getValue();
			} else if (acctSpecDTO.getAcctType() == AcctType.LOSSANDPROFILT
					.getValue()) {
				// 损益类　成本　借方为增加
				if (CRDRType.DEBIT.getValue() == crdr) {
					if (acctSpecDTO.getBalanceBy() == CRDRType.DEBIT.getValue()) {
						return BalanceByEnum.ADD.getValue();
					} else {
						return BalanceByEnum.DIVE.getValue();
					}

				} else {
					if (acctSpecDTO.getBalanceBy() == CRDRType.CREDIT
							.getValue()) {
						return BalanceByEnum.ADD.getValue();
					} else {
						return BalanceByEnum.DIVE.getValue();
					}
				}
			} else if (acctSpecDTO.getAcctType() == AcctType.ASSERTLIABILITY
					.getValue()) {
				// 负责共同　借方为增加
				if (CRDRType.DEBIT.getValue() == crdr) {
					if (acctSpecDTO.getBalanceBy() == CRDRType.DEBIT.getValue()) {
						return BalanceByEnum.ADD.getValue();
					} else {
						return BalanceByEnum.DIVE.getValue();
					}

				} else {
					if (acctSpecDTO.getBalanceBy() == CRDRType.CREDIT
							.getValue()) {
						return BalanceByEnum.ADD.getValue();
					} else {
						return BalanceByEnum.DIVE.getValue();
					}
				}
			} else {
				throw new PEBisnessRuntimeException("get balance by had error");
			}
		} else {
			if (CRDRType.DEBIT.getValue() == crdr) {
				return BalanceByEnum.DIVE.getValue();
			} else {
				return BalanceByEnum.ADD.getValue();
			}
		}
	}

	public void setInfMaJdbcTemplate(JdbcTemplate infMaJdbcTemplate) {
		this.infMaJdbcTemplate = infMaJdbcTemplate;
	}

	public void setAcctSpecService(AcctSpecService acctSpecService) {
		this.acctSpecService = acctSpecService;
	}

}
