package com.pay.app.controller.base.foot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class FootManageController extends MultiActionController{
	//客服中心
	private String customerService;
	//合作伙伴
	private String partners;
	//关于支付
	private String aboutView;
	//商户签约服务
	private String businessView;
	//安全保障
	private String securityView;
	//社会责任
	private String socialResponsView;
	//隐私申明
	private String privacyView;
	//风险控制体系
	private String riskControlView;
	//风险防范指南
	private String riskguideView;
	//合作商户
	private String partnersMerchant;
	
	public ModelAndView riskguideServices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(riskguideView);
	}
	
	
	public ModelAndView riskControlServices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(riskControlView);
	}
	
	public ModelAndView privacyServices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(privacyView);
	}
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(aboutView);
	}
	
	public ModelAndView businessSevices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(businessView);
	}
	
	public ModelAndView securityService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(securityView);
	}
	
	public ModelAndView customerService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(customerService);
	}
	
	public ModelAndView partners(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(partners);
	}
	
	public ModelAndView socialRespons(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(socialResponsView);
	}
	
	public ModelAndView partnersMerchant(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(partnersMerchant);
	}

	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	public String getPartners() {
		return partners;
	}

	public void setPartners(String partners) {
		this.partners = partners;
	}

	public void setAboutView(String aboutView) {
		this.aboutView = aboutView;
	}

	public void setBusinessView(String businessView) {
		this.businessView = businessView;
	}

	public void setSecurityView(String securityView) {
		this.securityView = securityView;
	}

	public void setSocialResponsView(String socialResponsView) {
		this.socialResponsView = socialResponsView;
	}

	public void setPrivacyView(String privacyView) {
		this.privacyView = privacyView;
	}
	public void setRiskControlView(String riskControlView) {
		this.riskControlView = riskControlView;
	}
	public void setRiskguideView(String riskguideView) {
		this.riskguideView = riskguideView;
	}
	public String getPartnersMerchant() {
		return partnersMerchant;
	}
	public void setPartnersMerchant(String partnersMerchant) {
		this.partnersMerchant = partnersMerchant;
	}
}
