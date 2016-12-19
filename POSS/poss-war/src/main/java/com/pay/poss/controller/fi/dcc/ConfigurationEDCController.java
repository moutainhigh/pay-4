package com.pay.poss.controller.fi.dcc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.dcc.model.EDCConfig;
import com.pay.dcc.service.ConfigurationEDCService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;

public class ConfigurationEDCController extends MultiActionController {
	private String edc;
	private ConfigurationEDCService configurationEDCService;

	public void setConfigurationEDCService(
			ConfigurationEDCService configurationEDCService) {
		this.configurationEDCService = configurationEDCService;
	}

	public void setEdc(String edc) {
		this.edc = edc;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(edc);
	}

	public ModelAndView saveEDCConfiguration(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * String markup=request.getParameter("markup"); String[]
		 * currencyCodes=request.getParameterValues("currencyCode");
		 */
		String json = request.getParameter("json");
		String[] currencyCodeMarkup = json.split("＆");// 一天数据 多个币种对应对应相同的markup
		EDCConfig edcConfig = new EDCConfig();
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil
				.getSessionUserHolder();
		edcConfig.setStatus("1");
		edcConfig.setCreateTime(new Date());
		edcConfig.setUpdateTime(new Date());
		edcConfig.setOperator(sessionUserHolder.getUsername());
		configurationEDCService.saveEDCConfiguration(edcConfig, currencyCodeMarkup);
		return new ModelAndView(edc);
	}

}
