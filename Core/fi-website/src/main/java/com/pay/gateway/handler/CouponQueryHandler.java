/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.gateway.handler;

import java.util.HashMap;
import java.util.List;
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
public class CouponQueryHandler implements EventHandler {

	private final Log logger = LogFactory.getLog(CouponQueryHandler.class);
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
			String effectDate = paraMap.get("effectDate");
			String expireDate = paraMap.get("expireDate");
			String couponNumber = paraMap.get("couponNumber");
			String value = paraMap.get("value");
			String minOrderAmount = paraMap.get("minOrderAmount");
			String scene = paraMap.get("scene");
			String status = paraMap.get("status");
			String orgCode = paraMap.get("orgCode");

			CouponList couponList = new CouponList();

			if (!StringUtil.isEmpty(couponNumber)) {
				couponList.setCouponNumber(couponNumber);
			}

			if (!StringUtil.isEmpty(effectDate)) {
				couponList.setEffectDate(DateUtil.parse("yyyy-MM-dd",
						effectDate));
			}

			if (!StringUtil.isEmpty(expireDate)) {
				couponList.setExpireDate(DateUtil.parse("yyyy-MM-dd",
						expireDate));
			}

			if (!StringUtil.isEmpty(value)) {
				couponList.setValue(Long.valueOf(value));
			}

			if (!StringUtil.isEmpty(minOrderAmount)) {
				couponList.setMinOrderAmount(Long.valueOf(minOrderAmount));
			}

			if (!StringUtil.isEmpty(scene)) {
				couponList.setScene(scene);
			}

			if (!StringUtil.isEmpty(status)) {
				couponList.setStatus(Integer.valueOf(status));
			}

			if (!StringUtil.isEmpty(orgCode)) {
				couponList.setOrgCode(orgCode);
			}

			List<CouponList> list = couponListService.queryCoupon(couponList);

			resultMap.put("list", list);
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
