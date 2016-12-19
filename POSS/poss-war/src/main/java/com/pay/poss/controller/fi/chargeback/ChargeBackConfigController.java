package com.pay.poss.controller.fi.chargeback;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.poss.client.ChargeBackService;
import com.pay.poss.controller.fi.dto.ChargeBackConfig;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 拒付订单管理
 * 
 * @Description
 * @file ChargeBackOrderController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 Corporation. All rights reserved. Date
 */
public class ChargeBackConfigController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String configView;
	private ChargeBackService chargeBackService;

	public void setConfigView(String configView) {
		this.configView = configView;
	}

	public void setChargeBackService(ChargeBackService chargeBackService) {
		this.chargeBackService = chargeBackService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map config = chargeBackService.queryChargeBackConfig(null);
		return new ModelAndView(configView, config);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getChargebackConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String memberCode = request.getParameter("memberCode");

		try {
			Map config = chargeBackService.queryChargeBackConfig(memberCode);
			String json = JSonUtil.toJSonString(config);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("getChargebackConfig error:", e);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addOrUpdate(HttpServletRequest request,
			HttpServletResponse response, ChargeBackConfig config)
			throws Exception {

		Integer id = config.getId();
		boolean isFlg = false;
		String userId = SessionUserHolderUtil.getLoginId();
		config.setOperator(userId);
		Map<String, String> resultMap;
		try {
			if (null != id) {
				resultMap = chargeBackService.updateChargeBackConfig(MapUtil
						.bean2map(config));
			} else {
				resultMap = chargeBackService.addChargeBackConfig(MapUtil
						.bean2map(config));
			}

			if (ResponseCodeEnum.SUCCESS.getCode().equals(
					resultMap.get("responseCode"))) {
				isFlg = true;
			}

			String json = JSonUtil.toJSonString(isFlg);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("add error:", e);
		}
		return null;
	}
}
