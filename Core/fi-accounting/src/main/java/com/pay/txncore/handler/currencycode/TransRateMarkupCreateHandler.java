/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TransRateMarkup;
import com.pay.txncore.service.TransRateMarkupService;
import com.pay.util.JSonUtil;

/**
 * 交易汇率markup创建
 * @author peiyu.yang
 * @since 2015年6月29日
 */
public class TransRateMarkupCreateHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(TransRateMarkupCreateHandler.class);
	private TransRateMarkupService transRateMarkupService; 

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,Object> paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String,Object>().getClass());
			String operator = (String) paraMap.get("operator");
			List<Map<String,String>> listMap = (List<Map<String,String>>) paraMap.get("list");

			List<TransRateMarkup> markups = null;
			if (null != listMap && !listMap.isEmpty()) {
				markups = new ArrayList<TransRateMarkup>();
				for (Map<String,String> map : listMap) {
					TransRateMarkup markup = new TransRateMarkup();

					markup.setCreateDate(new Date());
					markup.setCurrency(map.get("currency"));
					markup.setMemberCode(map.get("memberCode"));
					markup.setStartPoint(Integer.valueOf(map.get("startPoint")));
					markup.setEndPoint(Integer.valueOf(map.get("endPoint")));
					markup.setTargetCurrency(map.get("targetCurrency"));
                    markup.setMarkup(map.get("markup"));
                    markup.setCardOrg(map.get("cardOrg"));
                    markup.setCardCountry(map.get("cardCountry"));
                    markup.setCardCurrencyCode(map.get("cardCurrencyCode"));
                    markup.setLtaCurrencyCode(map.get("ltaCurrencyCode"));
                    markup.setStartAmount(Double.valueOf(map.get("startAmount")));
                    markup.setEndAmount(Double.valueOf(map.get("endAmount")));
                    markup.setPriority(Integer.valueOf(map.get("priority")));
					markup.setOperator(operator);
					markup.setStatus("1");
					
					markups.add(markup);
				}
			}

			transRateMarkupService.batchCreate(markups);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("query partner error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setTransRateMarkupService(
			TransRateMarkupService transRateMarkupService) {
		this.transRateMarkupService = transRateMarkupService;
	}
}
