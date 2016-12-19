/**
 *  File: Batch2bankProvinceCheckRule.java
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
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.ProvinceService;
import com.pay.util.StringUtil;

/**
 * 验证批次到银行省份
 */
public class Batch2bankProvinceCheckRule extends MessageRule {

	private static Map<String, Integer> provinces;
	private ProvinceService provinceService;

	public Batch2bankProvinceCheckRule(ProvinceService provinceService) {
		this.provinceService = provinceService;
		initProvince();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String province = detailRequest.getPayeeBankProvinceName();

		if (StringUtil.isEmpty(province) || null == provinces.get(province)) {
			Map paraMap = new HashMap();
			paraMap.put("row", detailRequest.getRow());
			String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);
			detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
			return false;
		} else {
			detailRequest.getPaymentResponse().setPayeeBankProvince(
					String.valueOf(provinces.get(province)));
			return true;
		}
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
