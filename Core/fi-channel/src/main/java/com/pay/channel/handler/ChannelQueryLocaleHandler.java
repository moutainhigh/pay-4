package com.pay.channel.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.service.ChannelMidCountService;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;
/**
 * 获取本地化选择的渠道
 * @author delin.dong
 * @date 2016年5月7日 16:32:06
 */
public class ChannelQueryLocaleHandler implements EventHandler{

	public final Log logger = LogFactory.getLog(ChannelQueryHandler.class);
	//private ChannelMidCountService channelMidCountService; 
	private PaymentChannelService paymentChannelService;
	private ChannelSecondRelationDAO channelSecondRelationDAO;
	private ChannelConfigDAO channelConfigDAO;
/*	
	public void setChannelMidCountService(
			ChannelMidCountService channelMidCountService) {
		this.channelMidCountService = channelMidCountService;
	}
*/
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
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		logger.info("dataMsg : "+dataMsg);
		Map resultMap = new HashMap();
		Map<String, String> paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryLoacleHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String paymentType = paraMap.get("paymentType");
		String memberCode = paraMap.get("memberCode");
		String transType = paraMap.get("transType");
		String tradeType = paraMap.get("tradeType");
		String prdtCode = paraMap.get("prdtCode");
		if(isDisguisedDCC(prdtCode))transType = "EDC";
		//获取商户配置的本地化支付的渠道
		List<PaymentChannelItem> configItems =
				paymentChannelService.queryConfigPaymentChannelItem(paymentType, memberCode,tradeType);
		if (null == configItems || configItems.isEmpty()) {
			logger.info("请配置"+memberCode+"的本地化支付渠道。。。");
		}
		resultMap.put("paymentChannelItems", configItems);
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}
	
	private boolean isDisguisedDCC(String prdtCode){
		if(StringUtil.isEmpty(prdtCode)){
			return false;
		}else if(DCCEnum.CUSTOM_STANDARD.getCode().equals(prdtCode)||
				 DCCEnum.CUSTOM_FORCED.getCode().equals(prdtCode)||
				 DCCEnum.CUSTOM_HIDDEN.getCode().equals(prdtCode)||
				 DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(prdtCode)){
			return true;
		}else{
			return false;
		}
	}
}
