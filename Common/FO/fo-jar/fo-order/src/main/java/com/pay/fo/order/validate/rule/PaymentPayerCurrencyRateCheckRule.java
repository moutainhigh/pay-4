/**
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fo.order.client.CurrencyRateService;
import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.inf.rule.MessageRule;

/**
 * 检测是否配置了商户手续费币种到出款账记币种的汇率
 * @author PengJiangbo
 *
 */
public class PaymentPayerCurrencyRateCheckRule extends MessageRule {
	
	private EnterpriseBaseService enterpriseBaseService ;
	private CurrencyRateService currencyRateService ;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(final Object validateBean) throws Exception {

		BatchPaymentRequest detailRequest = (BatchPaymentRequest) validateBean;
		Long memberCode = detailRequest.getMemberCode() ;
		String payerCurrencyCode = detailRequest.getPayerCurrencyCode() ;
		WithdrawUnionBatchpayFee unionFee = this.enterpriseBaseService.queryWithdrawUnionBatchByCode(memberCode) ;
		String batchpayFeeCurrency = unionFee.getBatchpayFeeCurrency() ;
		
		List<Map> listRate = this.recCurrencyRate(batchpayFeeCurrency, payerCurrencyCode, "1", Long.valueOf(memberCode)) ;
		if(CollectionUtils.isNotEmpty(listRate)){
			Map<String, Object> rateMap = listRate.get(0) ;
			String exchangeRate = (String) rateMap.get("exchangeRate") ;
			if(!StringUtils.isEmpty(exchangeRate)){
				return true ;
			}else{
				detailRequest.getPaymentResponse().setErrorMsg(getMessageId());
				return false ;
			}
		}else{
			//System.out.println(getMessageId());
			detailRequest.getBatchPaymentResponse().setErrorMsg(getMessageId());
			return false ;
		}
	}

	//获取当前商户清算费率配置
	private List<Map> recCurrencyRate(final String currency, final String targetCurrency, final String status, final Long memberCode){
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		/*paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);*/
		paraMap.put("status",status);
		Map<String, Object> rateMap = this.currencyRateService.settlementRateQuery(paraMap) ;
		List<Map> resultList = (List<Map>) rateMap.get("list") ;
		return resultList ;
	}
	
	public void setEnterpriseBaseService(final EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setCurrencyRateService(final CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}
	
	
}
