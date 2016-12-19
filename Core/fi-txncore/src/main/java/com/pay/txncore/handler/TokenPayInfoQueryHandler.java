/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.txncore.service.TokenPayInfoService;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 跨境API收单
 * 
 * @author chma
 */
public class TokenPayInfoQueryHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(getClass());
    private TokenPayInfoService tokenPayInfoService;
    private String securityKey;

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());      
			String registerUserId = paraMap.get("registerUserId");
			String partnerId = paraMap.get("partnerId");
			String token = paraMap.get("token");
			String status = paraMap.get("status");
			
			Map<String,Object> params = new HashMap<String, Object>();
			if(!StringUtil.isEmpty(token)){
				params.put("token",token);
			}else{
				resultMap.put("responseCode","9999");
				resultMap.put("responseDesc","缺少token值");
				return JSonUtil.toJSonString(resultMap);
			}
			params.put("registerUserId",registerUserId);
			params.put("partnerId",partnerId);
			if(!StringUtil.isEmpty(status)) {
				params.put("status",status);
			}
				
			List<TokenPayInfo> list = tokenPayInfoService.getTokenPayInfos(params);
			TokenPayInfo payInfo = null;
			if(list!=null&&!list.isEmpty()){
					payInfo = list.get(0);
					payInfo.setCardHolderNumber(DESUtil.decrypt(payInfo.getCardHolderNumber(),securityKey));
					payInfo.setCardExpirationMonth(DESUtil.decrypt(payInfo.getCardExpirationMonth(),securityKey));
					payInfo.setCardExpirationYear(DESUtil.decrypt(payInfo.getCardExpirationYear(),securityKey));
					payInfo.setSecurityCode(DESUtil.decrypt(payInfo.getSecurityCode(),securityKey));
					resultMap.put("tokenMap",MapUtil.bean2map(payInfo));
			}				
			resultMap.put("responseCode",ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.SUCCESS.getDesc());
		} catch (BusinessException e) {
			logger.error("TokenPayInfoQueryHandler error:", e);
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

	public void setTokenPayInfoService(TokenPayInfoService tokenPayInfoService) {
		this.tokenPayInfoService = tokenPayInfoService;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
}
