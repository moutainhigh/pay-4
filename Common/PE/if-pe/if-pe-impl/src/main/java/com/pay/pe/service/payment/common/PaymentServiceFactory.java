
package com.pay.pe.service.payment.common;

import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.pe.dto.PaymentServiceType;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.pe.service.exchangerate.ExchangeRateService;
import com.pay.pe.service.payment.IPaymentService;
import com.pay.pe.service.payment.impl.BillingPaymentService;
import com.pay.pe.service.payment.impl.DealPaymentService;


/**
 * 
 *
 */
public class PaymentServiceFactory {
    public static PaymentServiceFactory factory = new PaymentServiceFactory();
    private PricingStrategyService pricingService;
    private ExchangeRateService exchangeRateService;
    private CurrencyService currencyService;
    
    public static PaymentServiceFactory getFactory() {
        return factory;
    }
    
    /**
     * Default constructor.
     *
     */
    private PaymentServiceFactory() {
    }

    
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
     * @return Returns the pricingService.
     */
    public PricingStrategyService getPricingService() {
        return pricingService;
    }

    /**
     * @param pricingService The pricingService to set.
     */
    public void setPricingService(PricingStrategyService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * IPaymentService工厂类.
     * @param paymentService 支付服务对象
     * @return IPaymentService
     */
    public IPaymentService createPaymentService(
            final PaymentServiceDTO paymentService) {
        if (null == paymentService) {
            return null;
        }
        int type = paymentService.getPaymentServiceType();
        if (PaymentServiceType.TRANSACTION.getValue() == type) {
            return createDealPaymentService(paymentService);
        } else if (PaymentServiceType.BILLING.getValue() == type) {
            return createBillingPaymentService(paymentService);
        } 
        return null;
    }

    /**
     * 创建一个DealPaymentService对象.
     * @param paymentService <code>PaymentService</code> object.
     * @return IPaymentService
     */
    public IPaymentService createDealPaymentService(
            final PaymentServiceDTO paymentService) {
        DealPaymentService service = new DealPaymentService();
        service.setPaymentServiceDTO(paymentService);
        service.setPostingRules(paymentService.getPostingRuleCollectionDTO());
        service.setPricingService(getPricingService());
        service.setCurrencyService(getCurrencyService());
        service.setExchangeRateService(getExchangeRateService());
        return service;
    }

    /**
     * 创建一个BillingPaymentService对象.
     * @param paymentService <code>PaymentService</code> object.
     * @return IPaymentService
     */
    public IPaymentService createBillingPaymentService(
            final PaymentServiceDTO paymentService) {
        BillingPaymentService service = new BillingPaymentService();
        service.setPaymentServiceDTO(paymentService);
        service.setPostingRules(paymentService.getPostingRuleCollectionDTO());
        service.setPricingService(getPricingService());
        service.setCurrencyService(getCurrencyService());
        service.setExchangeRateService(getExchangeRateService());
        return service;
    }

   
}
