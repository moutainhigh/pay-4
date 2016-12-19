/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.controller.base.accountactive;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.SafeControllerWrapper;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.EnterpriseInfo;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.CheckUtil;

/**
 * 企业会员操作
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-18 下午05:27:25
 * 
 */
public class EnterpriseController extends MultiActionController {
	
	/** 会员基本信息服务 */
	private MemberService memberService;
	/** 操作员 */
    private OperatorServiceFacade  operatorServiceFacade;
	
	/** 激活成功页面的*/
	private String activeSuccess;
	
	private String activeFail;
	
	private String codeInvalidView;
	
	
	/** 会员激活信息服务 */
	private CheckCodeService checkCodeService;
	
	/** 补全信息页面 */
	private String completionInfo;
	/**
	 * 激活企业用户并补全信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView activeAndCompletionInfo(HttpServletRequest request,HttpServletResponse response,EnterpriseInfo enterpriseInfo)  {
		
		
		//验证checkCode是否正确
		String checkCode = enterpriseInfo.getCheckCode();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		CheckCodeDto ck = checkCodeService.getByCheckCodeAndOrigin(checkCode,CheckCodeOriginEnum.POSS_CORP_ACTIVE_REGISTER);
		if(ck == null || ck.getStatus()== 2){
			return new ModelAndView(codeInvalidView);
		}
		
		SafeControllerWrapper safeRequest = new SafeControllerWrapper(request,new String[]{"password","passwordConfirm","payPassword","payPasswordConfirm"});
		long memberCode = enterpriseInfo.getMemberCode();
		// 更新t_member表。更新用户登录密码、安全问题和答案、问候语。
		// 更新checkCode表。更新激活状态。
		// 添加功能权限信息t_operator_menu
		
		String loginPwd = safeRequest.getParameter("password");
		String passwordConfirm = safeRequest.getParameter("passwordConfirm");
		
		String securityQuestion = enterpriseInfo.getSecurityQuestion();
		String securityAnswer = enterpriseInfo.getSecurityAnswer();
		String greeting = enterpriseInfo.getGreeting();
		String loginName = enterpriseInfo.getLoginName();
		// 操作员
		String identity = enterpriseInfo.getIdentity();
		
		String oldIdentity = enterpriseInfo.getOldIdentity();
		String payPassword = safeRequest.getParameter("payPassword");
		String payPasswordConfirm = safeRequest.getParameter("payPasswordConfirm");
		
		paraMap.put("enterpriseInfo", enterpriseInfo);
		String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
		request.getSession().setAttribute("rand", null);
		if (StringUtils.isBlank(code) || !StringUtils.equalsIgnoreCase(code, randCode)) { 
			paraMap.put("rand","rand");
			paraMap.put("msgStr",MessageConvertFactory.getMessage("randCode"));
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
		}
		if(StringUtils.isBlank(loginPwd)){
			paraMap.put("msgStr", "登录密码不能为空");
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        if(!StringUtils.equals(loginPwd, passwordConfirm)){
        	paraMap.put("msgStr", "确认登录密码和登录密码必须一致");
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        if(StringUtils.equals(payPassword,loginPwd)){
        	paraMap.put("msgStr", "支付密码与登录密码不能相同");
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        if(CheckUtil.isPwdContainsSpecialCharacter(loginPwd)){
        	paraMap.put("msgStr", MessageConvertFactory.getMessage("specialCharInLoginPwd"));
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        if(!CheckUtil.checkLoginPwd(loginPwd)){
        	paraMap.put("msgStr", ErrorCodeEnum.MEMBER_LOGINNAME_PAYPASSWORD.getMessage());
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        
        if(StringUtils.isBlank(payPassword)){
			paraMap.put("msgStr", "支付密码不能为空");
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        if(!StringUtils.equals(payPassword, payPasswordConfirm)){
        	paraMap.put("msgStr", "确认支付密码和支付密码必须一致");
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
       
        //验证支付密码是否包含特殊字符
		if(CheckUtil.isPwdContainsSpecialCharacter(payPassword)){
			paraMap.put("msgStr", MessageConvertFactory.getMessage("specialCharInPayPwd"));
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
		}
        if(!CheckUtil.checkPayPwd(payPassword)){
        	paraMap.put("msgStr", MessageConvertFactory.getMessage("invalidePayPwd"));
			paraMap.put("email", loginName);
			return new ModelAndView(completionInfo,paraMap);
        }
        
		MemberDto memberDTO = new MemberDto();
		String msgStr = "";
		try {
			memberDTO.setMemberCode(Long.valueOf(memberCode));
			memberDTO.setLoginPwd(loginPwd);
			memberDTO.setSecurityQuestion(Integer.valueOf(securityQuestion));
			memberDTO.setSecurityAnswer(securityAnswer);
			memberDTO.setGreeting(greeting);
			// 激活
			ResultDto resDto=memberService.activeEnterpriseRnTx(memberDTO, checkCode, identity, oldIdentity,payPassword,2,10);
			msgStr=resDto.getErrorMsg();
		}catch(Exception ex){
			msgStr = "激活失败，系统暂时异常！";
		}
		if(StringUtils.isBlank(msgStr)){
			return new ModelAndView(activeSuccess).addObject("email", loginName).addObject("operatorName",identity);
		}
		paraMap.put("msgStr", msgStr);
		paraMap.put("email", loginName);
		return new ModelAndView(activeFail,paraMap);
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setActiveSuccess(String activeSuccess) {
		this.activeSuccess = activeSuccess;
	}

	public void setActiveFail(String activeFail) {
		this.activeFail = activeFail;
	}

	public void setOperatorServiceFacade(
			OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public void setCompletionInfo(String completionInfo) {
		this.completionInfo = completionInfo;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setCodeInvalidView(String codeInvalidView) {
		this.codeInvalidView = codeInvalidView;
	}
}