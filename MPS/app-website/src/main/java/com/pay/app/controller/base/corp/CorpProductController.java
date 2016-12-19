package com.pay.app.controller.base.corp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class CorpProductController extends MultiActionController{
	//企业产品列表
	private String productList;
	
	//人民币支付
	private String rmbPayProduct;
	//充值卡支付
	private String rechargeCardPayProduct;
	//手机支付
	private String mobilePayProduct;
	//国际汇款
	private String interMoneyTransferProduct;
	//担保支付
	private String guaranteePayProduct;
	//信用卡支付
	private String creditCardPayProduct;
	//企业支付
	private String corpPayProduct;
	//企业网银支付
	private String corpNetBankProduct;
	
	public ModelAndView productList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(productList);
	}
	
	public ModelAndView rmbPayProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(rmbPayProduct);
		mv.addObject("select", "rmbPayProduct");
		return mv;
		//return new ModelAndView(rmbPayProduct);
	}
	public ModelAndView rechargeCardPayProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(rechargeCardPayProduct);
		mv.addObject("select", "rechargeCardPayProduct");
		return mv;
		//return new ModelAndView(rechargeCardPayProduct);
	}
	public ModelAndView mobilePayProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(mobilePayProduct);
		mv.addObject("select", "mobilePayProduct");
		return mv;
		//return new ModelAndView(mobilePayProduct);
	}
	public ModelAndView interMoneyTransferProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(interMoneyTransferProduct);
		mv.addObject("select", "interMoneyTransferProduct");
		return mv;
		//return new ModelAndView(interMoneyTransferProduct);
	}
	public ModelAndView guaranteePayProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(guaranteePayProduct);
		mv.addObject("select", "guaranteePayProduct");
		return mv;
		//return new ModelAndView(guaranteePayProduct);
	}
	public ModelAndView creditCardPayProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(creditCardPayProduct);
		mv.addObject("select", "creditCardPayProduct");
		return mv;
		//return new ModelAndView(creditCardPayProduct);
	}
	public ModelAndView corppaypayProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(corpPayProduct);
		mv.addObject("select", "corppaypayProduct");
		return mv;
		//return new ModelAndView(corppaypayProduct);
	}
	public ModelAndView corpNetBankProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(corpNetBankProduct);
		mv.addObject("select", "corpNetBankProduct");
		return mv;
		//return new ModelAndView(corpNetBankProduct);
	}
	

	public String getProductList() {
		return productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public String getRmbPayProduct() {
		return rmbPayProduct;
	}

	public void setRmbPayProduct(String rmbPayProduct) {
		this.rmbPayProduct = rmbPayProduct;
	}

	public String getRechargeCardPayProduct() {
		return rechargeCardPayProduct;
	}

	public void setRechargeCardPayProduct(String rechargeCardPayProduct) {
		this.rechargeCardPayProduct = rechargeCardPayProduct;
	}

	public String getMobilePayProduct() {
		return mobilePayProduct;
	}

	public void setMobilePayProduct(String mobilePayProduct) {
		this.mobilePayProduct = mobilePayProduct;
	}

	public String getInterMoneyTransferProduct() {
		return interMoneyTransferProduct;
	}

	public void setInterMoneyTransferProduct(String interMoneyTransferProduct) {
		this.interMoneyTransferProduct = interMoneyTransferProduct;
	}

	public String getGuaranteePayProduct() {
		return guaranteePayProduct;
	}

	public void setGuaranteePayProduct(String guaranteePayProduct) {
		this.guaranteePayProduct = guaranteePayProduct;
	}

	public String getCreditCardPayProduct() {
		return creditCardPayProduct;
	}

	public void setCreditCardPayProduct(String creditCardPayProduct) {
		this.creditCardPayProduct = creditCardPayProduct;
	}

	public String getCorpPayProduct() {
		return corpPayProduct;
	}

	public void setCorpPayProduct(String corpPayProduct) {
		this.corpPayProduct = corpPayProduct;
	}

	public String getCorpNetBankProduct() {
		return corpNetBankProduct;
	}

	public void setCorpNetBankProduct(String corpNetBankProduct) {
		this.corpNetBankProduct = corpNetBankProduct;
	}
	
}
