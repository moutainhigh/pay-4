package com.pay.app.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pay.util.StringUtil;

public class CrossBorerPayValidator {
	
	Set<String> currencySet=new HashSet<String>();
	{
		currencySet.add("USD");
		currencySet.add("EUR");
		currencySet.add("GBP");
		currencySet.add("HKD");
		currencySet.add("AUD");
		currencySet.add("CAD");
		currencySet.add("NZD");
		currencySet.add("JPY");
	}
	Set<String> fileSuffixSet=new HashSet<String>();
	{
		fileSuffixSet.add("jpg");
		fileSuffixSet.add("png");
		fileSuffixSet.add("pdf");
		fileSuffixSet.add("zip");
		fileSuffixSet.add("rar");
	}
	public boolean verifyFileSuffix(String fileSuffix){
		return fileSuffixSet.contains(fileSuffix);
	}
	/**
	 * 验证金额币种是否为空
	 * @param item
	 * @param indexs
	 * @return
	 */
	public boolean verifyMoneyAndCurrencyIsNull(ArrayList<String> item,int...indexs){
		return StringUtil.isEmpty(item.get(indexs[0]))||StringUtil.isEmpty(item.get(indexs[1]));
	}
	/**
	 * 验证金额币种是否合法
	 * @return
	 */
	public boolean verifyCurrencyLegal(String currency,String op){
		boolean falg=false;
		if("外币".equals(op)){
			if(!currencySet.contains(currency)){
				falg=true;
			}
		}else{
			if(!currencySet.equals("CNY")){
				falg=true;
			}
		}
		return falg;
	}
	/**
	 * 验证重复订单
	 * @return
	 */
	public boolean verifyRepeatTrade(Map<String, List<String>> payDetail,String orderid){
		return payDetail.containsKey(orderid);
	}
}
