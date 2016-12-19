/**
 *  File: Batch2bankProvinceCheckRule.java
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
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.ProvinceService;
import com.pay.util.StringUtil;

/**
 * 验证批次到银行省份
 */
public class ProvinceCheckRule extends MessageRule {

	private static Map<String, Integer> provinces;
	private ProvinceService provinceService;

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

		String province = request.getProvince();
		initProvince();
		Integer provinceCode = provinces.get(province);
		if (StringUtil.isEmpty(province) || null == provinceCode) {
			result.setErrorCode(ErrorCode.PROVINCE_INVALID);
			result.setErrorMsg(ErrorCode.PROVINCE_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			result.setProvinceCode(String.valueOf(provinceCode));
			request.setResult(result);
			return true;
		}
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	private void initProvince() {

		if (null == provinces || provinces.isEmpty()) {
			provinces = new HashMap<String, Integer>();
			List<ProvinceDTO> proList = provinceService.findAll();
			for (ProvinceDTO pro : proList) {
				provinces.put(pro.getProvincename(), pro.getProvincecode());
			}
		}
	}
}
