package com.pay.risk.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.service.ValidateService;
import com.pay.risk.client.AccountingClientService;
import com.pay.risk.dto.PaymentInfo;
import com.pay.risk.dto.PaymentResult;
import com.pay.risk.service.RiskValidateService;
import com.pay.rm.service.model.RiskOrder;
import com.pay.util.StringUtil;


/**
 * 风控校验
 * @author peiyu
 * @since  2016年6月30日10:26:13
 */
public class RiskValidateServiceImpl implements RiskValidateService {
	private static Logger logger = LoggerFactory.getLogger(RiskValidateServiceImpl.class);
	private ValidateService riskValidateService;
	private EnterpriseBaseService enterpriseBaseService;
	private AccountingClientService  clientService;
	private BaseDAO<RiskOrder> riskOrderDAO;
	

	@Override
	public Map<String, String> validate(PaymentInfo paymentInfo){
		Map<String,String> resultMap = new HashMap<String, String>();
		if(paymentInfo==null){
			resultMap.put("desc","ACCEPT");
			resultMap.put("respCode","9999");
			resultMap.put("respMsg","参数不正确");
			return resultMap;
		}
		

		Map<String,String> respMap = new HashMap<String, String>();;
		
		PaymentResult paymentResult = new PaymentResult();
		paymentInfo.setPaymentResult(paymentResult);
		
		try {
			 //检查是否需要生产风控收费订单
			 calcRiskFee(paymentInfo);
			 riskValidateService.validate(paymentInfo);
		} catch (Exception e) {
			 e.printStackTrace();
			 respMap.put("desc","ACCEPT");
			 respMap.put("respMsg",""+e.getMessage());

			 return respMap;

		}
		
		String respCode = paymentResult.getResponseCode();
		String respMsg = paymentResult.getResponseDesc();
		
		respMap.put("respCode",respCode);
		respMap.put("respMsg",respMsg);
		
		logger.info("respCode: "+respCode+" ,respMsg: "+respMsg);
		if("0052".equals(respCode)||"0053".equals(respMsg)){
			respMsg="Risk Order:风险订单";
			respMap.put("respMsg",respMsg);
		}
		if("3200".equals(respCode)){
			respMap.put("desc","ACCEPT");
		}else{
			respMap.put("desc","REJECT");
		}
		return respMap;
	}
	
	/**
	 * 检查并创建
	 * @param params
	 */
	private void calcRiskFee(PaymentInfo paymentInfo) {
		try{
			//计算风控手续费
			EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
					.queryEnterpriseBaseByMemberCode(Long.valueOf(paymentInfo.getPartnerId()));
			
			String riskFeeStr = enterpriseBaseDto.getRiskFee();
			
			logger.info("beforecreateriskorder-"+paymentInfo.getTradeOrderNo()+", cardorg="+ paymentInfo.getCardOrg());
			
			//peiyu.yang 2016年4月27日10:08:56 新增，如果没有配置风控手续费，或者风控手续费配置成0.不生产风控手续费收费订单
			if(StringUtil.isEmpty(riskFeeStr)||Double.valueOf(riskFeeStr)<=0.0){
				return;
			}
			long riskFee = 0;
			
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("sourceCurrency",enterpriseBaseDto.getRiskFeeCurrency());// enterpriseBaseDto.getFeeCurrencyCode()//使用配置的风控处理费币种 modify by davis.guo at 2016-08-18
			paraMap.put("type","2");
			paraMap.put("memberCode",paymentInfo.getPartnerId());
			paraMap.put("targetCurrency",paymentInfo.getSettlementCurrencyCode());
			paraMap.put("status","1");
			if (!StringUtils.isEmpty(paymentInfo.getCardOrg())) {
				paraMap.put("cardOrg", paymentInfo.getCardOrg());
			}
			paraMap.put("ltaCurrencyCode", "CNY");
			paraMap.put("point", getTime());
			paraMap.put("orderAmount",riskFeeStr);
			
			Map<String,Object> respMap=clientService.getNewSSettlementRate(paraMap);
			String exchangeRate=null;
			
		 
			
			if(!StringUtil.isEmpty(riskFeeStr)&&respMap!=null&&respMap.get("exchangeRate")!=null){
				exchangeRate = (String) respMap.get("exchangeRate");
				riskFee = new BigDecimal(riskFeeStr).multiply(new BigDecimal(exchangeRate))
						.multiply(new BigDecimal("1000")).longValue();
						 
			}
			logger.info("aftergetsettlementrate-"+exchangeRate+",riskfee="+riskFee );
			
			RiskOrder riskOrder = new RiskOrder();
			riskOrder.setCreateDate(new Date());
			riskOrder.setMerchantOrderId(paymentInfo.getOrderId());
			riskOrder.setExchangeRate(exchangeRate);
			riskOrder.setFeeAmount(riskFeeStr);
			riskOrder.setFeeCurrencyCode(enterpriseBaseDto.getRiskFeeCurrency());// enterpriseBaseDto.getFeeCurrencyCode()//使用配置的风控处理费币种 modify by davis.guo at 2016-08-18
			riskOrder.setFeeFlg(0);
			riskOrder.setPartnerId(Long.valueOf(paymentInfo.getPartnerId()));
			riskOrder.setSettlementAmount(riskFee);
			riskOrder.setSettlementCurrencyCode(paymentInfo.getSettlementCurrencyCode());
			riskOrder.setTradeOrderNo(Long.valueOf(paymentInfo.getTradeOrderNo()));
			riskOrder.setUpdateDate(new Date());
			riskOrderDAO.create(riskOrder);
		}catch(Exception e){
			logger.error("save riskOrder error:" + paymentInfo.getTradeOrderNo(),e);
		}
	}

	public void setClientService(AccountingClientService clientService) {
		this.clientService = clientService;
	}
	
	public void setRiskOrderDAO(BaseDAO<RiskOrder> riskOrderDAO) {
		this.riskOrderDAO = riskOrderDAO;
	}

	public void setRiskValidateService(ValidateService riskValidateService) {
		this.riskValidateService = riskValidateService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}	
	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}
}

