package com.pay.channel.handler.paymentchannelitem;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.channel.dao.PaymentChannelItemConfigDAO;
import com.pay.channel.model.PaymentChannelItemConfig;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

public class PaymentChannelItemConfigUpdateHandler implements EventHandler{
	
	public final Log logger = LogFactory
			.getLog(PaymentChannelItemConfigUpdateHandler.class);
	
	private PaymentChannelItemConfigDAO paymentChannelItemConfigDAO;

	public void setPaymentChannelItemConfigDAO(
			PaymentChannelItemConfigDAO paymentChannelItemConfigDAO) {
		this.paymentChannelItemConfigDAO = paymentChannelItemConfigDAO;
	}



	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			String id = paraMap.get("id");
			String memberCode = paraMap.get("memberCode");
			String paymentChannelItemId = paraMap.get("paymentChannelItemId");
			String paymentType = paraMap.get("paymentType");
			String operator = paraMap.get("operator");
			String channelPriority = paraMap.get("channelPriority");
			PaymentChannelItemConfig paymentChannelItemConfig = new PaymentChannelItemConfig();
			if(StringUtils.hasText(id)){
				paymentChannelItemConfig.setId(Long.valueOf(id));
			}
			if(StringUtils.hasText(memberCode)){
				paymentChannelItemConfig.setMemberCode(Long.valueOf(memberCode));
			}
			if(StringUtils.hasText(paymentChannelItemId)){
				paymentChannelItemConfig.setPaymentChannelItemId(Long.valueOf(paymentChannelItemId));
			}
			if(StringUtils.hasText(paymentType)){
				paymentChannelItemConfig.setPaymentType(Integer.valueOf(paymentType));
			}
			if(StringUtils.hasText(operator)){
				paymentChannelItemConfig.setOperator(operator);
			}
			if(StringUtils.hasText(channelPriority)){
				paymentChannelItemConfig.setChannelPriority(channelPriority);
			}
			paymentChannelItemConfigDAO.update(paymentChannelItemConfig);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
