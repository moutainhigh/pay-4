package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.CardBindService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
/**
 * token-绑卡并支付处理类
 * @author zhaoyang
 * @version 1.0.0 2016-09-19
 */
public class CreateTokenAndPayHandler implements EventHandler {
	public final Logger logger = LoggerFactory.getLogger(getClass());
	private CardBindService cardBindService;
	
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			paraMap = JSonUtil.toObject(dataMsg,new HashMap<String, String>().getClass());
			PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,paraMap);
			PaymentResult paymentResult = null;
			//请求绑卡支付
			paymentResult = cardBindService.payAndBind(paymentInfo);
			if(paymentResult!=null){
				if(ResponseCodeEnum.SUCCESS.getCode().equals(paymentResult.getResponseCode())){
					paymentResult.setResponseDesc("token has been created and transaction has been completed:创建令牌及消费成功");
				}
				resultMap.putAll(MapUtil.bean2map(paymentResult));
				resultMap.remove("merchantBillName");
			}
			resultMap.put("responseCode", paymentResult.getResponseCode());
			resultMap.put("responseDesc", paymentResult.getResponseDesc());
		} catch (BusinessException e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("card bind error:", e);
			resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
	public void setCardBindService(CardBindService cardBindService) {
		this.cardBindService = cardBindService;
	}

}
