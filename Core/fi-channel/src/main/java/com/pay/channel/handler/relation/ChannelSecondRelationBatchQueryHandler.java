package com.pay.channel.handler.relation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelSndRelation;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 通道二级商户号添加查询可配置的二级商户号
 * 
 * @date  二〇一六年六月二十七日 17:08:12
 * @author delin
 */
public class ChannelSecondRelationBatchQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChannelSecondRelationQueryHandler.class);
	private PaymentChannelService paymentChannelService;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			String memberCode =  paraMap.get("memberCode");
			String orgCode = paraMap.get("orgCode");
			String startTime = paraMap.get("startTime");
			String endTime = paraMap.get("endTime");
			String type = paraMap.get("type");
			String fitMerchantType = paraMap.get("fitMerchantType");
			String orgMerchantCode = paraMap.get("orgMerchantCode");
			String paymentCategoryCode = paraMap.get("paymentCategoryCode");
			Map param = new HashMap();
			if (!StringUtil.isEmpty(memberCode)) {
				param.put("memberCode", memberCode);
			}
			if (!StringUtil.isEmpty(orgMerchantCode)) {
				param.put("orgMerchantCode", orgMerchantCode);
			}
			if (!StringUtil.isEmpty(paymentCategoryCode)) {
				param.put("paymentCategoryCode", paymentCategoryCode);
			}
			if (!StringUtil.isEmpty(orgCode)) {
				param.put("orgCode", orgCode);
			}
			if (!StringUtil.isEmpty(fitMerchantType)) {
				param.put("fitMerchantType", fitMerchantType);
			}
			if (!StringUtil.isEmpty(startTime)) {
				param.put("startTime", startTime);
			}
			if (!StringUtil.isEmpty(endTime)) {
				param.put("endTime", endTime);
			}

			List<ChannelSndRelation> relations=null;
			if(type.equals("1")){ //新增
				relations=	paymentChannelService.findSndrelationNew(param);
			}else if(type.equals("2")){ // 已删除
				relations=	paymentChannelService.findSndrelationDeleted(param);
			}else if(type.equals("3")){ //已关联
				relations=  paymentChannelService.findSndrelationConnected(param);
			}
			resultMap.put("relations",relations);
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
