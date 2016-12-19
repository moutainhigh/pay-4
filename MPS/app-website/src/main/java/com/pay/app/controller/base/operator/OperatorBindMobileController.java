/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.controller.base.operator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.base.dto.OperatorBindMobileDto;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.DESUtil;
import com.pay.util.ValidateUtils;
import com.pay.app.common.util.ValidateCodeUtils;

/**
 * 
 * desc: 操作员绑定手机号
 * @author DaiDeRong
 * 2011-11-17 上午11:24:09
 *
 */
public class OperatorBindMobileController extends MultiActionController {

	private OperatorServiceFacade operatorServiceFacade;
	
	
	/**
	 * 输入手机号页面 
	 */
	private String inputNewMobileView;
	
	
	/**
	 * 绑定结果页面 
	 */
	private String bindResultView;
	
	
	
	
	/**
	 * @param operatorServiceFacade the operatorServiceFacade to set
	 */
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}






	/**
	 * @param inputNewMobileView the inputNewMobileView to set
	 */
	public void setInputNewMobileView(String inputNewMobileView) {
		this.inputNewMobileView = inputNewMobileView;
	}






	/**
	 * @param bindResultView the bindResultView to set
	 */
	public void setBindResultView(String bindResultView) {
		this.bindResultView = bindResultView;
	}
   
	
	
	
	//////////////流程方法/////////////
	

	/**
	 * 输入新的手机 号
	 * @author DaiDeRong
	 * 2011-11-17 上午11:30:28
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		String operatorId = ServletRequestUtils.getStringParameter(request, "operatorId","");
		 operatorId = DESUtil.decrypt(operatorId);
		 Operator operator  = null;
		 if(operatorId != null){
			  operator =  operatorServiceFacade.getOperatorById(Long.valueOf(operatorId));
		 }
		
		if(operator==null || operator.getMemberCode() != Long.valueOf(SessionHelper.getMemeberCodeBySession()).longValue()){
			return new ModelAndView(bindResultView).addObject("status", false).addObject("errorMsg", "参数不正确,请重新选择操作员");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		String oldMobile = operator.getMobile();
		if(oldMobile != null){
			model.put("oldMobile", oldMobile);
			model.put("oldMobileFmt", oldMobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1 $2 $3"));
		}
		model.put("operatorId", operatorId);
		return new ModelAndView(inputNewMobileView,model);
	}
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param operatorBindMobileDto
	 * @return
	 */
	public ModelAndView exeBind(HttpServletRequest request,HttpServletResponse response,OperatorBindMobileDto operatorBindMobileDto){
		
		
		if(! ValidateCodeUtils.validateCode(request, "randCode")){
			return index(request, response).addObject("errorRandCode", "验证码输入有误");
		}
		
		Operator operator =  operatorServiceFacade.getOperatorById(Long.valueOf(operatorBindMobileDto.getOperatorId()));
		
		if(operator.getMobile()!=null && (operator.getMobile().equals(operatorBindMobileDto.getMobile()))){
			return index(request, response).addObject("errorMobile", "新手机号和原来手机一致,无需再次绑定");
		}
		
		if(! operatorBindMobileDto.getMobile().matches("\\d{11}")){
			return index(request, response).addObject("errorMobile", "手机 号码必须是11位数字");
		}
		//更新手机号
		operator.setMobile(operatorBindMobileDto.getMobile());
		boolean status = operatorServiceFacade.updateOperator(operator) == 1;
		
		return new ModelAndView(bindResultView).addObject("status", status).addObject("operatorName", operator.getName());
	} 
	
	
	
	
    
	

	
}
