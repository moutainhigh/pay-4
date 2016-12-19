package com.pay.app.controller.base.accountactive;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.app.common.helper.AppConf;
import com.pay.app.service.sms.SmsService;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 *  File: ReSendMessageController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date     2010-8-19     
 *  Author   zengjin     
 *  Changes   
 *  Comment 手机短信控制器
 */
public class ReSendMessageController extends MultiActionController{
	
	private String login;
	
	private String toMobile;
	
	private String retoMobile;
	
	public void setRetoMobile(String retoMobile) {
		this.retoMobile = retoMobile;
	}
	private String toFail;
	
	private SmsService smsService;
	
    private String reSendSMS="reSendSMS";


	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 加入发短信功能
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null
				|| request.getSession().getAttribute("verifyName") == null) {
			return new ModelAndView(login);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String mobile=request.getSession().getAttribute("loginName").toString();
		String verifyName=request.getSession().getAttribute("verifyName").toString();
		String origin=request.getParameter("origin")==null?CheckCodeOriginEnum.ACTIVE_REGISTER.getValue():request.getParameter("origin");
		String reSend=request.getParameter("reSendSMS");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		List<String> mobiles = new ArrayList<String>(1);
		mobiles.add(mobile);
		if(mobile!=null && !"".equals(mobile) && smsService.sendSms(memberCode, origin, verifyName, mobiles, 2)){
            if(null!=reSend && reSend.equals(reSendSMS)) {
            	return new ModelAndView("redirect:/reSendMessage.htm?method=toMobile&reSendSMS="+reSend);	
            }
			return new ModelAndView("redirect:/reSendMessage.htm?method=toMobile");
		}

		return new ModelAndView(toFail, paraMap);
	}
	
	public ModelAndView toFail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null
				|| request.getSession().getAttribute("verifyName") == null) {
			return new ModelAndView(login);
		}
		return new ModelAndView(toFail);
	}
	
	
	
	public ModelAndView toMobile(HttpServletRequest request,
			HttpServletResponse response){
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String reSend=request.getParameter("reSendSMS");
		String mobile=request.getSession().getAttribute("loginName").toString();
		Object errMsg =request.getAttribute("errMsg");
		if(null!=errMsg){
			return new ModelAndView(toFail).addObject("errMsg",errMsg).addObject("resend",request.getAttribute("resend")).addObject("mobile",request.getAttribute("mobile"));
		}
		
		CheckCodeDto checkcode=smsService.getByMemerCode(memberCode,CheckCodeOriginEnum.ACTIVE_REGISTER);
		if(checkcode==null){
			return new ModelAndView(toFail).addObject("mobile",mobile);
		}
		Date createtime=checkcode.getCreateTime();
		Integer minuttes=new Integer(AppConf.get(AppConf.sms_interval));
		String createtime2String=String.valueOf(FormatDate.sceondOfTwoDate(createtime, minuttes));
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		
		if(null!=gettimePage(request,mobile,createtime2String,reSend))
		{
		return this.gettimePage(request, mobile,createtime2String,reSend);			
		}
		return new ModelAndView(toMobile).addObject("mobile",mobile);	
	}
    private ModelAndView  gettimePage(HttpServletRequest request,String mobile,String endtime,String reSMS)
    {
    	
		if(!StringUtil.isEmpty(endtime))
		{
			if(null!=reSMS && reSMS.equals(reSendSMS)){return new ModelAndView(retoMobile).addObject("mobile",mobile).addObject("createtime2String",endtime);}
			return new ModelAndView(toMobile).addObject("mobile",mobile).addObject("createtime2String",endtime);	
		}
		return null;
    }
	public void setLogin(String login) {
		this.login = login;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public void setToFail(String toFail) {
		this.toFail = toFail;
	}
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
}
