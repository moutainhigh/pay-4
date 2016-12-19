package com.pay.pe.service.payment;

import java.util.List;

import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.dto.PaymentServiceType;
import com.pay.pe.dto.PostingRuleDTO;
import com.pay.pe.helper.RESERVEDCODETYPE;
import com.pay.pe.helper.TakenOnEnum;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.pe.service.exchangerate.ExchangeRateService;
import com.pay.pe.service.payment.common.AccountingEvent;
import com.pay.pe.service.payment.common.PaymentRequest;
import com.pay.pe.service.payment.common.PaymentResponse;
import com.pay.pricingstrategy.service.CalPricingStrategyParam;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;
import com.pay.util.Money;
import com.pay.util.ObjectUtil;
import com.pay.util.StringUtil;

/**
 *  
 */
public abstract class AbstractPaymentService implements IPaymentService {
	
	private PricingStrategyService pricingService;
	/**
	 * 支付服务.
	 */
	private PaymentServiceDTO paymentServiceDTO;

	/**
	 * 计帐规则.
	 */
	private List<PostingRuleDTO> postingRules;
	private CurrencyService currencyService;

	private ExchangeRateService exchangeRateService;

	public ExchangeRateService getExchangeRateService() {
		return exchangeRateService;
	}

	public void setExchangeRateService(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	/**
	 * 计算费用.
	 * 
	 * @param request
	 *            <code>PaymnetRequet</code> object.
	 * @param para
	 *            <code>CalPricingStrategyParam</code> object. 计费条件.
	 * @return Money
	 */
	// protected abstract Money calculateAmount(
	// PaymentRequest request, CalPricingStrategyParam para);

	protected abstract CalPriceFeeResponse calculateAmount(
			PaymentRequest request, CalPricingStrategyParam para);

	/**
	 * @return Returns the pricingService.
	 */
	public PricingStrategyService getPricingService() {
		return pricingService;
	}

	/**
	 * @param pricingService
	 *            The pricingService to set.
	 */
	public void setPricingService(PricingStrategyService pricingService) {
		this.pricingService = pricingService;
	}

	/**
	 * @return Returns the paymentService.
	 */
	public final PaymentServiceDTO getPaymentServiceDTO() {
		return paymentServiceDTO;
	}

	/**
	 * @param service
	 *            The paymentService to set.
	 */
	public final void setPaymentServiceDTO(final PaymentServiceDTO service) {
		this.paymentServiceDTO = service;
	}

	/**
	 * @return Returns the postingRules.
	 */
	public final List<PostingRuleDTO> getPostingRules() {
		return postingRules;
	}

	/**
	 * @param rules
	 *            The postingRules to set.
	 */
	public final void setPostingRules(final List<PostingRuleDTO> rules) {
		this.postingRules = rules;
	}

	/**
	 * 处理支付请求，生成支付事仄1�7. <br>
	 * 根据不同的IPaymentService分别计费,并得到不同的计帐规则.
	 * 
	 * #process(.domain.service.payment.Payment)
	 * 
	 * @param payment
	 *            <code>Payment</code> object.
	 */
	public final void process(final Payment payment) {

		if (null == payment) {
			return;
		}
		PaymentRequest request = payment.getPaymentRequest();
		PaymentResponse response = payment.getPaymentResponse();

		// 创建记账事件.
		AccountingEvent event = initAccountingEvent(request);
		List rules = getPaymentServiceDTO().getPostingRuleCollectionDTO();
		event.setPostingRules(rules);
		event.setPayTime(request.getDate());
		PaymentServiceDTO ps = getPaymentServiceDTO();
		event.setDealType(ps.getPaymentServiceType());
		CalPriceFeeResponse calPriceFeeResponse = caculatePrice(request, ps);
		Money money = Money.rmb(calPriceFeeResponse.getFee());
		if (null != ps.getTakeon()) {
			if (ps.getTakeon() == TakenOnEnum.PAYEE.getCode()) {
				// 设置收款方价格策略CODE
				response.setPayeeFeePriceStrategyCode(calPriceFeeResponse
						.getPriceStrategyCode());
			} else {
				// 设 置付款方价格策略CODE
				response.setPayerFeePriceStrategyCode(calPriceFeeResponse
						.getPriceStrategyCode());
			}
		}
		event.setAmount(money);

		caculateFee(request, response, ps, money);

		payment.addAccountingEvent(event);
	}

	/**
	 * 初始化AccountingEvent对象.
	 * 
	 * @return AccountingEvent
	 */
	private AccountingEvent initAccountingEvent(final PaymentRequest request) {

		PaymentServiceDTO ps = getPaymentServiceDTO();
		AccountingEvent event = new AccountingEvent();

		event.setOrderCode(request.getOrderId());
		event.setPaymentServiceDTO(ps);
		event.setPayee(request.getPayee());
		event.setPayeeOrgCode(request.getPayeeOrgCode());
		event.setPayeeOrgTypeCode(request.getPayeeOrgTypeCode());
		event.setPayeeMemberAccCode(request.getPayeeMemberAccCode());
		event.setPayeeFullMemberAccCode(request.getPayeeFullMemberAcctCode());

		event.setPayer(request.getPayer());
		event.setPayerOrgCode(request.getPayerOrgCode());
		event.setPayerOrgTypeCode(request.getPayerOrgTypeCode());
		event.setPayerMemberAccCode(request.getPayerMemberAccCode());
		event.setPayerFullMemberAccCode(request.getPayerFullMemberAcctCode());
		// }

		event.setAccountingCurrencyCode(request.getPayerCurrencyCode());
		event.setAccountingCurrencyNum(request.getPayerCurrencyNum());

		String exchangeRate = "1";
		if (null != request.getExchangeRate()) {
			exchangeRate = String.valueOf(request.getExchangeRate());
		}
		event.setAccountingExchangeRate(ObjectUtil
				.formatStrExRate(exchangeRate));
		return event;
	}

	/**
	 * 计算价格.
	 * 
	 * @param request
	 *            PaymentRequest
	 * @param ps
	 *            PaymentServiceDTO
	 * @return Money
	 */
	private CalPriceFeeResponse caculatePrice(final PaymentRequest request,
			final PaymentServiceDTO ps) {

		// 只有计费服务才计算价栄1�7.
		if (PaymentServiceType.BILLING.getValue() != ps.getPaymentServiceType()) {
			return this.calculateAmount(request, null);
		}
		// 判断是否已经做过计价，如果已经做过计价则返回以前计算的费
		Boolean CaculatedPayeePrice = request.isHasCaculatedPayeePrice();
		Boolean CaculatedPayerPrice = request.isHasCaculatedPayerPrice();

		if (ps.getTakeon() == TakenOnEnum.PAYEE.getCode()) {
			if (CaculatedPayeePrice != null && CaculatedPayeePrice
					&& request.getPayeeFee() != null) {
				CalPriceFeeResponse calPriceFeeResponse = setCaculatedPriceStrategy(
						request, ps);
				calPriceFeeResponse.setFee(request.getPayeeFee().longValue());
				return calPriceFeeResponse;
			}
		} else if (ps.getTakeon() == TakenOnEnum.PAYER.getCode()) {
			if (CaculatedPayerPrice != null && CaculatedPayerPrice
					&& request.getPayerFee() != null) {
				CalPriceFeeResponse calPriceFeeResponse = setCaculatedPriceStrategy(
						request, ps);
				calPriceFeeResponse.setFee(request.getPayerFee().longValue());
				return calPriceFeeResponse;
			}
		} else {
			CalPriceFeeResponse calPriceFeeResponse = setCaculatedPriceStrategy(
					request, ps);
			if (request.getPayerFee() != null) {
				calPriceFeeResponse.setFee(request.getPayerFee().longValue());
				return calPriceFeeResponse;
			} else if (request.getPayeeFee() != null) {
				calPriceFeeResponse.setFee(request.getPayeeFee().longValue());
				return calPriceFeeResponse;
			}
		}

		CalPricingStrategyParam para = this.createCalPricingStrategyParam(
				request, ps);

		return this.calculateAmount(request, para);
	}

	private CalPriceFeeResponse setCaculatedPriceStrategy(
			final PaymentRequest request, final PaymentServiceDTO ps) {
		CalPriceFeeResponse calPriceFeeResponse = new CalPriceFeeResponse();

		/*
		//add by sch 2016-06-02 如果费用已经计算好了，则无需去获取计费策略 ，测试已经通过了 。
		//建议再判断一下获取的费用. 如果不判断费用的话，调用者务必把 setPayeeFee 或者  setPayerFee 设置上，
		Boolean CaculatedPayeePrice = request.isHasCaculatedPayeePrice();
		Boolean CaculatedPayerPrice = request.isHasCaculatedPayerPrice();
		if((CaculatedPayeePrice != null ) && 
			(CaculatedPayeePrice)  && 
			(CaculatedPayerPrice != null)  && 
			(CaculatedPayerPrice)){
			logger.info("isHasCaculatedPayeePrice no read priceStrategyCode ");
			return calPriceFeeResponse;
		}
		*/

		Long strCode = request.getPriceStrategyCode();
		Long priceStrategyCode = 0l;
		if (null != strCode && strCode > 0) {
			priceStrategyCode = request.getPriceStrategyCode();
		} else {
			CalPricingStrategyParam para = this.createCalPricingStrategyParam(
					request, ps);
			priceStrategyCode = pricingService.getPricingStrategyCode(para);
		}
		if (null != priceStrategyCode && priceStrategyCode > 0) {
			calPriceFeeResponse.setPriceStrategyCode(priceStrategyCode);
		}
		return calPriceFeeResponse;
	}

	CalPricingStrategyParam createCalPricingStrategyParam(
			final PaymentRequest request, final PaymentServiceDTO ps) {
		// 创建记费对象.
		CalPricingStrategyParam para = new CalPricingStrategyParam();
		para.setTransactionAmount(request.getAmount().getAmount());
		para.setTerminaltype(request.getTerminalType());
		para.setMfDatetime(request.getDate().getTime());
		para.setReservedCode(request.getReservedCode());

		Integer takeon = ps.getTakeon();
		// 根据支付服务中设置的记费作用方，设置对应的会员号和服务等级.
		if (null != takeon) {
			if (takeon == TakenOnEnum.PAYEE.getCode()) {
				if (!StringUtil.isEmpty(request.getPayee())) {
					// para.setMemberCode(Long.valueOf(request.getPayee()));
					para.setMemberCode(Long.valueOf(request.getPayee()));
					para.setServiceLevelCode(request.getPayeeServiceLevel());
					// 设置RESERVEDCODE
					if (null != ps.getReservedCodeType()) {
						if (ps.getReservedCodeType() == RESERVEDCODETYPE.OPPOSINGACCTCODE
								.getValue())
							para.setReservedCode(request
									.getPayerMemberAccCode());
						else
							para.setReservedCode(request.getPayerOrgCode());
					}
				}
			} else {
				if (!StringUtil.isEmpty(request.getPayer())) {
					// para.setMemberCode(Long.valueOf(request.getPayer()));
					para.setMemberCode(Long.valueOf(request.getPayer()));
					para.setServiceLevelCode(request.getPayerServiceLevel());
					// 设置RESERVEDCODE
					if (null != ps.getReservedCodeType()) {
						if (ps.getReservedCodeType() == RESERVEDCODETYPE.OPPOSINGACCTCODE
								.getValue())
							para.setReservedCode(request
									.getPayeeMemberAccCode());
						else
							para.setReservedCode(request.getPayeeOrgCode());
					}
				}
			}
		}
		para.setPaymentServiceCode(ps.getPaymentservicecode());
		return para;
	}

	/**
	 * 计算在收款方或付款方产生的费用和.
	 * 
	 * @param request
	 *            PaymentRequest
	 * @param response
	 *            PaymentResponse
	 * @param ps
	 *            PaymentServiceDTO
	 * @param amount
	 *            Money
	 */
	private void caculateFee(final PaymentRequest request,
			final PaymentResponse response, final PaymentServiceDTO ps,
			final Money amount) {
		// 只有计费服务才计算价栄1�7.
		if (PaymentServiceType.BILLING.getValue() != ps.getPaymentServiceType()) {
			return;
		}

		// 判断是否已经做过计价，如果已经做过计价则返回以前计算的价格
		// 问题 就是hasCaculatedPrice==true payeeFee payerFee 必须有个不能为空
		Boolean hasCaculatedPayeePrice = request.isHasCaculatedPayeePrice();
		Boolean hasCaculatedPayerPrice = request.isHasCaculatedPayerPrice();

		if (ps.getTakeon() == TakenOnEnum.PAYEE.getCode()) {
			if (hasCaculatedPayeePrice == null || !hasCaculatedPayeePrice) {
				Long payeeFee = response.getPayeeFee() == null ? 0L : response
						.getPayeeFee();
				response.setPayeeFee(amount.getAmount());
				response.setHasCaculatedPayeePrice(Boolean.TRUE);
			} else if (request.getPayeeFee() != null) {
				response.setPayeeFee(request.getPayeeFee());
				response.setHasCaculatedPayeePrice(Boolean.TRUE);
			}
		} else if (ps.getTakeon() == TakenOnEnum.PAYER.getCode()) {
			if (hasCaculatedPayerPrice == null || !hasCaculatedPayerPrice) {
				Long payerFee = response.getPayerFee() == null ? 0L : response
						.getPayerFee();
				response.setPayerFee(amount.getAmount());
				response.setHasCaculatedPayerPrice(Boolean.TRUE);
			} else if (request.getPayerFee() != null) {
				response.setPayerFee(request.getPayerFee());
				response.setHasCaculatedPayerPrice(Boolean.TRUE);
			}
		} else {
			Long payerFee = response.getPayerFee() == null ? 0L : response
					.getPayerFee();
			response.setPayerFee(payerFee + amount.getAmount());
			response.setHasCaculatedPayerPrice(Boolean.TRUE);
		}

	}
}
