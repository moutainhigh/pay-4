/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fo.order.client.CurrencyRateService;
import com.pay.fo.order.service.batchpay2bank.ConfigurationCalcuFeeService;

/**
 * @author PengJiangbo
 *
 */
public class ConfigurationCalcuFeeServiceImpl implements
		ConfigurationCalcuFeeService {
	
	private static final Log logger = LogFactory.getLog(ConfigurationCalcuFeeServiceImpl.class) ;
	
	//注入
	private EnterpriseBaseService enterpriseBaseService ;
	private CurrencyRateService currencyRateService ;
	
	@Override
	public Long calcuFee(final Long memberCode, final String payerCurrencyCode) {
		//String errorMsg = null ;
		WithdrawUnionBatchpayFee unionFee = this.recFee(Long.valueOf(memberCode)) ;
		String withdrawFeeCurrency = unionFee.getWithdrawFeeCurrency() ;
		Long withdrawFee = unionFee.getWithdrawFee() ;
		String currencyCode = payerCurrencyCode ; //command.getCurrencyCode() ;
		Long payerFee = 0L ;
		if(StringUtils.isNotEmpty(withdrawFeeCurrency) && (null != withdrawFee && 0 != withdrawFee)){
			if(withdrawFeeCurrency.equals(currencyCode)){
				payerFee = withdrawFee ;
			}else{
				List<Map> listRate = this.recCurrencyRate(withdrawFeeCurrency, currencyCode, "1", Long.valueOf(memberCode)) ;
				if(CollectionUtils.isNotEmpty(listRate)){
					Map<String, Object> rateMap = listRate.get(0) ;
					String exchangeRate = (String) rateMap.get("exchangeRate") ;
					if(logger.isInfoEnabled()){
						logger.info("设置币种的手续费(单位：厘)[withdrawFee]为："
								+ unionFee.getWithdrawFee() + "  "
								+ unionFee.getWithdrawFeeCurrency()
								+ ",当前商户有效清算费率[exchangeRate]为:" + exchangeRate);
					}
					payerFee = (long) (Double.valueOf(exchangeRate) * unionFee.getWithdrawFee()) ;
					System.out.println("对应的提现手续费为(单位：厘)：" + payerFee);
				}else{
					//errorMsg = "没有找到汇率，请联系支付运营人员！" ;
					if(logger.isInfoEnabled()){
						logger.info("没有找到对应的清算汇率！");
					}
				}
				
			}
		}else{
			//errorMsg = "未设置提现手续费，请联系支付运营人员！" ;
		}
		return payerFee;
	}

	//获取后台给商户配置的提现手续费
	private WithdrawUnionBatchpayFee recFee(final Long memberCode){
		WithdrawUnionBatchpayFee withdrawUnionBatchpayFee = this.enterpriseBaseService.queryWithdrawUnionBatchByCode(memberCode) ;
		return withdrawUnionBatchpayFee ;
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
