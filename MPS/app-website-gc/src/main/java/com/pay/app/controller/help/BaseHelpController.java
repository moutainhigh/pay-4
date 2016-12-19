/**
 *  File: CenterHelpController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-27   single_zhang     Create
 *
 */
package com.pay.app.controller.help;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.announcement.AnnouncementService;
import com.pay.app.validator.AppealValidator;
import com.pay.app.validator.ValidatorDto;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.AppealDto;
import com.pay.base.dto.SuggestDto;
import com.pay.base.service.appeal.AppealService;
import com.pay.inf.exception.AppException;



public class BaseHelpController extends MultiActionController {
	//帮助中心链接
	private String index;
	private String centerHelpAdvice; //建议
	private String centerHelpReport; //举报
	private String centerHelpComplaint; //投诉
	private String redirectView;     
	//我的账户
	private String myAccountRegister;
	private String myAccountRegisterDetails;
	private String myAccountVerify;
	private String myAccountVerifyDetails;
	private String myAccountPassword;
	private String myAccountPasswordDetails;
	private String myAccountException;
	private String myAccountExceptionDetails;
	
	//交易规则
	private String transactionProcess;
	private String transactionRule;
	private String transactionPayment;
	private String transactionQuestion;
	private String transactionProcessDetails;
	private String transactionRuleDetails;
	private String transactionPaymentDetails;
	private String transactionQuestionDetails;
	
	
	//消费记录
	private String consumptionRecord;
	private String consumptionRechargeAndWithdraw;
	private String consumptionEvaluation;
	private String consumptionRecordDetails;
	private String consumptionRechargeAndWithdrawDetails;
	private String consumptionEvaluationDetails;
	
	//生活管家
	private String lifeMyPayment;
	private String lifeMyCollect;
	private String lifeSecuredTransaction;
	private String lifeShuidianmei;
	private String lifeCommunication;
	private String lifeCreditCardPayment;
	private String lifePresent;
	private String lifeRent;
	private String lifeAACollect;
	private String lifelivingExpenses;
	private String lifeAlimony;
	private String lifeInterBank;
	private String lifeMortgagerPayment;

	private String lifeMyPaymentDetails;
	private String lifeMyCollectDetails;
	private String lifeSecuredTransactionDetails;
	private String lifeShuidianmeiDetails;
	private String lifeCommunicationDetails;
	private String lifeCreditCardPaymentDetails;
	private String lifePresentDetails;
	private String lifeRentDetails;
	private String lifeAACollectDetails;
	private String lifelivingExpensesDetails;
	private String lifeAlimonyDetails;
	private String lifeInterBankDetails;
	private String lifeMortgagerPaymentDetails;
	
	//企业服务
	private String enterpriseAccess;
	private String enterpriseCalculate;
	private String enterpriseWithdraw;
	private String enterpriseProtocols;
	private String enterpriseAccessDetails;
	private String enterpriseCalculateDetails;
	private String enterpriseWithdrawDetails;
	private String enterpriseProtocolDetails;
	
	private AnnouncementService announcementService;
	
	private AppealService appealService;
	
	//private FileUpAndDownLoadService fileUpAndDownLoadService;
	
	//帮助中心首页
	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int topnum=8;
		List list = announcementService.queryTopAnnouncement(topnum);
		return new ModelAndView(index).addObject("ggls",list).addObject("method",index);
	}
	
	//显示建议页面
	public ModelAndView centerHelpAdvice(HttpServletRequest request,
			HttpServletResponse response,SuggestDto suggestDto) throws Exception {
		ModelAndView mv = new ModelAndView(centerHelpAdvice);
		mv.addObject("method", "centerHelpAdvice");
		return mv;
	}
	
	//建议
	public ModelAndView advice(HttpServletRequest request,
			HttpServletResponse response,AppealDto appealDto) throws Exception {
		//ModelAndView mv = new ModelAndView(redirectView);
		ValidatorDto vd= AppealValidator.validate(appealDto);
	    String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
        request.getSession().removeAttribute("rand");
        String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
        //mv.addObject("jumpUrl", "/baseHelp.htm?method=centerHelpAdvice");
    	ModelAndView mv = new ModelAndView(centerHelpAdvice);
		mv.addObject("method", "centerHelpAdvice");
	    if(vd.hasErrors()){
            String msgStr=vd.getError();
            mv.addObject("msgStr", msgStr);
            mv.addObject("iconNum","2");
            return mv;
        }
	    String appealCode = appealService.getAppealCode();
	    appealDto.setAppealCode(appealCode);
	    
	    if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { //校验验证码
            mv.addObject("iconNum", "2");
            mv.addObject("msgStr", MessageConvertFactory.getMessage("randCode"));
            mv.addObject("appealDto",appealDto);
            return mv;
        }
		if(saveAppeal(appealDto)){
			mv.addObject("iconNum","1"); //添加成功标记
			mv.addObject("msgStr",ErrorCodeEnum.SUGGEST_ADVICE_SUCCESS.getMessage()); 
			return mv;
		}else{
			mv.addObject("iconNum","2"); //添加失败标记
			mv.addObject("msgStr", ErrorCodeEnum.SUGGEST_ADVICE_FAIL.getMessage());
			return mv;
		}
	}
	
	//显示举报页面
	public ModelAndView centerHelpReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(centerHelpReport);
		mv.addObject("method", "centerHelpReport");
		return mv;
	}
	
	//举报
	public ModelAndView report(HttpServletRequest request,
			HttpServletResponse response,AppealDto appealDto) throws Exception {
		ModelAndView mv = new ModelAndView(redirectView);
		ValidatorDto vd= AppealValidator.validate(appealDto);
		String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
	    request.getSession().removeAttribute("rand");
	    String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
	    mv.addObject("jumpUrl", "/baseHelp.htm?method=centerHelpReport");
	    if(vd.hasErrors()){
    	    String msgStr=vd.getError();
            mv.addObject("msgStr", msgStr);
            mv.addObject("iconNum","2");
            return mv;
        }
	    String appealCode = appealService.getAppealCode();
	    appealDto.setAppealCode(appealCode);
	    
	    if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { //校验验证码
            mv.addObject("iconNum", "2");
            mv.addObject("msgStr", MessageConvertFactory.getMessage("randCode"));
            return mv;
        }
		if(saveAppeal(appealDto)){
			mv.addObject("iconNum","1"); //添加成功标记
			mv.addObject("msgStr",ErrorCodeEnum.SUGGEST_REPORT_SUCCESS.getMessage());
			return mv;
		}else{
			mv.addObject("iconNum","2"); //添加失败标记
			mv.addObject("msgStr",ErrorCodeEnum.SUGGEST_REPORT_FAIL.getMessage());
			return mv;
		}
	}
	
	//显示投诉页面
	public ModelAndView centerHelpComplaint(HttpServletRequest request,
			HttpServletResponse response,SuggestDto suggestDto) throws Exception {
		ModelAndView mv = new ModelAndView(centerHelpComplaint);
		mv.addObject("method", "centerHelpComplaint");
		return mv;
	}
	
	//投诉
	public ModelAndView complaint(HttpServletRequest request,
			HttpServletResponse response,AppealDto appealDto) throws Exception {
		ModelAndView mv = new ModelAndView(redirectView);
		ValidatorDto vd= AppealValidator.validate(appealDto);
		String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
	    request.getSession().removeAttribute("rand");
	    String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
	    mv.addObject("jumpUrl", "/baseHelp.htm?method=centerHelpComplaint");
	    if(vd.hasErrors()){
	    	  String msgStr=vd.getError();
	          mv.addObject("msgStr", msgStr);
	          mv.addObject("iconNum","2");
	          return mv;
        }
	    String appealCode = appealService.getAppealCode();
	    appealDto.setAppealCode(appealCode);
	    /*MultipartHttpServletRequest multipartRequest  = (MultipartHttpServletRequest) request;
	    MultipartFile imgFile  =  multipartRequest.getFile("picture1");   //上传的图片
        String img = imgFile.getName();
	    if(!"".equals(img)){
	    	fileUpAndDownLoadService.upLoad(imgFile,img,appealCode);
	    }*/
	    if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { //校验验证码
            mv.addObject("iconNum", "2");
            mv.addObject("msgStr", MessageConvertFactory.getMessage("randCode"));
            return mv;
        }
		if(saveAppeal(appealDto)){
			mv.addObject("iconNum","1"); //添加成功标记
			mv.addObject("msgStr",ErrorCodeEnum.SUGGEST_COMPLAINT_SUCCESS.getMessage());
			return mv;
		}else{
			mv.addObject("iconNum","2"); //添加失败标记
			mv.addObject("msgStr", ErrorCodeEnum.SUGGEST_COMPLAINT_FAIL.getMessage());
			return mv;
		}
	}
	
	//添加记录的方法
	public boolean saveAppeal(AppealDto appealDto){
		boolean result=true;
		Long appealId=0L;
		String appealSourceCode = appealDto.getAppealSourceCode() == null ? "" : appealDto.getAppealSourceCode().toString();
		//添加新记录
		if(StringUtils.isNotBlank(appealSourceCode)){
				try {
					appealId=appealService.createAppealHistoryRnTx(appealDto);
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(appealId!=null && appealId>0){
					result=true;
				}else{
					result=false;
				}
		 }
		return result;
	}
	
	//我的账户
	public ModelAndView myAccountRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountRegister);
		mv.addObject("method", "myAccountRegister");
		return mv;
	}
	
	public ModelAndView myAccountRegisterDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(myAccountRegisterDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView myAccountVerify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountVerify);
		mv.addObject("method", "myAccountVerify");
		return mv;
	}
	
	public ModelAndView myAccountVerifyDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(myAccountVerifyDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	
	public ModelAndView myAccountPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountPassword);
		mv.addObject("method", "myAccountPassword");
		return mv;
	}
	
	public ModelAndView myAccountPasswordDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(myAccountPasswordDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}

	public ModelAndView myAccountException(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(myAccountException);
		mv.addObject("method", "myAccountException");
		return mv;
	}
	
	public ModelAndView myAccountExceptionDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(myAccountExceptionDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	
	
	//交易规则
	public ModelAndView transactionProcess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionProcess);
		mv.addObject("method", "transactionProcess");
		return mv;
	}
	public ModelAndView transactionProcessDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(transactionProcessDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView transactionRule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionRule);
		mv.addObject("method", "transactionRule");
		return mv;
	}
	
	public ModelAndView transactionRuleDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(transactionRuleDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	
	public ModelAndView transactionPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionPayment);
		mv.addObject("method", "transactionPayment");
		return mv;
	}
	
	public ModelAndView transactionPaymentDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(transactionPaymentDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}

	public ModelAndView transactionQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(transactionQuestion);
		mv.addObject("method", "transactionQuestion");
		return mv;
	}
	
	public ModelAndView transactionQuestionDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(transactionQuestionDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	//消费记录
	public ModelAndView consumptionRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(consumptionRecord);
		mv.addObject("method", "consumptionRecord");
		return mv;
	}
	
	public ModelAndView consumptionRechargeAndWithdraw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(consumptionRecord);
		mv.addObject("method", "consumptionRechargeAndWithdraw");
		return mv;
	}

	public ModelAndView consumptionEvaluation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(consumptionEvaluation);
		mv.addObject("method", "consumptionEvaluation");
		return mv;
	}
	
	public ModelAndView consumptionRecordDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(consumptionRecordDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView consumptionRechargeAndWithdrawDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(consumptionRechargeAndWithdrawDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	
	public ModelAndView consumptionEvaluationDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(consumptionEvaluationDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	//生活管家
	public ModelAndView lifeMyPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeMyPayment);
		mv.addObject("method", "lifeMyPayment");
		return mv;
	}

	
	public ModelAndView lifeMyCollect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeMyCollect);
		mv.addObject("method", "lifeMyCollect");
		return mv;
	}
	
	public ModelAndView lifeSecuredTransaction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeSecuredTransaction);
		mv.addObject("method", "lifeSecuredTransaction");
		return mv;
	}
	
	public ModelAndView lifeShuidianmei(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeShuidianmei);
		mv.addObject("method", "lifeShuidianmei");
		return mv;
	}

	public ModelAndView lifeCommunication(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeCommunication);
		mv.addObject("method", "lifeCommunication");
		return mv;
	}
	
	public ModelAndView lifeCreditCardPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeCreditCardPayment);
		mv.addObject("method", "lifeCreditCardPayment");
		return mv;
	}
	
	public ModelAndView lifePresent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifePresent);
		mv.addObject("method", "lifePresent");
		return mv;
	}
	
	public ModelAndView lifeRent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeRent);
		mv.addObject("method", "lifeRent");
		return mv;
	}
	
	public ModelAndView lifeAACollect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeAACollect);
		mv.addObject("method", "lifeAACollect");
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
	
	public ModelAndView lifeInterBank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeInterBank);
		mv.addObject("method", "lifeInterBank");
		return mv;
	}
	
	public ModelAndView lifeMortgagerPayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(lifeMortgagerPayment);
		mv.addObject("method", "lifeMortgagerPayment");
		return mv;
	}
	

	
	
	public ModelAndView lifeMyPaymentDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeMyPaymentDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeMyCollectDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeMyCollectDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeSecuredTransactionDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeSecuredTransactionDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeShuidianmeiDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeShuidianmeiDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeCommunicationDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeCommunicationDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeCreditCardPaymentDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeCreditCardPaymentDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifePresentDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifePresentDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeRentDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeRentDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeAACollectDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeAACollectDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifelivingExpensesDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifelivingExpensesDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeAlimonyDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeAlimonyDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeInterBankDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeInterBankDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView lifeMortgagerPaymentDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(lifeMortgagerPaymentDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	
	
	//企业服务
	
	public ModelAndView enterpriseAccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(enterpriseAccess);
		mv.addObject("method", "enterpriseAccess");
		return mv;
	}
	
	public ModelAndView enterpriseCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(enterpriseCalculate);
		mv.addObject("method", "enterpriseCalculate");
		return mv;
	}
	
	public ModelAndView enterpriseWithdraw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(enterpriseWithdraw);
		mv.addObject("method", "enterpriseWithdraw");
		return mv;
	}
	
	public ModelAndView enterpriseProtocols(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(enterpriseProtocols);
		mv.addObject("method", "enterpriseProtocols");
		return mv;
	}
	
	
	public ModelAndView enterpriseAccessDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(enterpriseAccessDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView enterpriseProtocolDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(enterpriseProtocolDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView enterpriseCalculateDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(enterpriseCalculateDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	public ModelAndView enterpriseWithdrawDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String select = request.getParameter("select");
		String method = request.getParameter("method");
		ModelAndView mv = new ModelAndView(enterpriseWithdrawDetails);
		mv.addObject("method",method);
		mv.addObject("select", select);
		return mv;
	}
	
	//set get
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setMyAccountRegister(String myAccountRegister) {
		this.myAccountRegister = myAccountRegister;
	}

	public void setMyAccountRegisterDetails(String myAccountRegisterDetails) {
		this.myAccountRegisterDetails = myAccountRegisterDetails;
	}

	public void setMyAccountVerify(String myAccountVerify) {
		this.myAccountVerify = myAccountVerify;
	}

	public void setMyAccountVerifyDetails(String myAccountVerifyDetails) {
		this.myAccountVerifyDetails = myAccountVerifyDetails;
	}

	public void setMyAccountPassword(String myAccountPassword) {
		this.myAccountPassword = myAccountPassword;
	}

	public void setMyAccountPasswordDetails(String myAccountPasswordDetails) {
		this.myAccountPasswordDetails = myAccountPasswordDetails;
	}

	public void setMyAccountException(String myAccountException) {
		this.myAccountException = myAccountException;
	}

	public void setMyAccountExceptionDetails(String myAccountExceptionDetails) {
		this.myAccountExceptionDetails = myAccountExceptionDetails;
	}

	public void setTransactionProcess(String transactionProcess) {
		this.transactionProcess = transactionProcess;
	}

	public void setTransactionRule(String transactionRule) {
		this.transactionRule = transactionRule;
	}

	public void setTransactionPayment(String transactionPayment) {
		this.transactionPayment = transactionPayment;
	}

	public void setTransactionQuestion(String transactionQuestion) {
		this.transactionQuestion = transactionQuestion;
	}

	public void setTransactionProcessDetails(String transactionProcessDetails) {
		this.transactionProcessDetails = transactionProcessDetails;
	}

	public void setTransactionRuleDetails(String transactionRuleDetails) {
		this.transactionRuleDetails = transactionRuleDetails;
	}

	public void setTransactionPaymentDetails(String transactionPaymentDetails) {
		this.transactionPaymentDetails = transactionPaymentDetails;
	}

	public void setTransactionQuestionDetails(String transactionQuestionDetails) {
		this.transactionQuestionDetails = transactionQuestionDetails;
	}

	public void setConsumptionRecord(String consumptionRecord) {
		this.consumptionRecord = consumptionRecord;
	}

	public void setConsumptionRechargeAndWithdraw(
			String consumptionRechargeAndWithdraw) {
		this.consumptionRechargeAndWithdraw = consumptionRechargeAndWithdraw;
	}

	public void setConsumptionEvaluation(String consumptionEvaluation) {
		this.consumptionEvaluation = consumptionEvaluation;
	}

	public void setConsumptionRecordDetails(String consumptionRecordDetails) {
		this.consumptionRecordDetails = consumptionRecordDetails;
	}

	public void setConsumptionRechargeAndWithdrawDetails(
			String consumptionRechargeAndWithdrawDetails) {
		this.consumptionRechargeAndWithdrawDetails = consumptionRechargeAndWithdrawDetails;
	}

	public void setConsumptionEvaluationDetails(String consumptionEvaluationDetails) {
		this.consumptionEvaluationDetails = consumptionEvaluationDetails;
	}

	public void setLifeMyPayment(String lifeMyPayment) {
		this.lifeMyPayment = lifeMyPayment;
	}

	public void setLifeMyCollect(String lifeMyCollect) {
		this.lifeMyCollect = lifeMyCollect;
	}

	public void setLifeSecuredTransaction(String lifeSecuredTransaction) {
		this.lifeSecuredTransaction = lifeSecuredTransaction;
	}

	public void setLifeShuidianmei(String lifeShuidianmei) {
		this.lifeShuidianmei = lifeShuidianmei;
	}

	public void setLifeCommunication(String lifeCommunication) {
		this.lifeCommunication = lifeCommunication;
	}

	public void setLifeCreditCardPayment(String lifeCreditCardPayment) {
		this.lifeCreditCardPayment = lifeCreditCardPayment;
	}

	public void setLifePresent(String lifePresent) {
		this.lifePresent = lifePresent;
	}

	public void setLifeRent(String lifeRent) {
		this.lifeRent = lifeRent;
	}

	public void setLifeAACollect(String lifeAACollect) {
		this.lifeAACollect = lifeAACollect;
	}

	public void setLifelivingExpenses(String lifelivingExpenses) {
		this.lifelivingExpenses = lifelivingExpenses;
	}

	public void setLifeAlimony(String lifeAlimony) {
		this.lifeAlimony = lifeAlimony;
	}

	public void setLifeInterBank(String lifeInterBank) {
		this.lifeInterBank = lifeInterBank;
	}

	public void setLifeMortgagerPayment(String lifeMortgagerPayment) {
		this.lifeMortgagerPayment = lifeMortgagerPayment;
	}

	public void setLifeMyPaymentDetails(String lifeMyPaymentDetails) {
		this.lifeMyPaymentDetails = lifeMyPaymentDetails;
	}

	public void setLifeMyCollectDetails(String lifeMyCollectDetails) {
		this.lifeMyCollectDetails = lifeMyCollectDetails;
	}


	public void setLifeSecuredTransactionDetails(
			String lifeSecuredTransactionDetails) {
		this.lifeSecuredTransactionDetails = lifeSecuredTransactionDetails;
	}


	public void setLifeShuidianmeiDetails(String lifeShuidianmeiDetails) {
		this.lifeShuidianmeiDetails = lifeShuidianmeiDetails;
	}


	public void setLifeCommunicationDetails(String lifeCommunicationDetails) {
		this.lifeCommunicationDetails = lifeCommunicationDetails;
	}


	public void setLifeCreditCardPaymentDetails(String lifeCreditCardPaymentDetails) {
		this.lifeCreditCardPaymentDetails = lifeCreditCardPaymentDetails;
	}


	public void setLifePresentDetails(String lifePresentDetails) {
		this.lifePresentDetails = lifePresentDetails;
	}


	public void setLifeRentDetails(String lifeRentDetails) {
		this.lifeRentDetails = lifeRentDetails;
	}


	public void setLifeAACollectDetails(String lifeAACollectDetails) {
		this.lifeAACollectDetails = lifeAACollectDetails;
	}


	public void setLifelivingExpensesDetails(String lifelivingExpensesDetails) {
		this.lifelivingExpensesDetails = lifelivingExpensesDetails;
	}


	public void setLifeAlimonyDetails(String lifeAlimonyDetails) {
		this.lifeAlimonyDetails = lifeAlimonyDetails;
	}


	public void setLifeInterBankDetails(String lifeInterBankDetails) {
		this.lifeInterBankDetails = lifeInterBankDetails;
	}


	public void setLifeMortgagerPaymentDetails(String lifeMortgagerPaymentDetails) {
		this.lifeMortgagerPaymentDetails = lifeMortgagerPaymentDetails;
	}


	public void setCenterHelpAdvice(String centerHelpAdvice) {
		this.centerHelpAdvice = centerHelpAdvice;
	}


	public void setCenterHelpReport(String centerHelpReport) {
		this.centerHelpReport = centerHelpReport;
	}


	public void setCenterHelpComplaint(String centerHelpComplaint) {
		this.centerHelpComplaint = centerHelpComplaint;
	}
	public void setEnterpriseAccess(String enterpriseAccess) {
		this.enterpriseAccess = enterpriseAccess;
	}
	public void setEnterpriseCalculate(String enterpriseCalculate) {
		this.enterpriseCalculate = enterpriseCalculate;
	}
	public void setEnterpriseAccessDetails(String enterpriseAccessDetails) {
		this.enterpriseAccessDetails = enterpriseAccessDetails;
	}
	public void setEnterpriseCalculateDetails(String enterpriseCalculateDetails) {
		this.enterpriseCalculateDetails = enterpriseCalculateDetails;
	}
	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

	public void setEnterpriseWithdraw(String enterpriseWithdraw) {
		this.enterpriseWithdraw = enterpriseWithdraw;
	}

	public void setEnterpriseWithdrawDetails(String enterpriseWithdrawDetails) {
		this.enterpriseWithdrawDetails = enterpriseWithdrawDetails;
	}

	public void setEnterpriseProtocols(String enterpriseProtocols) {
		this.enterpriseProtocols = enterpriseProtocols;
	}

	public void setEnterpriseProtocolDetails(String enterpriseProtocolDetails) {
		this.enterpriseProtocolDetails = enterpriseProtocolDetails;
	}

	public void setAppealService(AppealService appealService) {
		this.appealService = appealService;
	}

	/*public void setFileUpAndDownLoadService(
			FileUpAndDownLoadService fileUpAndDownLoadService) {
		this.fileUpAndDownLoadService = fileUpAndDownLoadService;
	}*/

}
