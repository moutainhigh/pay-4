/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.gateway.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.gateway.model.CouponList;
import com.pay.gateway.service.CouponListService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class CouponUpdateHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(CouponUpdateHandler.class);
	private CouponListService couponListService;

	public void setCouponListService(CouponListService couponListService) {
		this.couponListService = couponListService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String id = paraMap.get("id");

			String effectDate = paraMap.get("effectDate");
			String expireDate = paraMap.get("expireDate");
			String couponNumber = paraMap.get("couponNumber");
			String value = paraMap.get("value");
			String minOrderAmount = paraMap.get("minOrderAmount");
			String scene = paraMap.get("scene");
			String status = paraMap.get("status");
			String orgCode = paraMap.get("orgCode");

			CouponList couponList = couponListService
					.findById(Long.valueOf(id));

			couponList.setCouponNumber(couponNumber);
			couponList.setEffectDate(DateUtil.parse("yyyy-MM-dd", effectDate));
			couponList.setExpireDate(DateUtil.parse("yyyy-MM-dd", expireDate));

			if (!StringUtil.isEmpty(minOrderAmount)) {
				couponList.setMinOrderAmount(Long.valueOf(minOrderAmount));
			}
			
			if (!StringUtil.isEmpty(orgCode)) {
				couponList.setOrgCode(orgCode);
			}

			couponList.setScene(scene);
			couponList.setStatus(Integer.valueOf(status));
			couponList.setValue(Long.valueOf(value));

			couponListService.updateCoupon(couponList);

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
