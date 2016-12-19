/**
 *  File: Pay2bankBrancheSettingAction.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-23   terry     Create
 *
 */
package com.pay.fo.order.validate.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.validate.PaymentRequest;
import com.pay.inf.rule.AbstractAction;
import com.pay.lucene.common.util.Provinces;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.lucene.service.LuceneService;

/**
 * 
 */
public class Pay2bankBrancheSettingAction extends AbstractAction {

	private LuceneService luceneService;
	private Log logger = LogFactory.getLog(Pay2bankBrancheSettingAction.class);
	private BankBrancheInfoService bankBrancheInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object arg0) throws Exception {
		PaymentRequest detailRequest = (PaymentRequest) arg0;

		String bankName = detailRequest.getPayeeBankName();
		String province = detailRequest.getPayeeBankProvinceName();
		String city = detailRequest.getPayeeBankCityName();
		String bankChannelCode = detailRequest.getPaymentResponse()
				.getBankChannelCode();
		String bankBranche = detailRequest.getPayeeOpeningBankName();

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
		} else {
			paramInfoDTO.setType(1);
		}
		unionBankCode = luceneService.searchBankUnionCode(paramInfoDTO);
		detailRequest.getPaymentResponse().setBankNumber(unionBankCode);
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}

}
