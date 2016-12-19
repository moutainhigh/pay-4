/**
 *  File: Batch2bankCityCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.api.validate.rule.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class CityCheckRule extends MessageRule {

	private static List<ProvinceDTO> provinces;
	private ProvinceService provinceService;
	private static Map<String, Integer> citys;
	private CityService cityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentItemRequest request = (PaymentItemRequest) validateBean;
		PaymentItemResult result = request.getResult();

		// 付款到账户直接跳过验证
		if (PayType.ACCT.getValue() == request.getPayType()) {
			return true;
		}

		String provinceCode = result.getProvinceCode();
		String cityName = request.getCity();

		initCity();
		Integer cityCode = citys.get(provinceCode + cityName);
		if (StringUtil.isEmpty(cityName) || null == cityCode) {
			result.setErrorCode(ErrorCode.CITY_INVALID);
			result.setErrorMsg(ErrorCode.CITY_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setCityCode(String.valueOf(cityCode));
			request.setResult(result);
			return true;
		}
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
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
