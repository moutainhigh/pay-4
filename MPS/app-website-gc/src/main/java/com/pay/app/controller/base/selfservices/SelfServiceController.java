/**
 *  File: SelfServiceController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-30   single_zhang     Create
 *
 */
package com.pay.app.controller.base.selfservices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 自助服务
 */
public class SelfServiceController extends MultiActionController {
	
	private String regpay;
	
	private String findpwd;
	
	private String recharge;
	
	private String uploginpwd;
	
	private String uppaypwd;
	
	private String szPwd;
	
	private String upsecurity;
	
	private String securitylogin;
	
	
	private String updatePhone;
	
	
	
	/**
	 * 注册--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView regpay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(regpay);
	}
	
	/**
	 * 修改登录密码--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView uploginpwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(uploginpwd);
	}
	
	/**
	 * 修改支付密码--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView uppaypwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(uppaypwd);
	}
	/**
	 * 网银充值
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView recharge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(recharge);
	}
	
	/**
	 * 找回支付密码--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView findpwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(findpwd);
	}
	
	/**
	 * 设置支付密码--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView szPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(szPwd);
	}
	
	/**
	 * 修改安全保护问题--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView upsecurity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(upsecurity);
	}
	
	/**
	 * 安全登录--自助服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView securitylogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	     return new ModelAndView(securitylogin);
	}
	/** set 方法**/

	public void setFindpwd(String findpwd) {
		this.findpwd = findpwd;
	}

	public String getRegpay() {
		return regpay;
	}

	public void setRegpay(String regpay) {
		this.regpay = regpay;
	}

	public String getUpdatePhone() {
		return updatePhone;
	}

	public void setUpdatePhone(String updatePhone) {
		this.updatePhone = updatePhone;
	}

	public String getFindpwd() {
		return findpwd;
	}

	public String getRecharge() {
		return recharge;
	}

	public String getUploginpwd() {
		return uploginpwd;
	}

	public String getUppaypwd() {
		return uppaypwd;
	}

	public String getSzPwd() {
		return szPwd;
	}

	public String getUpsecurity() {
		return upsecurity;
	}

	public String getSecuritylogin() {
		return securitylogin;
	}

	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}

	public void setUploginpwd(String uploginpwd) {
		this.uploginpwd = uploginpwd;
	}

	public void setUppaypwd(String uppaypwd) {
		this.uppaypwd = uppaypwd;
	}

	public void setUpsecurity(String upsecurity) {
		this.upsecurity = upsecurity;
	}

	public void setSzPwd(String szPwd) {
		this.szPwd = szPwd;
	}

	public void setSecuritylogin(String securitylogin) {
		this.securitylogin = securitylogin;
	}
	
}

