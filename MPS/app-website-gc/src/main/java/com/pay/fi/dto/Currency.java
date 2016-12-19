package com.pay.fi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Currency {
	EUR("EUR", "欧元"),
	CNY("CNY", "人民币"),
	USD("USD","美元"),
	GBP("GBP","英镑"),
	AUD("AUD","澳元"),
	BRL("BRL","巴西里尔"),
	CAD("CAD","加拿大元"),
	CHF("CHF","瑞士法郎"),
	DKK("DKK","丹麦克朗"),
	HKD("HKD","港币"),
	JPY("JPY","日元"),
	KRW("KRW","韩元"),
	MYR("MYR","马来西亚元"),
	NZD("NZD","新西兰元"),
	SGD("SGD","新加坡元"),
	TWD("TWD","台币"),
	THB("THB","泰铢"),
	SEK("SEK","瑞典克朗"),
	NOK("NOK","挪威克朗"),
	RUB("RUB","俄罗斯卢布"),
	PHP("PHP","菲律宾比索")	;

	Currency(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static List<Currency> getCurrencys(){
		return list;
	}
	
	public static Map<String,Currency> getCurrencyMap(){
		return map;
	}
	

	private String code;
	private String name;
	
	private static List<Currency> list=new ArrayList<Currency>();
	private static Map<String,Currency> map = new HashMap<String, Currency>();
	
	static {
		list.add(Currency.AUD);
		list.add(Currency.BRL);
		list.add(Currency.CAD);
		list.add(Currency.CHF);
		list.add(Currency.CNY);
		list.add(Currency.DKK);
		list.add(Currency.EUR);
		list.add(Currency.GBP);
		list.add(Currency.HKD);
		list.add(Currency.JPY);
		list.add(Currency.KRW);
		list.add(Currency.MYR);
		list.add(Currency.NOK);
		list.add(Currency.NZD);
		list.add(Currency.PHP);
		
		list.add(Currency.RUB);
		list.add(Currency.SEK);
		list.add(Currency.SGD);
		list.add(Currency.THB);
		list.add(Currency.TWD);
		list.add(Currency.USD);
		
		map.put("AUD",Currency.AUD);
		map.put("BRL",Currency.BRL);
		map.put("CAD",Currency.CAD);
		map.put("CHF",Currency.CHF);
		map.put("CNY",Currency.CNY);
		map.put("DKK",Currency.DKK);
		map.put("EUR",Currency.EUR);
		map.put("GBP",Currency.GBP);
		map.put("HKD",Currency.HKD);
		map.put("JPY",Currency.JPY);
		map.put("KRW",Currency.KRW);
		map.put("MYR",Currency.MYR);
		map.put("NOK",Currency.NOK);
		map.put("NZD",Currency.NZD);
		map.put("PHP",Currency.PHP);
		
		map.put("RUB",Currency.RUB);
		map.put("SEK",Currency.SEK);
		map.put("SGD",Currency.SGD);
		map.put("THB",Currency.THB);
		map.put("TWD",Currency.TWD);
		map.put("USD",Currency.USD);
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
