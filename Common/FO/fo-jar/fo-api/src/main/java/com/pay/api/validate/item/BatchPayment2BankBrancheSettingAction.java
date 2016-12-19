/**
 *  File: Pay2bankBrancheSettingAction.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-23   terry     Create
 *
 */
package com.pay.api.validate.item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.inf.rule.AbstractAction;
import com.pay.lucene.common.util.Provinces;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.service.LuceneService;

/**
 * 
 */
public class BatchPayment2BankBrancheSettingAction extends AbstractAction {

	private LuceneService luceneService;
	private Log logger = LogFactory
			.getLog(BatchPayment2BankBrancheSettingAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {

		BatchPaymentItemRequest request = (BatchPaymentItemRequest) validateBean;
		BatchPaymentItemResult result = request.getResult();

		String bankName = request.getBankName();
		String province = request.getProvince();
		String city = request.getCity();
		String bankChannelCode = result.getChannelCode();
		String bankBranche = request.getBranche();

		SearchParamInfoDTO paramInfoDTO = new SearchParamInfoDTO();
		paramInfoDTO.setResultSize(10);
		paramInfoDTO.setBankName(bankName);
		paramInfoDTO.setProvinceName(province);
		paramInfoDTO.setCityName(city);
		paramInfoDTO.setBankKaihu(bankBranche);

		// 判断是否需特定银行联号
		String unionBankCode = null;
		if ("10003001".equals(bankChannelCode)) {
			if (Provinces.SHANGHAI_NAME.equals(province)) {
				paramInfoDTO.setType(2);
			} else {
				paramInfoDTO.setType(1);
			}
			paramInfoDTO.setType(2);
		} else {
			paramInfoDTO.setType(1);
		}
		unionBankCode = luceneService.searchBankUnionCode(paramInfoDTO);
		result.setBankNumber(unionBankCode);
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

}
