package com.pay.gateway.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.gateway.client.TxncoreClientService;


/**
 * 下单系统存活检测
 * @author peiyu
 * @since  2015年8月13日21:35:44
 */
public class SystemDetectionController extends MultiActionController{
	
	private static Logger logger = LoggerFactory.getLogger(SystemDetectionController.class);
	
	private TxncoreClientService txncoreClientService;
     
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String memberCode = request.getParameter("memberCode");
			String orgCode = request.getParameter("orgCode");
			String resultCode = txncoreClientService.systemDetection(memberCode,orgCode);
			
			logger.info("webgate[ memberCode: "+memberCode+" ,orgCode: "+orgCode+" ,resultCode: "+resultCode+"]");
			response.getWriter().print(resultCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
}
