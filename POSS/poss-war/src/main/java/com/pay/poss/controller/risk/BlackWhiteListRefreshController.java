package com.pay.poss.controller.risk;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.RiskClientService;


/**
 * 黑白名单刷新
 * 
 * @Description
 * @file PartnerOrderFilterRuleController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class BlackWhiteListRefreshController extends MultiActionController{
	private final Log logger = LogFactory.getLog(getClass());
	private RiskClientService  riskClientService;
	/**
	 * 检查数据是否存在
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public ModelAndView refresh(HttpServletRequest request,
			                                HttpServletResponse response) 
			                                		{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Map<String,String> map = riskClientService.refreshBlackWhiteList();
		String responseCode = map.get("responseCode");
		String newAddAmount = map.get("newAddAmount");
		String oldAmount= map.get("oldAmount");
		String nowAmount= map.get("nowAmount");
		
		if("0000".equals(responseCode)){
			out.write("刷新成功,原来条数： "+oldAmount+",新增条数："+newAddAmount+",现在总数："+nowAmount);
		}else{
			out.write("刷新失败");
		}
		out.flush();
		out.close();
		return null;
		
	}
	public void setRiskClientService(RiskClientService riskClientService) {
		this.riskClientService = riskClientService;
	}

	
}
