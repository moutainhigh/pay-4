/**
 *  File: CenterHelpController.java
 *  Description:
 *  Copyright 2006-2010 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2010-8-27   single_zhang     Create
 *
 */
package com.pay.app.controller.help;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


import com.pay.app.dto.BaseDTO;
import com.pay.app.service.announcement.AnnouncementService;

/**
 * 
 */
public class CenterHelpController extends MultiActionController {

	private String helpView;
	private String EnterprisecenterHelp_AccessView;
	public void setEnterprisecenterHelp_CalculateView(
			String enterprisecenterHelpCalculateView) {
		EnterprisecenterHelp_CalculateView = enterprisecenterHelpCalculateView;
	}
	private String EnterprisecenterHelp_CalculateView;

	private AnnouncementService announcementService;
	
	// 注册和激活
	private String registerAndactiveView;
	// 账户密码
	private String accountPassorwdView;
	// 支付实名认证
	private String authenticationView;
	// 信息提醒设置
	private String setMessageView;

	// 支付付款
	private String PaymentView;

	// 交易规则
	private String TransactionView;

	// 消费记录
	private String ConsumptionrecordsView;

	// 生活助手
	private String LifeAssistantView;

	// 安全控件
	private String SecuritycontrolView;
	// 安全产品
	private String SecurityProductView;
	// 安全合作
	private String SecuritycooperationView;
	// 安全问题
	private String SecurityissueView;
	// 安全策略
	private String SecurityPolicyView;

	// 充值
	private String RechargeView;
	// 充值退回
	private String RechargebackView;
	// 提现
	private String WithdrawView;

	// 实用技巧
	private String PracticalSkillsView;
	//其他问题
	private String OtherproblemsView;
	//联系我们
	private String ContactusView;
	//合作商户
	private String BusinesscooperationView;
	
	
	//如何成为支付合作商户
	private String BecomepartnerView;
	//如何进行退款
	private String RefundView;
	//如何设置操作员
    private String SetoperatorView;
    //商户如何查询订单
    private String QueryorderView;
    //商户如何接入
    private String AccessView;
    //商户如何进行订单确认
    private String OrderConfirmationView;
    //商户忘记登陆、支付密码怎么办
    private String ForgetPasswordView;
    //支付平台有哪些支付业务
    private String PaymentServiceView;
    //支付支付业务如何收取交易服务费
    private String HowtopayView;
    
    //清算帮助中心
    //为什么要下载批量付款的模版文件
    private String BatchDownloadView;
	//使用批量付款至银行账户功能，收款方什么时候能收到款项呢？（是否三工作日有待确认）
    private String CollectiontimeView;
    //企业用户出款异地复核业务流程
    private String BusinessreviewView;
    //企业用户出款异地复核功能说明
    private String ReviewNoteView;
    //没有验证的银行账户可以用来提现吗？
    private String WithdrawconditionsView;
    //在添加银行账户时，为什么要正确填写银行开户行名称？
    private String FillBankView;
    //用来提现的银行卡必须是本人的吗？
    private String WithdrawCardView;
    //什么是充值退款？
    private String RechargerefundView;
    //我申请提现到银行卡了，多久能收到钱？
    private String WithdrawTimeView;
    //充值退款的到账时间
    private String RefundaccounttimeView;
    //完成付款到银行账户操作后，对方几天能收到钱？
    private String OthermoneytimeView;


  
    public void setEnterprisecenterHelp_AccessView(
			String enterprisecenterHelpAccessView) {
		EnterprisecenterHelp_AccessView = enterprisecenterHelpAccessView;
	}


	public void setBatchDownloadView(String batchDownloadView) {
		BatchDownloadView = batchDownloadView;
	}

	public void setCollectiontimeView(String collectiontimeView) {
		CollectiontimeView = collectiontimeView;
	}

	public void setBusinessreviewView(String businessreviewView) {
		BusinessreviewView = businessreviewView;
	}

	public void setReviewNoteView(String reviewNoteView) {
		ReviewNoteView = reviewNoteView;
	}

	public void setWithdrawconditionsView(String withdrawconditionsView) {
		WithdrawconditionsView = withdrawconditionsView;
	}

	public void setFillBankView(String fillBankView) {
		FillBankView = fillBankView;
	}

	public void setWithdrawCardView(String withdrawCardView) {
		WithdrawCardView = withdrawCardView;
	}

	public void setRechargerefundView(String rechargerefundView) {
		RechargerefundView = rechargerefundView;
	}

	public void setWithdrawTimeView(String withdrawTimeView) {
		WithdrawTimeView = withdrawTimeView;
	}

	public void setRefundaccounttimeView(String refundaccounttimeView) {
		RefundaccounttimeView = refundaccounttimeView;
	}

	public void setOthermoneytimeView(String othermoneytimeView) {
		OthermoneytimeView = othermoneytimeView;
	}    
	public void setBecomepartnerView(String becomepartnerView) {
		BecomepartnerView = becomepartnerView;
	}

	public void setRefundView(String refundView) {
		RefundView = refundView;
	}

	public void setSetoperatorView(String setoperatorView) {
		SetoperatorView = setoperatorView;
	}

	public void setQueryorderView(String queryorderView) {
		QueryorderView = queryorderView;
	}

	public void setAccessView(String accessView) {
		AccessView = accessView;
	}

	public void setOrderConfirmationView(String orderConfirmationView) {
		OrderConfirmationView = orderConfirmationView;
	}

	public void setForgetPasswordView(String forgetPasswordView) {
		ForgetPasswordView = forgetPasswordView;
	}

	public void setPaymentServiceView(String paymentServiceView) {
		PaymentServiceView = paymentServiceView;
	}

	public void setHowtopayView(String howtopayView) {
		HowtopayView = howtopayView;
	}

    
	public void setBusinesscooperationView(String businesscooperationView) {
		BusinesscooperationView = businesscooperationView;
	}

	public void setRegisterAndactiveView(String registerAndactiveView) {
		this.registerAndactiveView = registerAndactiveView;
	}

	public void setAccountPassorwdView(String accountPassorwdView) {
		this.accountPassorwdView = accountPassorwdView;
	}

	public void setAuthenticationView(String authenticationView) {
		this.authenticationView = authenticationView;
	}

	public void setSetMessageView(String setMessageView) {
		this.setMessageView = setMessageView;
	}

	public void setPaymentView(String paymentView) {
		PaymentView = paymentView;
	}

	public void setTransactionView(String transactionView) {
		TransactionView = transactionView;
	}

	public void setConsumptionrecordsView(String consumptionrecordsView) {
		ConsumptionrecordsView = consumptionrecordsView;
	}

	public void setLifeAssistantView(String lifeAssistantView) {
		LifeAssistantView = lifeAssistantView;
	}

	public void setSecuritycontrolView(String securitycontrolView) {
		SecuritycontrolView = securitycontrolView;
	}

	public void setSecurityProductView(String securityProductView) {
		SecurityProductView = securityProductView;
	}

	public void setSecuritycooperationView(String securitycooperationView) {
		SecuritycooperationView = securitycooperationView;
	}

	public void setSecurityissueView(String securityissueView) {
		SecurityissueView = securityissueView;
	}

	public void setSecurityPolicyView(String securityPolicyView) {
		SecurityPolicyView = securityPolicyView;
	}

	public void setRechargeView(String rechargeView) {
		RechargeView = rechargeView;
	}

	public void setRechargebackView(String rechargebackView) {
		RechargebackView = rechargebackView;
	}

	public void setWithdrawView(String withdrawView) {
		WithdrawView = withdrawView;
	}

	public void setPracticalSkillsView(String practicalSkillsView) {
		PracticalSkillsView = practicalSkillsView;
	}

	public void setOtherproblemsView(String otherproblemsView) {
		OtherproblemsView = otherproblemsView;
	}

	public void setContactusView(String contactusView) {
		ContactusView = contactusView;
	}
	
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	/**
	 * 帮助中心
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int topnum=8;
		List list = announcementService.queryTopAnnouncement(topnum);
		return new ModelAndView(helpView).addObject("ggls",list);
	}

	public ModelAndView EnterprisecenterHelp_Access(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(EnterprisecenterHelp_AccessView);
	}
	public ModelAndView EnterprisecenterHelp_Calculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(EnterprisecenterHelp_CalculateView);
	}
	
	public void setHelpView(String helpView) {
		this.helpView = helpView;
	}

	public ModelAndView registerAndactive(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(registerAndactiveView);
	}

	public ModelAndView accountPassorwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(accountPassorwdView);
	}

	public ModelAndView authentication(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(authenticationView);
	}

	public ModelAndView setMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(setMessageView);
	}

	public ModelAndView Payment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(PaymentView);
	}

	public ModelAndView Transaction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(TransactionView);
	}

	public ModelAndView Consumptionrecords(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(ConsumptionrecordsView);
	}

	public ModelAndView LifeAssistant(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(LifeAssistantView);
	}

	public ModelAndView Securitycontrol(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(SecuritycontrolView);
	}

	public ModelAndView SecurityProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(SecurityProductView);
	}

	public ModelAndView Securitycooperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(SecuritycooperationView);
	}

	public ModelAndView Securityissue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(SecurityissueView);
	}

	public ModelAndView SecurityPolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(SecurityPolicyView);
	}

	public ModelAndView Recharge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(RechargeView);
	}

	public ModelAndView Rechargeback(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(RechargebackView);
	}

	public ModelAndView Withdraw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(WithdrawView);
	}

	public ModelAndView PracticalSkills(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(PracticalSkillsView);
	}

	public ModelAndView Otherproblems(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(OtherproblemsView);
	}

	public ModelAndView Contactus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(ContactusView);
	}
	public ModelAndView Businesscooperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(BusinesscooperationView);
	}	
	
	//商户接入
	public ModelAndView Becomepartner(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(BecomepartnerView);
	}	
	public ModelAndView Refund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(RefundView);
	}
	public ModelAndView Setoperator(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(SetoperatorView);
	}
	public ModelAndView Queryorder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(QueryorderView);
	}	
	public ModelAndView Access(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(AccessView);
	}	
	public ModelAndView OrderConfirmation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(OrderConfirmationView);
	}	
	public ModelAndView ForgetPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(ForgetPasswordView);
	}	
	public ModelAndView PaymentService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(PaymentServiceView);
	}	
	public ModelAndView Howtopay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(HowtopayView);
	}
	public ModelAndView BatchDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(BatchDownloadView);
	}	
	public ModelAndView Collectiontime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(CollectiontimeView);
	}
	public ModelAndView Businessreview(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(BusinessreviewView);
	}	
	public ModelAndView ReviewNote(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(ReviewNoteView);
	}
	public ModelAndView Withdrawconditions(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(WithdrawconditionsView);
	}	
	public ModelAndView FillBank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(FillBankView);
	}
	public ModelAndView WithdrawCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(WithdrawCardView);
	}	
	public ModelAndView Rechargerefund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(RechargerefundView);
	}	
	public ModelAndView WithdrawTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(WithdrawTimeView);
	}	
	public ModelAndView Refundaccounttime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(RefundaccounttimeView);
	}
	public ModelAndView Othermoneytime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(OthermoneytimeView);
	}		
}
