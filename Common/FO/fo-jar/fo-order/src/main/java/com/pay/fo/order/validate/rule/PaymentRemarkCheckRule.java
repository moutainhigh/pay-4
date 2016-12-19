/**
 *  File: PaymentAmountCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.RemarkLimit;
import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 备注限制
 */
public class PaymentRemarkCheckRule extends MessageRule {

	/**
	 * 出款行配置服务类
	 */
	protected ConfigBankService configBankService;

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String payeeBankCode = detailRequest.getPaymentResponse().getPayeeBankCode();
		String tradeType = detailRequest.getTradeType();
		String remark = detailRequest.getRemark();
		String fundoutBank = "";

		if (!StringUtil.isNull(remark)) {
			if (!regString(remark)) {
				Map<Object, Object> paraMap = new HashMap<Object, Object>();
				paraMap.put("row", detailRequest.getRow());
				paraMap.put("remarkLimitMsg", "备注仅支持汉字、字母和数字");
				String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);

				detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
				return false;
			}
		}

		if (!StringUtil.isNull(payeeBankCode)) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 目的银行编号
			map.put("targetBankId", payeeBankCode);
			// 出款方式
			map.put("foMode", 1);
			// 出款业务
			map.put("fobusiness", OrderType.BATCHPAY2BANK.getValue());

			fundoutBank = configBankService.queryFundOutBank2Withdraw(map);
		}

		if (!StringUtil.isNull(fundoutBank)) {

			RemarkLimit remarkLimit = RemarkLimit.get(fundoutBank, tradeType);
			if (null == remarkLimit) {
				remarkLimit = RemarkLimit.OTHER;
			}
			int limit = remarkLimit.getLimit();
			int remarkLength = StringLength(remark);
			if (remarkLength > limit) {

				Map<Object, Object> paraMap = new HashMap<Object, Object>();
				paraMap.put("row", detailRequest.getRow());
				paraMap.put("remarkLimitMsg", remarkLimit.getDesc());
				String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);

				detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
				return false;
			} else {
				return true;
			}

		} else {
			int limit = RemarkLimit.OTHER.getLimit();
			int remarkLength = StringLength(remark);
			if (remarkLength > limit) {
				Map<Object, Object> paraMap = new HashMap<Object, Object>();
				paraMap.put("row", detailRequest.getRow());
				paraMap.put("remarkLimitMsg", RemarkLimit.OTHER.getDesc());
				String errorMsg = FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap);

				detailRequest.getPaymentResponse().setErrorMsg(errorMsg);
				return false;
			} else {
				return true;
			}
		}
	}

	private boolean regString(String value) {
		return Pattern.matches("(\\w|[\\u4E00-\\u9FA5])*", value);
	}

	private int StringLength(String value) {
		int utflen = 0;
		if (StringUtils.isNotEmpty(value)) {
			char[] val = value.toCharArray();

			for (int i = 0; i < value.length(); ++i) {
				int c = val[i];
				if ((c >= 1) && (c <= 127))
					++utflen;
				else if (c > 2047)
					utflen += 2;
				else {
					utflen += 2;
				}
			}
		}
		return utflen;
	}
}
