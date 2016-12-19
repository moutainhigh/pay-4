package com.pay.pe.service.payment.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.pe.dao.BankOrgCodeMappingDAO;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.dto.DealDto;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.dto.PaymentServicePkgAssignmentDTO;
import com.pay.pe.dto.PostingRuleDTO;
import com.pay.pe.exception.ErrorCodeType;
import com.pay.pe.exception.PEBisnessRuntimeException;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.pe.service.exchangerate.ExchangeRateService;
import com.pay.pe.service.payment.IPaymentService;
import com.pay.pe.service.payment.Payment;
import com.pay.pe.service.payment.common.AccountingEvent;
import com.pay.pe.service.payment.common.LogUtil;
import com.pay.pe.service.payment.common.PaymentDto;
import com.pay.pe.service.payment.common.PaymentRequest;
import com.pay.pe.service.payment.common.PaymentResponse;
import com.pay.pe.service.payment.common.PaymentServiceFactory;
import com.pay.pe.service.payment.posting.PostingService;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pricingstrategy.service.PricingStrategyService;

/**
 *  
 * 
 * 
 */
public final class PaymentImpl implements Payment {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 与当前记帐线程相关的变量.
	 */
	private static ThreadLocal<PaymentDto> threadVar = new ThreadLocal<PaymentDto>() {
		protected synchronized PaymentDto initialValue() {
			return new PaymentDto();
		}
	};

	private BankOrgCodeMappingDAO bankOrgCodeMappingDAO;

	/**
	 * 价格策略服务.
	 */
	private PricingStrategyService pricingService;

	/**
	 * 支付服务服务.
	 */
	private PaymentServiceService paymentService;

	/**
	 * 记帐服务.
	 */
	private PostingService postingService;

	/**
	 * 汇率服务
	 */
	private ExchangeRateService exchangeRateService;

	/**
	 * 货币服务
	 */
	private CurrencyService currencyService;

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public ExchangeRateService getExchangeRateService() {
		return exchangeRateService;
	}

	public void setExchangeRateService(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	/**
	 * @return Returns the paymentService.
	 */
	public PaymentServiceService getPaymentService() {
		return paymentService;
	}

	/**
	 * @param paymentService
	 *            The paymentService to set.
	 */
	public void setPaymentService(PaymentServiceService paymentService) {
		this.paymentService = paymentService;
	}

	public void setBankOrgCodeMappingDAO(
			BankOrgCodeMappingDAO bankOrgCodeMappingDAO) {
		this.bankOrgCodeMappingDAO = bankOrgCodeMappingDAO;
	}

	/**
	 * @return Returns the postingService.
	 */
	public PostingService getPostingService() {
		return postingService;
	}

	/**
	 * @param postingService
	 *            The postingService to set.
	 */
	public void setPostingService(PostingService postingService) {
		this.postingService = postingService;
	}

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
	 * 得到当前记帐线程的变量.
	 * 
	 * @return PaymentDto
	 */
	public PaymentDto getPaymentDto() {
		return (PaymentDto) threadVar.get();
	}

	/**
	 * 增加一个支付处理事件.
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 */
	public void addAccountingEvent(final AccountingEvent accountingEvent) {
		getPaymentDto().addResultEvent(accountingEvent);
	}

	/**
	 * 增加一个帐目分录.
	 * 
	 * @param entry
	 *            <code>AccountEntry</code> object.
	 */
	public void addAccountingEntry(final AccountEntryDTO entry) {
		getPaymentDto().addResultEntry(entry);
	}

	/**
	 * 得到支付请求的参数.
	 * 
	 * #getPaymentRequest()
	 * 
	 * @return PaymentRequest
	 */
	public PaymentRequest getPaymentRequest() {
		return getPaymentDto().getPaymentRequest();
	}

	public BankOrgCodeMappingDAO getBankOrgCodeMappingDAO() {
		return bankOrgCodeMappingDAO;
	}

	/*
	 * (non-Javadoc)
	 */
	public PaymentResponse getPaymentResponse() {
		return getPaymentDto().getPaymentResponse();
	}

	/**
	 * Set paymentRequest.
	 * 
	 * @param request
	 *            <code>PaymentRequest</code> object.
	 */
	public void setPaymentRequest(final PaymentRequest request) {
		getPaymentDto().setPaymentRequest(request);
	}

	/**
	 * Set paymentResponse.
	 * 
	 * @param response
	 *            PaymentResponse
	 */
	public void setPaymentResponse(final PaymentResponse response) {
		getPaymentDto().setPaymentResponse(response);
	}

	@SuppressWarnings("unchecked")
	private List<AccountEntryDTO> generateEntries(final DealDto deal,
			final PaymentRequest request) throws PEBisnessRuntimeException {
		Map mainIden = new HashMap();
		mainIden.put("DealCode", deal.getDealCode());
		Map addition = new HashMap();
		addition.put("Deal", deal);
		logger.info(LogUtil.wrapLog("generateEntries", "Start",
				deal.getDealId(), mainIden, addition, null, null, null));

		List<AccountEntryDTO> result = null;
		PaymentResponse response = new PaymentResponse();
		// 清理变量
		getPaymentDto().clean();
		setPaymentRequest(request);
		setPaymentResponse(response);
		Integer paymentServicePkgCode = getPaymentDto().getPaymentRequest()
				.getPaymentServicePkgCode();
		boolean isMergeEntry = false;
		// 1表示自动合并分录
		Integer autoMergeFlag = getPaymentService().getPaymentServicePKG(
				paymentServicePkgCode).getAutoMergeEntries();
		if (null != autoMergeFlag && 1 == autoMergeFlag) {
			isMergeEntry = true;
		}

		List<IPaymentService> services = processPaymentServices(request,
				paymentServicePkgCode);

		processAccountEvents(services);

		// 对所有的需要处理的支付事件，生成相应的帐户分录.
		Iterator<AccountingEvent> it = getPaymentDto().getResultEvents()
				.iterator();
		while (it.hasNext()) {
			AccountingEvent event = it.next();
			if (null != event) {
				// 生成记帐分录记录.
				event.process(this);
			}
		}
		// 分录 entry_code ,1 ， 2 ， 3 ，4 序号这里产生
		processEntries(isMergeEntry);

		deal.setPayeeFee(response.getPayeeFee());
		deal.setPayerFee(response.getPayerFee());
		// TODO修改，双向收费
		deal.setHasCaculatedPayeePrice(response.isHasCaculatedPayeePrice());
		deal.setHasCaculatedPayerPrice(response.isHasCaculatedPayerPrice());

		result = getPaymentDto().getResultEntries();
		// 清理变量
		getPaymentDto().clean();

		logger.info(LogUtil.wrapLog("generateEntries", "Success",
				deal.getDealId(), null, null, null, null, null));
		mainIden = null;
		addition = null;

		return result;
	}

	/**
	 * 根据支付服务组处理支付服务.
	 * 
	 * @param request
	 * @param paymentServicePkgCode
	 * @throws PaymentException
	 */
	@SuppressWarnings("unchecked")
	private List<IPaymentService> processPaymentServices(
			final PaymentRequest request, final Integer paymentServicePkgCode)
			throws PEBisnessRuntimeException {
		Map mainIden = new HashMap();
		Map addition = new HashMap();
		addition.put("paymentServicePkgCode", paymentServicePkgCode);
		logger.info(LogUtil.wrapLog("processPaymentServices", "Start", null,
				mainIden, addition, null, null, null));

		// 根据此次支付所使用的支付服务组，得到所有的支付服务.
		List<PaymentServicePkgAssignmentDTO> paymentServices = getPaymentService()
				.getAllByPKGCode(paymentServicePkgCode);
		if (null == paymentServices || paymentServices.size() == 0) {
			logger.info(LogUtil.wrapLog("processPaymentServices", "Fail", null,
					mainIden, addition,
					"Can not find the payment services for the package", null,
					"Can not find the payment services for the package"));

			throw new PEBisnessRuntimeException(
					ErrorCodeType.PAYMENTSERVICES_NOT_FOUND,
					"Can not find the payment services for the package "
							+ paymentServicePkgCode);
			// throw new
			// PaymentException("Can not find the payment services for the package "
			// + paymentServicePkgCode);
		}

		// 对每一个支付服务，将其转化为对应的支付处理对象，然后计算金额，得到需要处理的支付事件.
		PaymentServiceFactory factory = PaymentServiceFactory.getFactory();
		factory.setPricingService(getPricingService());
		factory.setExchangeRateService(getExchangeRateService());
		factory.setCurrencyService(getCurrencyService());

		// 为了让各IPaymentService的执行顺序是按Deal, Bill, Costing的顺序执行，先把生成
		// 的IPaymentService放到List中，再按顺序执行.
		List<IPaymentService> services = new ArrayList<IPaymentService>();
		Iterator<PaymentServicePkgAssignmentDTO> it = paymentServices
				.iterator();
		while (it.hasNext()) {
			PaymentServicePkgAssignmentDTO temp = it.next();
			if (null != temp) {
				PaymentServiceDTO paymentServiceDto = getPaymentService()
						.getPaymentService(temp.getPaymentService());
				// PaymentService model =
				// paymentService.getModel(String.valueOf(temp.getPaymentService()));

				List<PostingRuleDTO> postingRulesDtos = getPaymentService()
						.getAllPostingRuleByPCode(temp.getPaymentService(),
								request.getDate());

				paymentServiceDto.setPostingRuleCollectionDTO(postingRulesDtos);

				IPaymentService service = factory
						.createPaymentService(paymentServiceDto);
				services.add(service);
			}
		}

		logger.info(LogUtil.wrapLog("processPaymentServices", "Success", null,
				mainIden, addition, null, null, null));

		return services;
	}

	/**
	 * 处理记账.
	 * 
	 * @param services
	 * @throws PaymentException
	 */
	private void processAccountEvents(final List<IPaymentService> services) {
		logger.info(LogUtil.wrapLog("processAccountEvents", "Start", null,
				null, null, null, null, null));

		// 按顺序生成需要处理的支付事件
		// TODO deal 累计扣费不生产deal会计分录
		generateAccountEvent(services, DealPaymentService.class);

		// billing
		generateAccountEvent(services, BillingPaymentService.class);

		if (null == getPaymentDto().getResultEvents()
				|| getPaymentDto().getResultEvents().size() == 0) {
			logger.info(LogUtil.wrapLog("processAccountEvents", "Fail", null,
					null, null, "No payment event generated.", null,
					"No payment event generated."));

			throw new IllegalArgumentException("No payment event generated.");
		}

		logger.info(LogUtil.wrapLog("processAccountEvents", "Success", null,
				null, null, null, null, null));
	}

	/**
	 * 处理分录.
	 * 
	 * @param isMergeEntry
	 * @return
	 */
	private List<AccountEntryDTO> processEntries(final boolean isMergeEntry) {
		List<AccountEntryDTO> result = new ArrayList<AccountEntryDTO>();
		// 重新设置entry code，使entry code不重复
		List<AccountEntryDTO> entries = getPaymentDto().getResultEntries();
		if (isMergeEntry) {
			entries = this.mergeEntry(entries);
		}
		Iterator<AccountEntryDTO> itEntry = entries.iterator();
		int entryCode = 1;
		while (itEntry.hasNext()) {
			AccountEntryDTO entry = itEntry.next();
			entry.setEntrycode(entryCode);
			// 如果分录金额为0,则不记分录.
			if (0 != entry.getValue()) {
				result.add(entry);
				entryCode++;
			}
		}
		getPaymentDto().setResultEntries(result);

		// Added by Kevin Xiong: 对分录按照账号进行，防止发生死锁
		sortEntries(result);
		return result;
	}

	/**
	 * 此方法只会在process方法中调用，不需要加synchronized.
	 * 
	 * @param paymentServices
	 *            IPaymentService
	 * @param type
	 *            Class
	 *            CostingPaymentService,BillingPaymentService,DealPaymentService
	 */
	private void generateAccountEvent(List<IPaymentService> paymentServices,
			Class<?> type) {
		if (null == paymentServices) {
			return;
		}
		// costing billing deal
		for (IPaymentService service : paymentServices) {
			if (null != service && type.isAssignableFrom(service.getClass())) {
				service.process(this);
			}
		}
	}

	/**
	 * 合并分录.
	 * 
	 */
	protected List<AccountEntryDTO> mergeEntry(
			List<AccountEntryDTO> resultingEntries) {
		logger.info(LogUtil.wrapLog("mergeEntry", "Start", null, null, null,
				null, null, null));

		// 对于空resultingEntries不进行任何合并处理
		if (null == resultingEntries || resultingEntries.size() == 0) {
			logger.info(LogUtil.wrapLog("mergeEntry", "Success", null, null,
					null, null, null, null));

			return resultingEntries;
		}
		Map<String, AccountEntryDTO> tempMap = new HashMap<String, AccountEntryDTO>();
		List<AccountEntryDTO> mergedEntryList = new ArrayList<AccountEntryDTO>();
		long tempAmount = 0;
		for (AccountEntryDTO newEntry : resultingEntries) {
			AccountEntryDTO orgEntry = (AccountEntryDTO) tempMap.get(newEntry
					.getAcctcode());
			// tempMap中 orgEntry 为空,表示对应accoutCode的分录第一次出现
			if (null == orgEntry) {
				tempMap.put(newEntry.getAcctcode(), newEntry);
			}
			// 否则合并新分录到原来的分录
			else {
				// 借贷方向相同时金额相加
				if (newEntry.getCrdr().longValue() == orgEntry.getCrdr()
						.longValue()) {
					tempAmount = newEntry.getValue() + orgEntry.getValue();
					newEntry.setValue(tempAmount);
				} else {
					tempAmount = newEntry.getValue() - orgEntry.getValue();
					// 汇总金额小于0,取orgEntry的借贷方向
					if (tempAmount < 0) {
						newEntry.setCrdr(orgEntry.getCrdr());
					}
					newEntry.setValue(Math.abs(tempAmount));
				}
				// 汇总金额为不为0时,才记录这笔分录
				if (tempAmount != 0) {
					tempMap.put(newEntry.getAcctcode(), newEntry);
				} else {
					// 汇总金额为0，从Map中去掉这笔分录
					tempMap.remove(newEntry.getAcctcode());
				}
			}
		}
		mergedEntryList.addAll(tempMap.values());
		resultingEntries.clear();
		resultingEntries.addAll(mergedEntryList);

		logger.info(LogUtil.wrapLog("mergeEntry", "Success", null, null, null,
				null, null, null));

		return resultingEntries;
	}

	/**
	 * 对分录按账号进行排序.
	 * 
	 */
	private static void sortEntries(List<AccountEntryDTO> entries) {
		Collections.sort(entries, new Comparator<AccountEntryDTO>() {
			public int compare(AccountEntryDTO o1, AccountEntryDTO o2) {
				return o1.getAcctcode().compareTo(o2.getAcctcode());
			}
		});
	}

	@Override
	public PaymentResponse generateEntries(PaymentRequest request)
			throws PEBisnessRuntimeException {

		List<AccountEntryDTO> result = null;
		PaymentResponse response = new PaymentResponse();

		// 设置费率
		response.setPayerFee(request.getPayerFee());
		response.setPayeeFee(request.getPayeeFee());
		response.setHasCaculatedPayerPrice(request.isHasCaculatedPayerPrice());
		response.setHasCaculatedPayeePrice(request.isHasCaculatedPayeePrice());

		// 清理变量
		getPaymentDto().clean();
		setPaymentRequest(request);
		setPaymentResponse(response);
		Integer paymentServicePkgCode = getPaymentDto().getPaymentRequest()
				.getPaymentServicePkgCode();
		boolean isMergeEntry = false;
		// 1表示自动合并分录
		Integer autoMergeFlag = getPaymentService().getPaymentServicePKG(
				paymentServicePkgCode).getAutoMergeEntries();
		if (null != autoMergeFlag && 1 == autoMergeFlag) {
			isMergeEntry = true;
		}

		List<IPaymentService> services = processPaymentServices(request,
				paymentServicePkgCode);

		// 更加支付服务 产生事件
		processAccountEvents(services);

		// 对所有的需要处理的支付事件，生成相应的帐户分录.
		Iterator<AccountingEvent> it = getPaymentDto().getResultEvents()
				.iterator();
		while (it.hasNext()) {
			AccountingEvent event = it.next();
			if (null != event) {
				event.setBankCode(request.getBankCode());
				// 生成记帐分录记录.
				event.process(this);
			}
		}
		// 分录 entry_code ,1 ， 2 ， 3 ，4 序号这里产生
		processEntries(isMergeEntry);

		result = getPaymentDto().getResultEntries();

		PaymentResponse response2 = new PaymentResponse();
		BeanUtils.copyProperties(response, response2);
		response2.setEntries(result);
		// 清理变量
		getPaymentDto().clean();
		return response2;
	}
}
