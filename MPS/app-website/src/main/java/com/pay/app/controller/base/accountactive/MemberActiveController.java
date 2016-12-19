package com.pay.app.controller.base.accountactive;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.AppFilterCommon;
import com.pay.app.service.mail.MailService;
import com.pay.base.common.enums.OrginEnum;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.member.MemberService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 *  File: MemberActiveController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date     2010-8-30     
 *  Author   zengjin     
 *  Changes   
 *  Comment
 */
public class MemberActiveController extends MultiActionController {
 
	private String activeSuccess;

	private String activeMailFaile;

	private String activeMobileFaile;
	
	private String activeEmailSuccess;
	
	private String activeMobileSuccess;
	
	private String activeFaile;

	private String activeSuccessAndRedirect;

	private String activeReady;

	private String login;

	private MailService mailService;

	private AcctService acctService;

	private String setPaypwd;
  
    private MemberService baseMemberService;

    private IMessageDigest iMessageDigest;
    
	/**
	 * 激活状态码 1-未激活 2-已激活
	 */
	private static final int successStatus = 2;
	private static final int failStatus = 1;
	
	public ModelAndView toSetPaypwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(setPaypwd);
	}
	
	public ModelAndView toActiveReady(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(activeReady);
	}
	
	public ModelAndView memberActive(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/***验证验证码是否正确*/
		String randCode = request.getSession().getAttribute("rand") == null
		? ""
		: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		
		String checkCode = request.getParameter("code") == null ? "" : request
				.getParameter("code");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		if (!code.equalsIgnoreCase(randCode)) {
			request.getSession().removeAttribute("rand");
			paraMap.put("returninfo", MessageConvertFactory.getMessage("randCode"));
			paraMap.put("classcss", "feedback warning");
			request.setAttribute("loginName", request.getSession().getAttribute("loginName"));
			request.setAttribute("code", checkCode);
			return new ModelAndView(setPaypwd, paraMap);
		}
		
		request.getSession().removeAttribute("rand");
		
		String returnView=activeFaile;
		String errMsg = MessageConvertFactory.getMessage("errorHandle");
		String loginName="";
		/************验证是否登录**********/
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null) {
			AppFilterCommon.setCallBackUri(request,AppConf.defaultCallBack,OrginEnum.INDIVIDUAL_LOCAL.getValue(),1);
			return new ModelAndView("redirect:/outapp.htm");
			//return new ModelAndView(login);
		}
		loginName=request.getSession().getAttribute("loginName").toString();
		String memberCode = request.getSession().getAttribute("memberCode").toString();
//		/************验证是否激活过**********/
//		if(mailService.isActive(memberCode)){
//			return new ModelAndView(activeReady);
//		}
//		
		
		String activeType = request.getParameter("activeType") == null ? ""
				: request.getParameter("activeType");
		String endtime="0";
		if (!"".equals(checkCode)) {
				
				CheckCodeDto ck = mailService.getByCheckCode(checkCode);
			
				if (ck != null) {
					endtime= this.getdiffTime(ck.getCreateTime());
					if (failStatus == ck.getStatus()){ //未激活时
						if(ck.getCheckCode().equals(checkCode)){
							
							String paypwd = request.getParameter("newpaypassword2");
							String paypwd0 = request.getParameter("newpaypassword1");
							
							//验证2次输入的密码是否一致
							if(StringUtils.isNotBlank(paypwd)&&StringUtils.isNotBlank(paypwd0)){
								if(!paypwd0.equals(paypwd)){
									paraMap.put("returninfo", MessageConvertFactory.getMessage("differentPwd"));
									paraMap.put("classcss", "feedback warning");
									paraMap.put("myAccount", loginName);
									paraMap.put("loginName", loginName);
									paraMap.put("code", checkCode);
									return new ModelAndView(setPaypwd, paraMap);
								}
							}else{
								paraMap.put("returninfo", MessageConvertFactory.getMessage("emptyPwd"));
								paraMap.put("classcss", "feedback warning");
								paraMap.put("myAccount", loginName);
								paraMap.put("loginName", loginName);
								paraMap.put("code", checkCode);
								return new ModelAndView(setPaypwd, paraMap);
							}
							
							//验证支付密码是否包含特殊字符
				    		if(CheckUtil.isPwdContainsSpecialCharacter(paypwd)){
				    			paraMap.put("returninfo", MessageConvertFactory.getMessage("specialCharInPayPwd"));
				    			
				    			paraMap.put("classcss", "feedback warning");
								paraMap.put("myAccount", loginName);
								paraMap.put("loginName", loginName);
								paraMap.put("code", checkCode);
								return new ModelAndView(setPaypwd, paraMap);
				    		}
							//验证密码格式是否符合
							if(!CheckUtil.checkPayPwd(paypwd)){
								paraMap.put("returninfo", MessageConvertFactory.getMessage("invalidePayPwd"));
								paraMap.put("classcss", "feedback warning");
								paraMap.put("myAccount", loginName);
								paraMap.put("loginName", loginName);
								paraMap.put("code", checkCode);
								return new ModelAndView(setPaypwd, paraMap);
							}
							
							// 验证支付密码和登录密码是否一致   start
							MemberDto member =  baseMemberService.findByMemberCode(Long.parseLong(memberCode));
							if(member!=null && StringUtils.isNotBlank(paypwd)){
								if(member.getLoginPwd()!=null && member.getLoginPwd().equals(iMessageDigest.genMessageDigest(paypwd.getBytes()))){
									paraMap.put("returninfo", MessageConvertFactory.getMessage("errorForSamePassword"));
									paraMap.put("classcss", "feedback warning");
									paraMap.put("myAccount", loginName);
									paraMap.put("loginName", loginName);
									paraMap.put("code", checkCode);
									return new ModelAndView(setPaypwd, paraMap);
								}
							}
							// 验证支付密码和登录密码是否一致   end
							
							boolean isSetPaypwdSuccess = setPaypwd(memberCode, AcctTypeEnum.BASIC_CNY.getCode(),paypwd);
							
							if(!isSetPaypwdSuccess){
								// 设置支付密码出错
								if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
									returnView=activeMailFaile;
								}else if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
									returnView=activeMobileFaile;
								}
							}else{
							
								ResultDto rd = baseMemberService.doActiveMemberRnTx(Long
										.valueOf(ck.getMemberCode()), activeType, ck
										.getCreateTime());
								if (rd.getErrorCode() == null) {
									mailService.updateCheckCodeStates(checkCode);
									Map<String,Object> jMaps = AppFilterCommon.getCallBackUri(request,1);
									Object flag=null;
									if(jMaps!=null){
										flag = jMaps.get(AppConf.orginUrl);
									}
									 
									if(null!=flag && new Integer(OrginEnum.SHOP.getValue()).equals(flag)){
										//from 商城
										returnView = activeSuccessAndRedirect;
									}else{
										if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
											setSessionTimeout(request);
											returnView=activeEmailSuccess;
										}else if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
											setSessionTimeout(request);
											returnView=activeMobileSuccess;
										}
									}
									
										
								} else {
									errMsg=rd.getErrorMsg();
									if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
										returnView=activeMailFaile;
									}else if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
										returnView=activeMobileFaile;
									}
									
								}
							}
						}else{
							errMsg=MessageConvertFactory.getMessage("activeError");
							if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
								returnView=activeMailFaile;
							}else if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
								returnView=activeMobileFaile;
							}
						}
					} else if (successStatus == ck.getStatus()){//已激活
						setSessionTimeout(request);
						return new ModelAndView(activeReady);
					}
				}else{
					errMsg=MessageConvertFactory.getMessage("activeError");
				}
			
		}
		
		ModelAndView mv=new ModelAndView(returnView);
		mv.addObject("errMsg",errMsg);
		if(CheckUtil.checkEmail(loginName)){
			mv.addObject("email",loginName);
		}else{
			mv.addObject("mobile",loginName);
			mv.addObject("createtime2String",endtime);
		}
		
		return mv;
	}
	
	private void setSessionTimeout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(0);
	}

	private String getdiffTime(Date createtime) {
	//	CheckCode checkcode = mailService.getByMemerCode(memberCode);
	//	Date createtime = checkcode.getCreateTime();// AppConf
		Integer minuttes = new Integer(AppConf.get(AppConf.sms_interval));
		String createtime2String = String.valueOf(FormatDate.sceondOfTwoDate(
				createtime, minuttes));
		if (!StringUtil.isEmpty(createtime2String)) {
			return createtime2String;
		} else {
			return "0";
		}
	}
	
	/**
	 * 设置支付密码
	 * @param memberCode
	 * @param scaleType
	 * @param paypwd
	 * @return
	 */
	private boolean setPaypwd(String memberCode,int scaleType,String paypwd){
		ResultDto results  =acctService.doResetPayPwdRnTx(Long.parseLong(memberCode),scaleType , paypwd,null);
		if(null==results.getErrorCode()){
			return true;
		}else{
			return false;
		}
	}


	public void setActiveSuccess(String activeSuccess) {
		this.activeSuccess = activeSuccess;
	}

	public void setActiveReady(String activeReady) {
		this.activeReady = activeReady;
	}

	public void setActiveMailFaile(String activeMailFaile) {
		this.activeMailFaile = activeMailFaile;
	}

	public void setActiveMobileFaile(String activeMobileFaile) {
		this.activeMobileFaile = activeMobileFaile;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}



	public void setBaseMemberService(MemberService baseMemberService) {
	        this.baseMemberService = baseMemberService;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	
	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setActiveEmailSuccess(String activeEmailSuccess) {
		this.activeEmailSuccess = activeEmailSuccess;
	}

	public void setActiveMobileSuccess(String activeMobileSuccess) {
		this.activeMobileSuccess = activeMobileSuccess;
	}
	public void setActiveFaile(String activeFaile) {
		this.activeFaile = activeFaile;
	}

	public void setSetPaypwd(String setPaypwd) {
		this.setPaypwd = setPaypwd;
	}
	
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setActiveSuccessAndRedirect(String activeSuccessAndRedirect) {
		this.activeSuccessAndRedirect = activeSuccessAndRedirect;
	}

}
