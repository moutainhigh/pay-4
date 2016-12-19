package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.BindCardService;
import com.pay.txncore.service.CardBindService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
/**
 * 解绑卡
 * 
 * @author yanshichuan
 */
public class CardBindHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(getClass());
    private BindCardService bindCardService;

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,paraMap);
			PaymentResult paymentResult = null;
			
			String tradeType = paymentInfo.getTradeType();
			
			if(TradeTypeEnum.CARD_BIND.getCode().equals(tradeType)||
					TradeTypeEnum.CARD_BIND_API.getCode().equals(tradeType)){
				paymentResult = bindCardService.bind(paymentInfo);
			}else if(TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType)){
				paymentResult = bindCardService.unBind(paymentInfo);
			}
			if(paymentResult!=null){
				resultMap.putAll(MapUtil.bean2map(paymentResult));
			}
			//将真实的渠道返回码返回, 供失败订单根据渠道返回码捕获异常卡使用
			resultMap.put("orgCode", paymentResult.getOrgCode()) ;
			resultMap.put("channelRespCode", paymentResult.getChannelRespCode()) ;
			resultMap.put("responseCode", paymentResult.getResponseCode());
			resultMap.put("responseDesc", paymentResult.getResponseDesc());
			resultMap.put("token", paymentResult.getToken());
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

	public BindCardService getBindCardService() {
		return bindCardService;
	}

	public void setBindCardService(BindCardService bindCardService) {
		this.bindCardService = bindCardService;
	}

}
