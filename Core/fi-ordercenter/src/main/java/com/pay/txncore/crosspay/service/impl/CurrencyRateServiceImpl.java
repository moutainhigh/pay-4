package com.pay.txncore.crosspay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.SettlementBaseRateService;
import com.pay.txncore.crosspay.service.SettlementRateAdjustService;
import com.pay.txncore.crosspay.service.SettlementRateService;
import com.pay.txncore.crosspay.service.TransRateMarkupService;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.crosspay.service.TransactionRateAdjustService;
import com.pay.txncore.crosspay.service.TransactionRateService;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.SettlementRateAdjust;
import com.pay.txncore.model.TransRateMarkup;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.txncore.model.TransactionRate;
import com.pay.txncore.model.TransactionRateAdjust;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;

/**
 * 汇率相关处理中间服务
 * 为了解决相互依赖问题
 * @author peiyu.yang
 *
 */
public class CurrencyRateServiceImpl implements CurrencyRateService {
	
	private static Logger logger = LoggerFactory.getLogger(CurrencyRateServiceImpl.class);
	
	private TransactionBaseRateService transactionBaseRateService;
	private TransactionRateService transactionRateService;
	private TransactionRateAdjustService transactionRateAdjustService;
	
	private SettlementBaseRateService settlementBaseRateService;
	private SettlementRateService settlementRateService;
	private SettlementRateAdjustService settlementRateAdjustService;
	private TransRateMarkupService transRateMarkupService;
	
	
	public void createTransactionBaseRate(List<TransactionBaseRate> baseRates) {
		 if(baseRates!=null&&baseRates.size()>0){
			 List<TransactionRate> rateList = new ArrayList<TransactionRate>();

			 for(TransactionBaseRate tbr:baseRates){
				 TransactionRateAdjust adjust = new TransactionRateAdjust();
				 adjust.setCurrency(tbr.getCurrency());
				 adjust.setTargetCurrency(tbr.getTargetCurrency());
				 adjust.setStatus("1");
				 
				 List<TransactionRateAdjust> list = transactionRateAdjustService.findByCriteria(adjust);
				 
				 if(list!=null&&list.size()>0){
					 for(TransactionRateAdjust at:list){
							 String adjustRate = at.getAdjustRate();
							 
							 String rateStr = this.getRate(tbr.getExchangeRate(), adjustRate);
							 
							 TransactionRate rate = new TransactionRate();
							 rate.setCurrency(tbr.getCurrency());
							 rate.setTargetCurrency(tbr.getTargetCurrency());
							 rate.setExchangeRate(rateStr);
							 rate.setCreateDate(new Date());
							 rate.setStartPoint(at.getStartPoint());
							 rate.setEndPoint(at.getEndPoint());
							 rate.setEffectDate(at.getEffectDate());
							 rate.setExpireDate(at.getExpireDate());
							 rate.setStatus(tbr.getStatus());
							 rate.setCurrencyUnit(tbr.getCurrencyUnit());
							 rate.setOperator(tbr.getOperator());
							 rate.setMemberCode(at.getMemberCode());
							 rate.setCardOrg(at.getCardOrg());
							 rate.setLeastTransAmount(at.getLeastTransAmount());
							 rate.setStartPoint(at.getStartPoint());
							 rate.setEndPoint(at.getEndPoint());
							 rate.setLtaCurrencyCode(at.getLtaCurrencyCode());
							 
							 rateList.add(rate);
					 }
					  
				 }else{
	
					 //String [] vs = {"VISA","MASTER","JCB"};		//modify by sch 2016-05-10
					 String [] vs = {"VISA","MASTER","JCB","AE"};

					 for(String org:vs){
						 TransactionRate rateV = new TransactionRate();
						 rateV.setCurrency(tbr.getCurrency());
						 rateV.setTargetCurrency(tbr.getTargetCurrency());
						 rateV.setExchangeRate(tbr.getExchangeRate());
						 rateV.setCreateDate(new Date());
						 rateV.setEffectDate(tbr.getEffectDate());
						 rateV.setExpireDate(tbr.getExpireDate());
						 rateV.setStatus(tbr.getStatus());
						 rateV.setCurrencyUnit(tbr.getCurrencyUnit());
						 rateV.setOperator(tbr.getOperator());
						 rateV.setMemberCode("0");
						 rateV.setStartPoint(0);
						 rateV.setEndPoint(24);
						 rateV.setLeastTransAmount(0.0);
						 rateV.setCardOrg(org);
						 rateV.setLtaCurrencyCode(tbr.getCurrency());
						 rateList.add(rateV);
					 }
				 }
			 }
			 
			 if(rateList.size()>0){
				 transactionRateService.batchCreate(rateList);
			 }
			 
			 transactionBaseRateService.batchCreate(baseRates);
		 }
	}
	
	
	public void createSettlementBaseRate(List<SettlementBaseRate> baseRates) {
		 if(baseRates!=null&&baseRates.size()>0){
			 List<SettlementRate> rateList = new ArrayList<SettlementRate>();

			 for(SettlementBaseRate tbr:baseRates){
				 SettlementRateAdjust adjust = new SettlementRateAdjust();
				 adjust.setCurrency(tbr.getCurrency());
				 adjust.setTargetCurrency(tbr.getTargetCurrency());
				 adjust.setStatus("1");
				 
				 List<SettlementRateAdjust> list = settlementRateAdjustService.findByCriteria(adjust);
				 
				 if(list!=null&&list.size()>0){
					 for(SettlementRateAdjust at:list){
							 String adjustRate = at.getAdjustRate();
							 String rateStr = this.getRate(tbr.getExchangeRate(), adjustRate);
							 
							 SettlementRate rate = new SettlementRate();
							 rate.setCurrency(tbr.getCurrency());
							 rate.setTargetCurrency(tbr.getTargetCurrency());
							 rate.setExchangeRate(rateStr);
							 rate.setCreateDate(new Date());
							 rate.setStartPoint(at.getStartPoint());
							 rate.setEndPoint(at.getEndPoint());
							 rate.setEffectDate(tbr.getEffectDate());
							 rate.setExpireDate(tbr.getExpireDate());
							 rate.setStatus(tbr.getStatus());
							 rate.setCurrencyUnit(tbr.getCurrencyUnit());
							 rate.setOperator(tbr.getOperator());
							 rate.setMemberCode(at.getMemberCode());
							 rate.setCardOrg(at.getCardOrg());
							 rate.setLeastTransAmount(at.getLeastTransAmount());
							 rate.setStartPoint(at.getStartPoint());
							 rate.setEndPoint(at.getEndPoint());
							 rate.setLtaCurrencyCode(at.getLtaCurrencyCode());
							 
							 rateList.add(rate);
					 }
					  
				 }else{
					 
					 //String [] vs = {"VISA","MASTER","JCB"};		//modify by sch 2016-05-10
					 String [] vs = {"VISA","MASTER","JCB","AE"};
					 
					 for(String org:vs){
						 SettlementRate rateV = new SettlementRate();
						 rateV.setCurrency(tbr.getCurrency());
						 rateV.setTargetCurrency(tbr.getTargetCurrency());
						 rateV.setExchangeRate(tbr.getExchangeRate());
						 rateV.setCreateDate(new Date());
						 rateV.setEffectDate(tbr.getEffectDate());
						 rateV.setExpireDate(tbr.getExpireDate());
						 rateV.setStatus(tbr.getStatus());
						 rateV.setCurrencyUnit(tbr.getCurrencyUnit());
						 rateV.setOperator(tbr.getOperator());
						 rateV.setMemberCode("0");
						 rateV.setCardOrg(org);
						 rateV.setStartPoint(0);
						 rateV.setEndPoint(23);
						 rateV.setLeastTransAmount(0.0);
						 rateV.setLtaCurrencyCode(tbr.getCurrency());
						 
						 rateList.add(rateV);
					 }
				 }
			 }
			 
			 if(rateList.size()>0){
				 settlementRateService.batchCreate(rateList);
			 }
			 
			 settlementBaseRateService.batchCreate(baseRates);
		 }
	}
	
	
	/**
	 * 创建交易基本汇率
	 */
	public void createTransactionBaseRate1(List<TransactionBaseRate> baseRates) {
		 if(baseRates!=null&&baseRates.size()>0){
			 TransactionRateAdjust rateAdjust = new TransactionRateAdjust();
			 rateAdjust.setStatus("1");//取生效的微调值
			 List<TransactionRateAdjust>  adjusts = transactionRateAdjustService.findByCriteria(rateAdjust);
			 Map<String,TransactionRateAdjust> map = new HashMap<String, TransactionRateAdjust>();
			 if(adjusts!=null&&adjusts.size()>0){
				 for(TransactionRateAdjust tra:adjusts){
					 String key = ""+tra.getCurrency()+"-"+tra.getTargetCurrency()+"-"+tra.getMemberCode();
					 map.put(key, tra);
				 }
			 }
			 List<TransactionRate> rateList = new ArrayList<TransactionRate>();

			 for(TransactionBaseRate tbr:baseRates){
				  TransactionRate rate = new TransactionRate();
                  rate.setCurrency(tbr.getCurrency());
                  rate.setTargetCurrency(tbr.getTargetCurrency());
                  rate.setStatus(tbr.getStatus());
                  
                  List<TransactionRate> list = transactionRateService.findByCriteria(rate);
                       
                  if(list!=null&&list.size()>0){
                       for(int i=0;i<list.size()-1;i++){
                    	   TransactionRate tr = list.get(i);
                    	   String key =""+tr.getCurrency()+"-"+tr.getTargetCurrency()+"-"+tr.getMemberCode();
                    	   TransactionRateAdjust tra_ = map.get(key);
                    	   
                    	   if(tra_!=null){
                    		   String adjustRate = tra_.getAdjustRate();
                    		   String newRate = this.getRate(tbr.getExchangeRate(),
                    				   adjustRate);
                    		   tr.setExchangeRate(newRate);
                    	   }else{
                    		   tr.setExchangeRate(tbr.getExchangeRate());
                    	   }
                    	   
                    	   tr.setCreateDate(new Date());
                    	   tr.setCurrencyUnit(tbr.getCurrencyUnit());
                    	   tr.setOperator(tbr.getOperator());
                    	   tr.setEffectDate(tbr.getEffectDate());
                    	   tr.setExpireDate(tbr.getExpireDate());
                    	   
                    	   list.set(i, tr);
                       }
                       
                       transactionRateService.batchCreate(list);
                  }else{
                	  String key =""+tbr.getCurrency()+"-"+tbr.getTargetCurrency()+"-0";
                	  TransactionRateAdjust tra_ = map.get(key);
                	  
                	  if(tra_!=null){
	               		   String adjustRate = tra_.getAdjustRate();
	               		   String newRate = this.getRate(tbr.getExchangeRate(),
	               				   adjustRate);
	               		   rate.setExchangeRate(newRate);
                	  }else{
	               		   rate.setExchangeRate(tbr.getExchangeRate());
                	  }
                	  
                	  rate.setCreateDate(new Date());
                	  rate.setCurrencyUnit(tbr.getCurrencyUnit());
                	  rate.setEffectDate(tbr.getEffectDate());
                	  rate.setExpireDate(tbr.getExpireDate());
                	  rate.setMemberCode("0");
                	  rate.setOperator(tbr.getOperator());
                	  
                	  rateList.add(rate);
                  } 
			 }
			 ///
			 
			 if(rateList.size()>0){
				 transactionRateService.batchCreate(rateList);
			 }
			 
			 transactionBaseRateService.batchCreate(baseRates);
		 }
	}
	
	
	private String getRate(String sourceRate,String adjustRate){
		
		BigDecimal rate_ = new BigDecimal(sourceRate);
		BigDecimal change = new BigDecimal(adjustRate);
		BigDecimal tmp = new BigDecimal("0");
		
		tmp = rate_.add(rate_.multiply(change.multiply(new BigDecimal("0.01"))));
		double rateTmp = tmp.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(rateTmp);
	}
	
	@Override
	public void createTransactionRate(List<TransactionRate> baseRates) {
	     
	}
	
	@Override
	public void createTransactionRateAdjust(
			List<TransactionRateAdjust> baseRateAdjusts) {
            
	}
	
	
	
	/**
	 * 查询交易汇率
	 * 先查找商户定制汇率
	 * 如果没有取公用汇率
	 */
	@Override
	public TransactionRate getTransactionRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,
			Date currentDate) {
		
		/*if (sourceCurrency.equals(targetCurrency)) {
			TransactionRate rate = new TransactionRate();
			rate.setExchangeRate("1");
			return rate;
		}*/
		
		logger.info("methodName: getTransactionRate(sourceCurrency: "
		           +sourceCurrency+",targetCurrency: "+targetCurrency+" ,status: "+status
		           +",memberCode: "+memberCode+",currentDate: "+currentDate+")");
		
		TransactionRate rate = transactionRateService.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency, status,memberCode, currentDate);
		if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
			
			//----------------------------
			if(CurrencyCodeEnum.CNY.getCode().equals(targetCurrency)){
				rate = transactionRateService.findCurrentCurrencyRate(sourceCurrency, 
						CurrencyCodeEnum.CNY.getCode(), status,"0", currentDate);
				if(rate!=null&&!StringUtil.isEmpty(rate.getExchangeRate())){
					return rate;
				}
				
				logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 CNY的汇率,member_code: 0");
				return null;
			}
			
			
			rate = transactionRateService.findCurrentCurrencyRate(sourceCurrency, 
					CurrencyCodeEnum.CNY.getCode(), status, memberCode, currentDate);
			
			TransactionRate settRate = transactionRateService.findCurrentCurrencyRate(targetCurrency, 
					CurrencyCodeEnum.CNY.getCode(), status, memberCode, currentDate);
			
			if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
					settRate==null||StringUtil.isEmpty(settRate.getExchangeRate())){
				
				rate = transactionRateService.findCurrentCurrencyRate(sourceCurrency, 
						targetCurrency, status,  "0", currentDate);
				
				if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
					rate = transactionRateService.findCurrentCurrencyRate(sourceCurrency, 
							CurrencyCodeEnum.CNY.getCode(), status,"0", currentDate);
					
					settRate = transactionRateService.findCurrentCurrencyRate(targetCurrency, 
							CurrencyCodeEnum.CNY.getCode(), status,"0", currentDate);
					
					if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
							settRate==null||StringUtil.isEmpty(settRate.getExchangeRate())){
						logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 "+targetCurrency
								     +"的汇率,member_code: "+memberCode);
						return null;
					}
					
				}else{
					return rate;
				}
			}
			
			BigDecimal rate0 = new BigDecimal(rate.getExchangeRate());
			BigDecimal rate1 = new BigDecimal(settRate.getExchangeRate());
				
			BigDecimal rate3 = rate0.divide(rate1, 7, BigDecimal.ROUND_HALF_UP);
				
			TransactionRate rate4 = new TransactionRate();
			rate4.setExchangeRate(rate3.toString());
			rate4.setCurrency(sourceCurrency);
			rate4.setTargetCurrency(targetCurrency);
			rate4.setMemberCode(memberCode);
			rate4.setStatus("1");
				
			return rate4;
			//-----------------------------
		}
		
		return rate;
	}
	
	
	/**
	 * 查询交易汇率
	 * 先查找商户定制汇率
	 * 如果没有取公用汇率
	 */
	@Override
	public TransactionRate getBoncedTransactionRate(Map<String, Object> params,
			String currencyCode, String targetCurrencyCode,Date currentDate) {
		TransactionRate transRate = new TransactionRate();
		TransRateMarkup markup=null;
		String status = null ;
		if(null !=params.get("status"))
		{
			status=params.get("status").toString();
		}
		
		TransactionBaseRate baseRate = this.
				findTransactionBaseRate(currencyCode,targetCurrencyCode,status,currentDate);
		if(baseRate==null){
			throw new BusinessException("transactionRate not exists",
					ExceptionCodeEnum.OTHER_ERROR);
		}
		TransactionBaseRate baseRate_ = this
				.findTransactionBaseRate(currencyCode,"USD",null, currentDate);
		String orderAmount = (String) params.get("orderAmount");
		
		String isIgnoreMarkup = (String) params.get("isIgnoreMarkup");//add by Mack to control ignore Markup
		
		if (!isIgnoreMarkup.equals("1")) //add by Mack to control ignore markup
		{
			if(baseRate_!=null){
				  BigDecimal rate0 = new BigDecimal(baseRate_.getExchangeRate());
				  //比较数据库中是实际金额，所有要除于1000
				  BigDecimal amount = new BigDecimal(orderAmount).divide(new BigDecimal("1000") ).multiply(rate0);
				  params.put("leastTransAmount",amount);
			}
			markup = transRateMarkupService.getTransRateMarkup(params);
			logger.info("markup:"+markup);
		}
		if(markup!=null){
			BigDecimal rateTmp = new BigDecimal(baseRate.getExchangeRate());
			BigDecimal rate = rateTmp.add(rateTmp.multiply(new BigDecimal(markup.getMarkup())
			             .multiply(new BigDecimal("0.01"))));
			transRate.setExchangeRate(rate.toString());
		}else{
			transRate.setExchangeRate(baseRate.getExchangeRate());
		}
		return transRate;
	}
	/**
	 * 查询交易汇率
	 * 先查找商户定制汇率
	 * 如果没有取公用汇率
	 */
	@Override
	public TransactionRate getNewTransactionRate(Map<String,Object> param) {
		
		/*if (sourceCurrency.equals(targetCurrency)) {
			TransactionRate rate = new TransactionRate();
			rate.setExchangeRate("1");
			return rate;
		}*/
		
		String sourceCurrency = (String) param.get("sourceCurrency");
		String targetCurrency = (String) param.get("targetCurrency");
		String status = (String) param.get("status");
		String memberCode  = (String) param.get("memberCode");
		Date currentDate = (Date) param.get("currentDate");
		String cardOrg = (String) param.get("cardOrg");
		String orderAmount = (String)param.get("orderAmount");
		double point = (Double) param.get("point");
		String ltaCurrencyCode = (String) param.get("ltaCurrencyCode");
		
		logger.info("methodName: getNewTransactionRate,sourceCurrency: "
		           +sourceCurrency+",targetCurrency: "+targetCurrency+" ,status: "+status
		           +",memberCode: "+memberCode+",currentDate: "+currentDate
		           		+",ltaCurrencyCode: "+ltaCurrencyCode+",cardOrg: "+cardOrg+" ,point: "+point);
		
		TransactionBaseRate baseRate = transactionBaseRateService.findCurrentCurrencyRate
				(sourceCurrency,ltaCurrencyCode, status, currentDate);
		
		logger.info("baseRate: "+baseRate+",orderAmount: "+orderAmount);
		
		Map<String,Object> paraMap = new HashMap<String, Object>();
		if(baseRate!=null){
			BigDecimal rate_ = new BigDecimal(orderAmount)
			                      .multiply(new BigDecimal(baseRate.getExchangeRate()))
			                      .multiply(new BigDecimal("0.001"));
			paraMap.put("leastTransAmount",rate_.toString());
			logger.info("leastTransAmount: "+rate_.toString());
		}
		
		
		
		paraMap.put("status",status);
		paraMap.put("cardOrg",cardOrg);
		paraMap.put("point",point);
		paraMap.put("memberCode",memberCode);
		paraMap.put("sourceCurrency",sourceCurrency);
		paraMap.put("targetCurrency",targetCurrency);
		if(currentDate!=null){
			paraMap.put("currentDate",currentDate);
		}

		TransactionRate rate = transactionRateService.findCurrencyRate(paraMap);
		
		if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
			
			if(CurrencyCodeEnum.CNY.getCode().equals(targetCurrency)){
				paraMap.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
				paraMap.put("memberCode","0");

				rate = transactionRateService.findCurrencyRate(paraMap);
				
				if(rate!=null&&!StringUtil.isEmpty(rate.getExchangeRate())){
					return rate;
				}
				logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 CNY的汇率,member_code: 0");
				return null;
			}

			paraMap.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
			rate = transactionRateService.findCurrencyRate(paraMap);
			
			paraMap.put("sourceCurrency",targetCurrency);			
			TransactionRate transRate = transactionRateService.findCurrencyRate(paraMap);
			
			if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
					transRate==null||StringUtil.isEmpty(transRate.getExchangeRate())){
				
				paraMap.put("sourceCurrency",sourceCurrency);
				paraMap.put("targetCurrency",targetCurrency);
				paraMap.put("memberCode","0");
				
				rate = transactionRateService.findCurrencyRate(paraMap);
				
				if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
					
					paraMap.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
					rate = transactionRateService.findCurrencyRate(paraMap);
					
					
					paraMap.put("sourceCurrency",targetCurrency);
					transRate = transactionRateService.findCurrencyRate(paraMap);
					
					if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
							transRate==null||StringUtil.isEmpty(transRate.getExchangeRate())){
						logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 "+targetCurrency
								     +"的汇率,member_code: "+memberCode);
						return null;
					}
					
				}else{
					return rate;
				}
			}
			
			BigDecimal rate0 = new BigDecimal(rate.getExchangeRate());
			BigDecimal rate1 = new BigDecimal(transRate.getExchangeRate());
				
			BigDecimal rate3 = rate0.divide(rate1, 7, BigDecimal.ROUND_HALF_UP);
				
			TransactionRate rate4 = new TransactionRate();
			rate4.setExchangeRate(rate3.toString());
			rate4.setCurrency(sourceCurrency);
			rate4.setTargetCurrency(targetCurrency);
			rate4.setMemberCode(memberCode);
			rate4.setStatus("1");
				
			return rate4;
			//-----------------------------
		}
		
		return rate;
	}
	
	
	/**
	 * 查询清算汇率
	 * 先查找商户定制汇率
	 * 如果没有取公用汇率
	 */
	@Override
	public SettlementRate getNewSettlementRate(Map<String,Object> param) {
		
		/*if (sourceCurrency.equals(targetCurrency)) {
			TransactionRate rate = new TransactionRate();
			rate.setExchangeRate("1");
			return rate;
		}*/
		
		String sourceCurrency = (String) param.get("sourceCurrency");
		String targetCurrency = (String) param.get("targetCurrency");
		String status = (String) param.get("status");
		String memberCode  = (String) param.get("memberCode");
		Date currentDate = (Date) param.get("currentDate");
		String cardOrg = (String) param.get("cardOrg");
		Long orderAmount = (Long)param.get("orderAmount");
		double point = (Double) param.get("point");
		String ltaCurrencyCode = (String) param.get("ltaCurrencyCode");
		
		logger.info("methodName: getNewSettlementRate,sourceCurrency: "
		           +sourceCurrency+",targetCurrency: "+targetCurrency+" ,status: "+status
		           +",memberCode: "+memberCode+",currentDate: "+currentDate
		           		+",ltaCurrencyCode: "+ltaCurrencyCode+",cardOrg: "+cardOrg+" ,point: "+point);
		
		SettlementBaseRate baseRate = settlementBaseRateService.findCurrentCurrencyRate
				(sourceCurrency,ltaCurrencyCode, status, currentDate);
		
		Map<String,Object> paraMap = new HashMap<String, Object>();
		if(baseRate!=null){
			BigDecimal rate_ = new BigDecimal(orderAmount)
			                      .multiply(new BigDecimal(baseRate.getExchangeRate()))
			                      .multiply(new BigDecimal("0.001"));
			paraMap.put("leastTransAmount",rate_);
			
			logger.info("methodName: getSettlementRate,sourceCurrency: "
			           +sourceCurrency+",targetCurrency: "+targetCurrency+" ,status: "+status
			           +",memberCode: "+memberCode+",currentDate: "+currentDate+",leastTransAmount:"
			           		+ " "+rate_.toString()+",ltaCurrencyCode: "+ltaCurrencyCode+",");
		}
		
		paraMap.put("status",status);
		paraMap.put("cardOrg",cardOrg);
		paraMap.put("point",point);
		paraMap.put("memberCode",memberCode);
		paraMap.put("sourceCurrency",sourceCurrency);
		paraMap.put("targetCurrency",targetCurrency);
		if(currentDate!=null){
			paraMap.put("currentDate",currentDate);
		}
		
		SettlementRate rate = settlementRateService.findCurrencyRate(paraMap);
		
		if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
			
			if(CurrencyCodeEnum.CNY.getCode().equals(targetCurrency)){
				paraMap.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
				paraMap.put("memberCode","0");

				rate = settlementRateService.findCurrencyRate(paraMap);
				
				if(rate!=null&&!StringUtil.isEmpty(rate.getExchangeRate())){
					return rate;
				}
				logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 CNY的汇率,member_code: 0");
				return null;
			}

			paraMap.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
			rate = settlementRateService.findCurrencyRate(paraMap);
			
			paraMap.put("sourceCurrency",targetCurrency);			
			SettlementRate settlementRate = settlementRateService.findCurrencyRate(paraMap);
			
			if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
					settlementRate==null||StringUtil.isEmpty(settlementRate.getExchangeRate())){
				
				paraMap.put("sourceCurrency",sourceCurrency);
				paraMap.put("targetCurrency",targetCurrency);
				paraMap.put("memberCode","0");
				
				rate = settlementRateService.findCurrencyRate(paraMap);
				
				if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
					
					paraMap.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
					rate = settlementRateService.findCurrencyRate(paraMap);
					
					paraMap.put("sourceCurrency",targetCurrency);
					settlementRate = settlementRateService.findCurrencyRate(paraMap);
					
					if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
							settlementRate==null||StringUtil.isEmpty(settlementRate.getExchangeRate())){
						logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 "+targetCurrency
								     +"的汇率,member_code: "+memberCode);
						return null;
					}
					
				}else{
					return rate;
				}
			}
			
			BigDecimal rate0 = new BigDecimal(rate.getExchangeRate());
			BigDecimal rate1 = new BigDecimal(settlementRate.getExchangeRate());
				
			BigDecimal rate3 = rate0.divide(rate1, 7, BigDecimal.ROUND_HALF_UP);
				
			SettlementRate rate4 = new SettlementRate();
			rate4.setExchangeRate(rate3.toString());
			rate4.setCurrency(sourceCurrency);
			rate4.setTargetCurrency(targetCurrency);
			rate4.setMemberCode(memberCode);
			rate4.setStatus("1");
				
			return rate4;
			//-----------------------------
		}
		
		return rate;
	}
	
	public static void main(String[] args) {
		BigDecimal rate0 = new BigDecimal("1.767");
		BigDecimal rate1 = new BigDecimal("6.1468");
		
		BigDecimal rate3 = rate0.divide(rate1, 7, BigDecimal.ROUND_HALF_UP);
        
		System.out.println("rate3: "+rate3.toString());
	}
	

	/**
	 * 查询交易汇率
	 * 先查找商户定制汇率
	 * 如果没有取公用汇率
	 */
	@Override
	public SettlementRate getSettlementRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,
			Date currentDate) {
		
		/*if (sourceCurrency.equals(targetCurrency)) {
			SettlementRate rate = new SettlementRate();
			rate.setExchangeRate("1");
			return rate;
		}*/
		
		logger.info("methodName: getSettlementRate(sourceCurrency: "
		           +sourceCurrency+",targetCurrency: "+targetCurrency+" ,status: "+status
		           +",memberCode: "+memberCode+",currentDate: "+currentDate+")");
		
		SettlementRate rate = settlementRateService.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency, status,memberCode, currentDate);
		if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){//如果商户没有定制的、就查找共有的
			
			//----------------------------
			if(CurrencyCodeEnum.CNY.getCode().equals(targetCurrency)){
				rate = settlementRateService.findCurrentCurrencyRate(sourceCurrency, 
						CurrencyCodeEnum.CNY.getCode(), status,"0", currentDate);
				if(rate!=null&&!StringUtil.isEmpty(rate.getExchangeRate())){
					return rate;
				}
				
				logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 CNY的汇率,member_code: 0");
				return null;
			}
			
			
			rate = settlementRateService.findCurrentCurrencyRate(sourceCurrency, 
					CurrencyCodeEnum.CNY.getCode(), status, memberCode, currentDate);
			
			SettlementRate settRate = settlementRateService.findCurrentCurrencyRate(targetCurrency, 
					CurrencyCodeEnum.CNY.getCode(), status, memberCode, currentDate);
			
			if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
					settRate==null||StringUtil.isEmpty(settRate.getExchangeRate())){
				
				rate = settlementRateService.findCurrentCurrencyRate(sourceCurrency, 
						targetCurrency, status,  "0", currentDate);
				
				if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
					rate = settlementRateService.findCurrentCurrencyRate(sourceCurrency, 
							CurrencyCodeEnum.CNY.getCode(), status,"0", currentDate);
					
					settRate = settlementRateService.findCurrentCurrencyRate(targetCurrency, 
							CurrencyCodeEnum.CNY.getCode(), status,"0", currentDate);
					
					if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())||
							settRate==null||StringUtil.isEmpty(settRate.getExchangeRate())){
						logger.error("未获取到 ：sourceCurrency: "+sourceCurrency+" 到 "+targetCurrency
								     +"的汇率,member_code: "+memberCode);
						return null;
					}
					
				}else{
					return rate;
				}
			}
			
			BigDecimal rate0 = new BigDecimal(rate.getExchangeRate());
			BigDecimal rate1 = new BigDecimal(settRate.getExchangeRate());
				
			BigDecimal rate3 = rate0.divide(rate1, 7, BigDecimal.ROUND_HALF_UP);
				
			SettlementRate rate4 = new SettlementRate();
			rate4.setExchangeRate(rate3.toString());
			rate4.setCurrency(sourceCurrency);
			rate4.setTargetCurrency(targetCurrency);
			rate4.setMemberCode(memberCode);
			rate4.setStatus("1");
				
			return rate4;
			//-----------------------------
		}
		
		return rate;
	}
	
	
    /**
     * 创建结算基本汇率
     */
	public void createSettlementBaseRate1(List<SettlementBaseRate> baseRates) {
		 if(baseRates!=null&&baseRates.size()>0){
			 SettlementRateAdjust rateAdjust = new SettlementRateAdjust();
			 rateAdjust.setStatus("1");//取生效的微调值
			 List<SettlementRateAdjust>  adjusts = settlementRateAdjustService.findByCriteria(rateAdjust);
			 Map<String,SettlementRateAdjust> map = new HashMap<String, SettlementRateAdjust>();
			 if(adjusts!=null&&adjusts.size()>0){
				 for(SettlementRateAdjust tra:adjusts){
					 String key = ""+tra.getCurrency()+"-"+tra.getTargetCurrency()+"-"+tra.getMemberCode();
					 map.put(key, tra);
				 }
			 }
			 List<SettlementRate> rateList = new ArrayList<SettlementRate>();

			 for(SettlementBaseRate tbr:baseRates){
				  SettlementRate rate = new SettlementRate();
                  rate.setCurrency(tbr.getCurrency());
                  rate.setTargetCurrency(tbr.getTargetCurrency());
                  rate.setStatus(tbr.getStatus());
                  
                  List<SettlementRate> list = settlementRateService.findByCriteria(rate);
                       
                  if(list!=null&&list.size()>0){
                       for(int i=0;i<list.size()-1;i++){
                    	   SettlementRate tr = list.get(i);
                    	   String key =""+tr.getCurrency()+"-"+tr.getTargetCurrency()+"-"+tr.getMemberCode();
                    	   SettlementRateAdjust tra_ = map.get(key);
                    	   
                    	   if(tra_!=null){
                    		   String adjustRate = tra_.getAdjustRate();
                    		   String newRate = this.getRate(tbr.getExchangeRate(),
                    				   adjustRate);
                    		   tr.setExchangeRate(newRate);
                    	   }else{
                    		   tr.setExchangeRate(tbr.getExchangeRate());
                    	   }
                    	   
                    	   tr.setCreateDate(new Date());
                    	   tr.setCurrencyUnit(tbr.getCurrencyUnit());
                    	   tr.setOperator(tbr.getOperator());
                    	   tr.setEffectDate(tbr.getEffectDate());
                    	   tr.setExpireDate(tbr.getExpireDate());
                    	   
                    	   list.set(i, tr);
                       }
                       
                       settlementRateService.batchCreate(list);
                  }else{
                	  String key =""+tbr.getCurrency()+"-"+tbr.getTargetCurrency()+"-0";
                	  SettlementRateAdjust tra_ = map.get(key);
                	  
                	  if(tra_!=null){
	               		   String adjustRate = tra_.getAdjustRate();
	               		   String newRate = this.getRate(tbr.getExchangeRate(),
	               				   adjustRate);
	               		   rate.setExchangeRate(newRate);
                	  }else{
	               		   rate.setExchangeRate(tbr.getExchangeRate());
                	  }
                	  
                	  rate.setCreateDate(new Date());
                	  rate.setCurrencyUnit(tbr.getCurrencyUnit());
                	  rate.setEffectDate(tbr.getEffectDate());
                	  rate.setExpireDate(tbr.getExpireDate());
                	  rate.setMemberCode("0");
                	  rate.setOperator(tbr.getOperator());
                	  
                	  rateList.add(rate);
                  } 
			 }
			 ///
			 
			 if(rateList.size()>0){
				 settlementRateService.batchCreate(rateList);
			 }
			 
			 settlementBaseRateService.batchCreate(baseRates);
		 }
	}


	@Override
	public void createSettlemenRate(List<SettlementRate> baseRates) {

	}


	@Override
	public void createSettlemenRateAdjust(
			List<SettlementRateAdjust> baseRateAdjusts) {
		
	}
	
	public TransactionBaseRateService getTransactionBaseRateService() {
		return transactionBaseRateService;
	}
	public void setTransactionBaseRateService(
			TransactionBaseRateService transactionBaseRateService) {
		this.transactionBaseRateService = transactionBaseRateService;
	}
	public TransactionRateService getTransactionRateService() {
		return transactionRateService;
	}
	public void setTransactionRateService(
			TransactionRateService transactionRateService) {
		this.transactionRateService = transactionRateService;
	}
	public TransactionRateAdjustService getTransactionRateAdjustService() {
		return transactionRateAdjustService;
	}
	public void setTransactionRateAdjustService(
			TransactionRateAdjustService transactionRateAdjustService) {
		this.transactionRateAdjustService = transactionRateAdjustService;
	}
	public SettlementBaseRateService getSettlementBaseRateService() {
		return settlementBaseRateService;
	}
	public void setSettlementBaseRateService(
			SettlementBaseRateService settlementBaseRateService) {
		this.settlementBaseRateService = settlementBaseRateService;
	}
	public SettlementRateService getSettlementRateService() {
		return settlementRateService;
	}
	public void setSettlementRateService(SettlementRateService settlementRateService) {
		this.settlementRateService = settlementRateService;
	}
	public SettlementRateAdjustService getSettlementRateAdjustService() {
		return settlementRateAdjustService;
	}
	public void setSettlementRateAdjustService(
			SettlementRateAdjustService settlementRateAdjustService) {
		this.settlementRateAdjustService = settlementRateAdjustService;
	}


	@Override
	public SettlementBaseRate findSettlementBaseRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate) {
		return settlementBaseRateService.findCurrentCurrencyRate(sourceCurrency, 
				                           targetCurrency, status, currentDate);
	}


	@Override
	public TransactionBaseRate findTransactionBaseRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate) {
		return transactionBaseRateService.findCurrentCurrencyRate(sourceCurrency,
				                            targetCurrency, status, currentDate);
	}


	public void setTransRateMarkupService(TransRateMarkupService transRateMarkupService) {
		this.transRateMarkupService = transRateMarkupService;
	}
}