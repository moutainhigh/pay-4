/**
 *  File: CenterHelpController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-27   single_zhang     Create
 *
 */
package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;



public class HelpController extends MultiActionController {
	private String securityType;
	private String type;
	
	//安全合作
	private String helpSCooperation;
	//安全策略
	private String helpSStrategy;
	//帮助服务列表
	private String helpSQuestion;
	//什么是安全控件
	private String helpSProduct;
	//支付密码输入不了
	private String helpSControl;
	//安全中心类表
	private String helpSecuritys;

	//充值退回 
	private String helpCChargeBack;
	//收银台列表
	private String helpCheckouts;
	//充值
	private String helpCRecharge;
	//提现
	private String helpCWithdraw;
	
	//我要付款列表
	private String helpPayPays;
	//我要付款详细
	private String helpPayDetail;
	
	//其他
	private String helpOther;
	//联系我们
	private String helpOContactus;
	//使用技巧
	private String helpOPracticalSkills;
	//其他问题
	private String helpOtherQuestion;
	
	//个人协议
	private String helpPersonProtocols;
	//协议明细
	private String helpPPDetail;
	//新手教程首页
	private String helpNewTutorial;
	
	private String helpCorpProtocols;
	
	
	private String helpCorpDetail;
	
	public ModelAndView helpNewTutorial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(helpNewTutorial);
	}
	
	public ModelAndView helpPersonProtocols(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		return new ModelAndView(helpPersonProtocols).addObject("type", type);
	}
	
	public ModelAndView helpPPDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		return new ModelAndView(helpPPDetail).addObject("type", type);
	}
	
	public ModelAndView helpCorpProtocols(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		return new ModelAndView(helpCorpProtocols).addObject("type", type);
	}
	
	public ModelAndView helpCorpDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type")==null?"1":request.getParameter("type");
		return new ModelAndView(helpCorpDetail).addObject("type", type);
	}
	
	public ModelAndView helpOther(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		securityType = request.getParameter("securityType");
		return new ModelAndView(helpOther).addObject("securityType", securityType);
	}
	
	public ModelAndView helpOContactus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpOContactus);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpOPracticalSkills(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpOPracticalSkills);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpOtherQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpOtherQuestion);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helppayPays(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		securityType = request.getParameter("securityType");
		return new ModelAndView(helpPayPays).addObject("securityType", securityType);
	}
	
	public ModelAndView helppayPayDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		return new ModelAndView(helpPayDetail).addObject("type", type);
	}
	
	public ModelAndView helpCheckouts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		securityType = request.getParameter("securityType");
		return new ModelAndView(helpCheckouts).addObject("securityType", securityType);
	}
	
	public ModelAndView helpCChargeBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpCChargeBack);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpCRecharge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpCRecharge);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpCWithdraw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpCWithdraw);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpSecuritys(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		securityType = request.getParameter("securityType");
		return new ModelAndView(helpSecuritys).addObject("securityType", securityType);
	}
	
	public ModelAndView helpSCooperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpSCooperation);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpSStrategy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpSStrategy);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpSQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpSQuestion);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpSProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpSProduct);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	
	public ModelAndView helpSControl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		type = request.getParameter("type");
		securityType = request.getParameter("securityType");
		ModelAndView mv = new ModelAndView(helpSControl);
		mv.addObject("type",type);
		mv.addObject("securityType", securityType);
		return mv;
	}
	

	public String getHelpSCooperation() {
		return helpSCooperation;
	}

	public void setHelpSCooperation(String helpSCooperation) {
		this.helpSCooperation = helpSCooperation;
	}

	public String getHelpSStrategy() {
		return helpSStrategy;
	}

	public void setHelpSStrategy(String helpSStrategy) {
		this.helpSStrategy = helpSStrategy;
	}

	public String getHelpSQuestion() {
		return helpSQuestion;
	}

	public void setHelpSQuestion(String helpSQuestion) {
		this.helpSQuestion = helpSQuestion;
	}

	public String getHelpSProduct() {
		return helpSProduct;
	}

	public void setHelpSProduct(String helpSProduct) {
		this.helpSProduct = helpSProduct;
	}

	public String getHelpSControl() {
		return helpSControl;
	}

	public void setHelpSControl(String helpSControl) {
		this.helpSControl = helpSControl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHelpSecuritys() {
		return helpSecuritys;
	}

	public void setHelpSecuritys(String helpSecuritys) {
		this.helpSecuritys = helpSecuritys;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getHelpCChargeBack() {
		return helpCChargeBack;
	}

	public void setHelpCChargeBack(String helpCChargeBack) {
		this.helpCChargeBack = helpCChargeBack;
	}

	public String getHelpCheckouts() {
		return helpCheckouts;
	}

	public void setHelpCheckouts(String helpCheckouts) {
		this.helpCheckouts = helpCheckouts;
	}

	public String getHelpCRecharge() {
		return helpCRecharge;
	}

	public void setHelpCRecharge(String helpCRecharge) {
		this.helpCRecharge = helpCRecharge;
	}

	public String getHelpCWithdraw() {
		return helpCWithdraw;
	}

	public void setHelpCWithdraw(String helpCWithdraw) {
		this.helpCWithdraw = helpCWithdraw;
	}

	public String getHelppayPays() {
		return helpPayPays;
	}

	public void setHelppayPays(String helppayPays) {
		this.helpPayPays = helppayPays;
	}

	public String getHelpPayPays() {
		return helpPayPays;
	}

	public void setHelpPayPays(String helpPayPays) {
		this.helpPayPays = helpPayPays;
	}

	public String getHelpPayDetail() {
		return helpPayDetail;
	}

	public void setHelpPayDetail(String helpPayDetail) {
		this.helpPayDetail = helpPayDetail;
	}

	public String getHelpOther() {
		return helpOther;
	}

	public void setHelpOther(String helpOther) {
		this.helpOther = helpOther;
	}

	public String getHelpOContactus() {
		return helpOContactus;
	}

	public void setHelpOContactus(String helpOContactus) {
		this.helpOContactus = helpOContactus;
	}

	public String getHelpOPracticalSkills() {
		return helpOPracticalSkills;
	}

	public void setHelpOPracticalSkills(String helpOPracticalSkills) {
		this.helpOPracticalSkills = helpOPracticalSkills;
	}

	public String getHelpOtherQuestion() {
		return helpOtherQuestion;
	}

	public void setHelpOtherQuestion(String helpOtherQuestion) {
		this.helpOtherQuestion = helpOtherQuestion;
	}

	public String getHelpPersonProtocols() {
		return helpPersonProtocols;
	}

	public void setHelpPersonProtocols(String helpPersonProtocols) {
		this.helpPersonProtocols = helpPersonProtocols;
	}

	public String getHelpPPDetail() {
		return helpPPDetail;
	}

	public void setHelpPPDetail(String helpPPDetail) {
		this.helpPPDetail = helpPPDetail;
	}

	public String getHelpNewTutorial() {
		return helpNewTutorial;
	}

	public void setHelpNewTutorial(String helpNewTutorial) {
		this.helpNewTutorial = helpNewTutorial;
	}

	public String getHelpCorpProtocols() {
		return helpCorpProtocols;
	}

	public void setHelpCorpProtocols(String helpCorpProtocols) {
		this.helpCorpProtocols = helpCorpProtocols;
	}

	public String getHelpCorpDetail() {
		return helpCorpDetail;
	}

	public void setHelpCorpDetail(String helpCorpDetail) {
		this.helpCorpDetail = helpCorpDetail;
	}

	
}
