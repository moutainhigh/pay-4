/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.dao.Page;
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
public class SimpleTokenPayInfoQueryHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(getClass());
    private TokenPayInfoService tokenPayInfoService;
    private String securityKey;

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			
			if(paraMap.get("cardNo") != null && !StringUtil.isEmpty(paraMap.get("cardNo").toString())) {
				paraMap.put("cardNo", DESUtil.encrypt(paraMap.get("cardNo").toString().trim(), securityKey));
			}
			
			List<TokenPayInfo> list = tokenPayInfoService.getTokenPayInfos(paraMap, page);
			if(list!=null&&!list.isEmpty()){
				resultMap.put("list", list);
			} else {
				resultMap.put("list", new ArrayList<TokenPayInfo>());
			}
			resultMap.put("page", page);
			resultMap.put("responseCode",ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",ResponseCodeEnum.SUCCESS.getDesc());
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

	public void setTokenPayInfoService(TokenPayInfoService tokenPayInfoService) {
		this.tokenPayInfoService = tokenPayInfoService;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
}
