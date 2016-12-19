/**
 *  File: Batch2bankCityCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class Batch2bankCityCheckRule extends MessageRule {

	private static List<ProvinceDTO> provinces;
	private ProvinceService provinceService;
	private static Map<String, Integer> citys;
	private CityService cityService;

	public Batch2bankCityCheckRule(ProvinceService provinceService,
			CityService cityService) {
		this.provinceService = provinceService;
		this.cityService = cityService;
		initCity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String provinceCode = detailRequest.getPaymentResponse()
				.getPayeeBankProvince();
		String cityName = detailRequest.getPayeeBankCityName();
		if (StringUtil.isEmpty(cityName)
				|| null == citys.get(provinceCode + cityName)) {
			Map paraMap = new HashMap();
			paraMap.put("row", detailRequest.getRow());
			String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);
			detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
			return false;
		} else {
			detailRequest.getPaymentResponse().setPayeeBankCity(
					String.valueOf(citys.get(provinceCode + cityName)));
			return true;
		}
	}

	private void initCity() {

		initProvince();//

		if (null == citys || citys.isEmpty()) {
			citys = new HashMap<String, Integer>();
			for (ProvinceDTO pro : provinces) {
				List<CityDTO> cityList = cityService.findByProvinceId(pro
						.getProvincecode());
				for (CityDTO city : cityList) {
					citys.put(pro.getProvincecode() + city.getCityname(), city
							.getCitycode());
				}
			}
		}
	}

	private void initProvince() {

		if (null == provinces || provinces.isEmpty()) {
			provinces = provinceService.findAll();
		}
	}
}
