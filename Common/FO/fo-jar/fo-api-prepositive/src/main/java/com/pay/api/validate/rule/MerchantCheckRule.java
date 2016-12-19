/**
 *  File: MerchantCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import org.apache.commons.lang.StringUtils;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.rule.MessageRule;

/**
 * 
 */
public class MerchantCheckRule extends MessageRule {

	private BaseDAO<MerchantConfigure> merchantConfigureDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest request = (PaymentRequest) validateBean;

		PaymentResult result = request.getResult();
		String merchantCode = request.getMerchantCode();

		MerchantConfigure merchantConfigure = new MerchantConfigure();
		boolean flag = false;
		// 查看预留域1值是否为空，不是则当做merchantCode进行查询
		if(StringUtils.isNotBlank(request.getText1())){
			MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
			criteria.createCriteria().andMerchantCodeEqualTo(request.getText1());
			merchantConfigure = (MerchantConfigure) merchantConfigureDao.findObjectByCriteria(criteria);
			if(merchantConfigure != null){
				flag = true;
			}
		}
		if(!flag){
			MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
			criteria.createCriteria().andMerchantCodeEqualTo(merchantCode);
			merchantConfigure = (MerchantConfigure) merchantConfigureDao.findObjectByCriteria(criteria);
			flag = null != merchantConfigure;
		}
		if (flag) {
			result.setMerchantCode(merchantCode);
			result.setConfigure(merchantConfigure);
			return true;
		} else {
			result.setErrorCode(ErrorCode.MERCHANT_INVALID);
			result.setErrorMsg(ErrorCode.MERCHANT_INVALID_DESC);
			request.setResult(result);
			return false;
		}
	}

	public void setMerchantConfigureDao(
			BaseDAO<MerchantConfigure> merchantConfigureDao) {
		this.merchantConfigureDao = merchantConfigureDao;
	}
}
