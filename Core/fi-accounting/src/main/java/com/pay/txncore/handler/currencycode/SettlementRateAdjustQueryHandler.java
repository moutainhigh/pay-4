/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.SettlementRateAdjustService;
import com.pay.txncore.model.SettlementRateAdjust;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @author peiyu.yang
 */
public class SettlementRateAdjustQueryHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(SettlementRateAdjustQueryHandler.class);
	private SettlementRateAdjustService settlementRateAdjustService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String currency = (String)paraMap.get("currency");
			String targetCurrency =(String) paraMap.get("targetCurrency");
			String effectDate = (String)paraMap.get("effectDate");
			String expireDate = (String)paraMap.get("expireDate");
			String status = (String) paraMap.get("status");
			String memberCode = (String) paraMap.get("memberCode");
			String cardOrg = (String) paraMap.get("cardOrg");
			
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);

			SettlementRateAdjust exchangeRate = new SettlementRateAdjust();

			if (!StringUtil.isEmpty(currency)) {
				exchangeRate.setCurrency(currency);
			}
			SimpleDateFormat formatter = new SimpleDateFormat(
					DEFAULT_DATE_FROMAT);

			if (!StringUtil.isEmpty(effectDate)) {
				exchangeRate.setEffectDate(formatter.parse(effectDate));
			}
			if (!StringUtil.isEmpty(effectDate)) {
				exchangeRate.setExpireDate(formatter.parse(expireDate));
			}

			if (!StringUtil.isEmpty(targetCurrency)) {
				exchangeRate.setTargetCurrency(targetCurrency);
			}
			
			if (!StringUtil.isEmpty(status)) {
				exchangeRate.setStatus(status);
			}
			
			if (!StringUtil.isEmpty(cardOrg)) {
				exchangeRate.setCardOrg(cardOrg);
			}
			
			if (!StringUtil.isEmpty(memberCode)) {
				exchangeRate.setMemberCode(memberCode);
			}

			List<SettlementRateAdjust> list = settlementRateAdjustService
					.findByCriteria(exchangeRate,page);
			
			resultMap.put("list", list);
			resultMap.put("page", page);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setSettlementRateAdjustService(
			SettlementRateAdjustService settlementRateAdjustService) {
		this.settlementRateAdjustService = settlementRateAdjustService;
	}

    
}
