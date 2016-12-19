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



public class ShowHelpController extends MultiActionController {
	//演示大厅
	private String index;

	//我的账户
	private String myAccountRegister;
	private String myAccountRecharge;
	private String myAccountPayment;	
	//生活管家
	private String lifeShuidianmei;
	private String lifeRent;
	private String lifelivingExpenses;
	private String lifeAlimony;
	private String lifeMortgagerPayment;
	private String lifeInterBank;
	private String lifePresent;
	private String lifeAACollect;
	private String lifeCommunication;
	private String lifeMobileRecharge;
	private String lifeCreditCardPayment;
	//交易规则
	private String transactionPayment;
	private String transactionRechargeAndWithdraw;
	private String transactionRefund;
	
	//安全中心
	private String securityPayPassword;
	private String securityQuestion;
	private String securityMatrixCard;
	private String securityGreet;

	
	//首页
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(index);
		mv.addObject("method", "index");
		return mv;
	}

	//我的账户
	public ModelAndView myAccountRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountRegister);
		mv.addObject("method", "myAccountRegister");
		return mv;
	}

	public ModelAndView myAccountRecharge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountRecharge);
		mv.addObject("method", "myAccountRecharge");
		return mv;
	}

	public ModelAndView myAccountPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountPayment);
		mv.addObject("method", "myAccountPayment");
		return mv;
	}
	
	//生活管家
	public ModelAndView lifeShuidianmei(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeShuidianmei);
		mv.addObject("method", "lifeShuidianmei");
		return mv;
	}

	public ModelAndView lifeRent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeRent);
		mv.addObject("method", "lifeRent");
		return mv;
	}

	public ModelAndView lifelivingExpenses(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifelivingExpenses);
		mv.addObject("method", "lifelivingExpenses");
		return mv;
	}

	public ModelAndView lifeAlimony(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeAlimony);
		mv.addObject("method", "lifeAlimony");
		return mv;
	}

	public ModelAndView lifeMortgagerPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeMortgagerPayment);
		mv.addObject("method", "lifeMortgagerPayment");
		return mv;
	}

	public ModelAndView lifeInterBank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeInterBank);
		mv.addObject("method", "lifeInterBank");
		return mv;
	}

	public ModelAndView lifePresent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifePresent);
		mv.addObject("method", "lifePresent");
		return mv;
	}

	public ModelAndView lifeAACollect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeAACollect);
		mv.addObject("method", "lifeAACollect");
		return mv;
	}

	public ModelAndView lifeCommunication(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeCommunication);
		mv.addObject("method", "lifeCommunication");
		return mv;
	}
	public ModelAndView lifeMobileRecharge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeMobileRecharge);
		mv.addObject("method", "lifeMobileRecharge");
		return mv;
	}
	public ModelAndView lifeCreditCardPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeCreditCardPayment);
		mv.addObject("method", "lifeCreditCardPayment");
		return mv;
	}
	
	//交易记录
	public ModelAndView transactionPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionPayment);
		mv.addObject("method", "transactionPayment");
		return mv;
	}

	
	public ModelAndView transactionRechargeAndWithdraw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionRechargeAndWithdraw);
		mv.addObject("method", "transactionRechargeAndWithdraw");
		return mv;
	}
	

	
	public ModelAndView transactionRefund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionRefund);
		mv.addObject("method", "transactionRefund");
		return mv;
	}
	
	
	
	//安全中心
	public ModelAndView securityPayPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(securityPayPassword);
		mv.addObject("method", "securityPayPassword");
		return mv;
	}
	public ModelAndView securityQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(securityQuestion);
		mv.addObject("method", "securityQuestion");
		return mv;
	}
	public ModelAndView securityMatrixCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(securityMatrixCard);
		mv.addObject("method", "securityMatrixCard");
		return mv;
	}
	public ModelAndView securityGreet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(securityGreet);
		mv.addObject("method", "securityGreet");
		return mv;
	}
	
	
	//-----------set get ---------------

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getMyAccountRegister() {
		return myAccountRegister;
	}

	public void setMyAccountRegister(String myAccountRegister) {
		this.myAccountRegister = myAccountRegister;
	}

	public String getTransactionPayment() {
		return transactionPayment;
	}

	public void setTransactionPayment(String transactionPayment) {
		this.transactionPayment = transactionPayment;
	}



	public String getTransactionRechargeAndWithdraw() {
		return transactionRechargeAndWithdraw;
	}

	public void setTransactionRechargeAndWithdraw(
			String transactionRechargeAndWithdraw) {
		this.transactionRechargeAndWithdraw = transactionRechargeAndWithdraw;
	}

	public String getTransactionRefund() {
		return transactionRefund;
	}

	public void setTransactionRefund(String transactionRefund) {
		this.transactionRefund = transactionRefund;
	}

	public String getLifeShuidianmei() {
		return lifeShuidianmei;
	}

	public void setLifeShuidianmei(String lifeShuidianmei) {
		this.lifeShuidianmei = lifeShuidianmei;
	}

	public String getLifeRent() {
		return lifeRent;
	}

	public void setLifeRent(String lifeRent) {
		this.lifeRent = lifeRent;
	}

	public String getLifelivingExpenses() {
		return lifelivingExpenses;
	}

	public void setLifelivingExpenses(String lifelivingExpenses) {
		this.lifelivingExpenses = lifelivingExpenses;
	}

	public String getLifeAlimony() {
		return lifeAlimony;
	}

	public void setLifeAlimony(String lifeAlimony) {
		this.lifeAlimony = lifeAlimony;
	}

	public String getLifeMortgagerPayment() {
		return lifeMortgagerPayment;
	}

	public void setLifeMortgagerPayment(String lifeMortgagerPayment) {
		this.lifeMortgagerPayment = lifeMortgagerPayment;
	}

	public String getLifeInterBank() {
		return lifeInterBank;
	}

	public void setLifeInterBank(String lifeInterBank) {
		this.lifeInterBank = lifeInterBank;
	}

	public String getLifePresent() {
		return lifePresent;
	}

	public void setLifePresent(String lifePresent) {
		this.lifePresent = lifePresent;
	}

	public String getLifeAACollect() {
		return lifeAACollect;
	}

	public void setLifeAACollect(String lifeAACollect) {
		this.lifeAACollect = lifeAACollect;
	}

	public String getLifeCommunication() {
		return lifeCommunication;
	}

	public void setLifeCommunication(String lifeCommunication) {
		this.lifeCommunication = lifeCommunication;
	}

	public String getLifeMobileRecharge() {
		return lifeMobileRecharge;
	}

	public void setLifeMobileRecharge(String lifeMobileRecharge) {
		this.lifeMobileRecharge = lifeMobileRecharge;
	}

	public String getSecurityPayPassword() {
		return securityPayPassword;
	}

	public void setSecurityPayPassword(String securityPayPassword) {
		this.securityPayPassword = securityPayPassword;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityMatrixCard() {
		return securityMatrixCard;
	}

	public void setSecurityMatrixCard(String securityMatrixCard) {
		this.securityMatrixCard = securityMatrixCard;
	}

	public String getSecurityGreet() {
		return securityGreet;
	}

	public void setSecurityGreet(String securityGreet) {
		this.securityGreet = securityGreet;
	}

	public String getLifeCreditCardPayment() {
		return lifeCreditCardPayment;
	}

	public void setLifeCreditCardPayment(String lifeCreditCardPayment) {
		this.lifeCreditCardPayment = lifeCreditCardPayment;
	}

	public String getMyAccountRecharge() {
		return myAccountRecharge;
	}

	public void setMyAccountRecharge(String myAccountRecharge) {
		this.myAccountRecharge = myAccountRecharge;
	}

	public String getMyAccountPayment() {
		return myAccountPayment;
	}

	public void setMyAccountPayment(String myAccountPayment) {
		this.myAccountPayment = myAccountPayment;
	}




}
