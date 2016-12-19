/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.client.HandlerClientService;
import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 获取可用渠道
 * 
 * @author chma
 */
public class OrgRateQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(OrgRateQueryHandler.class);
	private HandlerClientService handlerClientService;
	private PaymentChannelService paymentChannelService;
	private ChannelSecondRelationDAO channelSecondRelationDAO;
	private ChannelConfigDAO channelConfigDAO;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	public void setChannelSecondRelationDAO(
			ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	public void setHandlerClientService(
			HandlerClientService handlerClientService) {
		this.handlerClientService = handlerClientService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map<String, String> paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String paymentType = paraMap.get("paymentType");
		String memberType = paraMap.get("memberType");
		String memberCode = paraMap.get("memberCode");
		String orgCode = paraMap.get("orgCode");
		String orgMerchantCode = paraMap.get("orgMerchantCode");
		String transType = paraMap.get("transType");
		String currencyCode = paraMap.get("currencyCode");
		String invoiceNo = paraMap.get("invoiceNo");
		String orderAmount = paraMap.get("orderAmount");
		String cardNo = paraMap.get("cardHolderNumber");
		String cardExpirationYear = paraMap.get("cardExpirationYear");
		String cardExpirationMonth = paraMap.get("cardExpirationMonth");

		// 获取配置渠道
		PaymentChannelItem item = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode, orgMerchantCode);
		
		

		Map itemConfigMap = MapUtil.bean2map(item);

		Map preParaMap = new HashMap();
		preParaMap.putAll(itemConfigMap);
		preParaMap.putAll(paraMap);

		Map<String, String> preResultMap = handlerClientService
				.preOrgRateQuery(item.getPreServerUrl(), preParaMap);

		resultMap.putAll(preResultMap);
		resultMap.put("orgCode", item.getOrgCode());
		resultMap.put("orgMerchantCode", item.getOrgMerchantCode());
		return JSonUtil.toJSonString(resultMap);
	}
	

}
