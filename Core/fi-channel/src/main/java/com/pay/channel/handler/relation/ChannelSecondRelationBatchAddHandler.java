/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.relation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 添加渠道二级商户映射
 * 
 * @author chma
 */
public class ChannelSecondRelationBatchAddHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChannelSecondRelationBatchAddHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String orgCode = (String) paraMap.get("orgCode");
			String cardOrg = (String) paraMap.get("cardOrg");
			String memberCode = (String) paraMap.get("memberCode");
			String orgMerchantCodes = (String) paraMap.get("orgMerchantCode");
			String operator = String.valueOf(paraMap.get("operator"));
			String transType = (String) paraMap.get("transType");
			String currencyCode = (String) paraMap.get("currencyCode");
			String payChannelItemId = (String)paraMap.get("payChannelItemId");
			String channelConfigIds = (String)paraMap.get("channelConfigId");
			List<ChannelSecondRelation> channelSecondRelations = null;
			channelSecondRelations = new ArrayList<ChannelSecondRelation>();
			String[] orgMerchantCodeArr = orgMerchantCodes.split(",");
			String[] channelConfigIdArr = channelConfigIds.split(",");
			for (int i = 0; i < orgMerchantCodeArr.length; i++) {
				String orgMerchantCode = orgMerchantCodeArr[i];
				String channelConfigId = channelConfigIdArr[i];
				if(StringUtils.isNotEmpty(orgMerchantCode)){
					ChannelSecondRelation relation = new ChannelSecondRelation();
					relation.setCreateDate(new Date());
					relation.setMemberCode(Long.valueOf(memberCode));
					relation.setOperator(operator);
					relation.setOrgCode(orgCode);
					relation.setOrgMerchantCode(orgMerchantCode);
					relation.setPaymentChannelItemId(payChannelItemId);
					relation.setCardOrg(cardOrg);
					relation.setChannelConfigId(Long.parseLong(channelConfigId));
					channelSecondRelations.add(relation);	
				}
			}
			paymentChannelService
					.addChannelSecondRelation(channelSecondRelations);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
}
