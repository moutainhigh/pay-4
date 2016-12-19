/**
 *  File: CertificationController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-18   lihua     Create
 *
 */
package com.pay.app.controller.base.certification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.dao.announcement.impl.AnnouncementDAOImpl;

/**
 * 
 */
public class CertificationController extends MultiActionController {

	// 实名认证首页
	private String certificationindexpage;
	// 实名认证选择认证方式页面
	private String certificationmodepage;
	// 实名认证填写个人信息页面
	private String certificationuserinfopage;
	// 实名认证填写银行卡信息页面
	private String certificationbankbindpage;
	// 实名认证确认个人和银行卡信息页面
	private String certificationbankresultpage;
	// 实名认证确认信息结果页面
	private String certificationuserinfosuccesspage;
	// 实名认证输入打款金额
	private String certificationinamountindexpage;
	// 实名认证确认打款金额
	private String certificationinamountpage;
	// 实名认证成功
	private String success;

	/**
	 * 跳转至实名认证首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tocertificationindex(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(certificationindexpage);
	}

	/**
	 * 跳转至实名认证选择认证方式页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tocertificationmode(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(certificationmodepage);
	}

	/**
	 * 跳转至填写个人信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tocertificationuserinfo(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(certificationuserinfopage);
	}

	/**
	 * 跳转至填写银行卡信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tocertificationbankbind(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(certificationbankbindpage);
	}

	/**
	 * 实名认证向用户银行汇款
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView certificationremitmoney(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(certificationuserinfosuccesspage);
	}

	/**
	 * 跳转至实名认证输入打款金额页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tocertificationinamountindex(
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(certificationinamountindexpage);
	}

	/**
	 * 跳转至实名认证确认打款金额页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tocertificationinamount(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(certificationinamountpage);
	}

	/**
	 * 实名认证提交
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView certification(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(success);
	}

	public void setCertificationindexpage(String certificationindexpage) {
		this.certificationindexpage = certificationindexpage;
	}
	public void setCertificationmodepage(String certificationmodepage) {
		this.certificationmodepage = certificationmodepage;
	}
	public void setCertificationuserinfopage(String certificationuserinfopage) {
		this.certificationuserinfopage = certificationuserinfopage;
	}
	public void setCertificationbankbindpage(String certificationbankbindpage) {
		this.certificationbankbindpage = certificationbankbindpage;
	}
	public void setCertificationbankresultpage(
			String certificationbankresultpage) {
		this.certificationbankresultpage = certificationbankresultpage;
	}

	public void setCertificationuserinfosuccesspage(
			String certificationuserinfosuccesspage) {
		this.certificationuserinfosuccesspage = certificationuserinfosuccesspage;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public void setCertificationinamountindexpage(
			String certificationinamountindexpage) {
		this.certificationinamountindexpage = certificationinamountindexpage;
	}

	public void setCertificationinamountpage(String certificationinamountpage) {
		this.certificationinamountpage = certificationinamountpage;
	}

}
