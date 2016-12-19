/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 帮助中心--个人服务
 * @author fjl
 * @date 2011-5-10
 */
public class PersonalServiceController extends MultiActionController {

	/*
	 * 页面路径
	 */
	private String about;			//了解支付
	private String guide;			//使用指南
	private String kefu;			//在线客服
	private String regLogin;		//注册与登录
	private String accQuery;		//帐户查询
	private String accModify;		//帐户修改
	private String relNameAuth;		//实名认证
	private String charge;			//充值
	private String withdraw;		//提现
	private String payment;			//付款
	private String tradeManage;		//交易管理
	private String tradeRegulation; //交易规则
	private String payQuota;        // 支付额度
	private String securityProduct;	//安全产品
	private String safeClassroom;	//安全课堂
	
	/**
	 * 了解支付
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView about(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(about);
	}
	
	/**
	 * 快速使用指南
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView guide(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(guide);
	}
	
	/**
	 * 账户注册登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView registerAndLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(regLogin);
	}
	
	/**
	 * 账户信息查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView accountQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(accQuery);
	}
	
	/**
	 * 账户信息修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView accountModify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(accModify);
	}
	
	 
	 /**
	  * 实名认证
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView realNameAuthentication(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(relNameAuth);
	}
	
	/**
	  * 充值
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView charge(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(charge);
	}
	
	/**
	  * 提现
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView withdraw(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(withdraw);
	}
	
	/**
	  * 付款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView payment(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(payment);
	}
	
	/**
	  * 交易管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tradeManage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(tradeManage);
	}
	
	/**
	  * 交易规则
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tradeRegulation(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(tradeRegulation);
	}
	
	/**
	 * 支付额度
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView payQuota(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(payQuota);
	}
	
	/**
	  * 安全产品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView securityProduct (HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return new ModelAndView(securityProduct);
	}
	
	/**
	 * 安全课堂
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView safeClassroom (HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(safeClassroom);
	}
	
	/**
	 * 在线客服
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView kefu (HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(kefu);
	}
	
	

	/**
	 * @param about the about to set
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * @param guide the guide to set
	 */
	public void setGuide(String guide) {
		this.guide = guide;
	}

	/**
	 * @param regLogin the regLogin to set
	 */
	public void setRegLogin(String regLogin) {
		this.regLogin = regLogin;
	}

	/**
	 * @param accQuery the accQuery to set
	 */
	public void setAccQuery(String accQuery) {
		this.accQuery = accQuery;
	}

	/**
	 * @param accModify the accModify to set
	 */
	public void setAccModify(String accModify) {
		this.accModify = accModify;
	}

	/**
	 * @param relNameAuth the relNameAuth to set
	 */
	public void setRelNameAuth(String relNameAuth) {
		this.relNameAuth = relNameAuth;
	}

	/**
	 * @param charge the charge to set
	 */
	public void setCharge(String charge) {
		this.charge = charge;
	}

	/**
	 * @param withdraw the withdraw to set
	 */
	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @param tradeManage the tradeManage to set
	 */
	public void setTradeManage(String tradeManage) {
		this.tradeManage = tradeManage;
	}

	/**
	 * @param tradeRegulation the tradeRegulation to set
	 */
	public void setTradeRegulation(String tradeRegulation) {
		this.tradeRegulation = tradeRegulation;
	}

	/**
	 * @param securityProduct the securityProduct to set
	 */
	public void setSecurityProduct(String securityProduct) {
		this.securityProduct = securityProduct;
	}

	public void setSafeClassroom(String safeClassroom) {
		this.safeClassroom = safeClassroom;
	}

	public void setKefu(String kefu) {
		this.kefu = kefu;
	}
	
	public void setPayQuota(String payQuota) {
		this.payQuota = payQuota;
	}
}
