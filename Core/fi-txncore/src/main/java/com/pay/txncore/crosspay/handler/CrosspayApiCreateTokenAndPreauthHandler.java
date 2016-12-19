/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.CardBindService;
import com.pay.txncore.service.CreateTokenPreauthService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 创建token及预授权
 * @author JiangboPeng
 * 
 */
public class CrosspayApiCreateTokenAndPreauthHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(getClass());
	private CreateTokenPreauthService createTokenPreauthService ;

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
			//创建token绑卡
			paymentResult = this.createTokenPreauthService.CreateTokenPreauthBind(paymentInfo);
			if(paymentResult!=null){
				if(ResponseCodeEnum.SUCCESS.getCode().equals(paymentResult.getResponseCode())){
					paymentResult.setResponseDesc("token has been created and authorization has been completed:创建令牌及预授权成功");
				}
				resultMap.putAll(MapUtil.bean2map(paymentResult));
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

	/**
	 * @param createTokenPreauthService the createTokenPreauthService to set
	 */
	public void setCreateTokenPreauthService(
			CreateTokenPreauthService createTokenPreauthService) {
		this.createTokenPreauthService = createTokenPreauthService;
	}

	
}
