/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.app.controller.base.login;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.EnterpriseRegisterInfo;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.member.MemberService;
import com.pay.app.common.helper.MessageConvertFactory;

/**
 * 企业注册
 * @author zhi.wang
 * @version $Id: CorpRegisterController.java, v 0.1 2011-2-22 上午10:44:32 zhi.wang Exp $
 */
public class CorpRegisterController extends MultiActionController {
	private static final String POST_METHOD_PREFIX = "POST";
	private String toRegister;
	
	private EnterpriseRegisterService enterpriseRegisterService;
	private MemberService baseMemberService;
	
	private Pattern NUM_OR_ABC = Pattern.compile("^[0-9a-zA-Z]*$");

	private Pattern NUMBER = Pattern.compile("^[0-9]*$");
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response)  {
		return new ModelAndView(toRegister).addObject("info",new EnterpriseRegisterInfo());
	}

	

	public ModelAndView register(HttpServletRequest request,
			HttpServletResponse response,EnterpriseRegisterInfo info)throws Exception{
		String msgStr=ErrorCodeEnum.SYSTEM_NO_POST.getMessage();
		
	    String method=request.getMethod();
	    if(POST_METHOD_PREFIX.equals(method)){
	        String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
	        request.getSession().removeAttribute("rand");
	        String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
	        String loginName=info.getEmail();
	        
	        if(!checkStringLength(info.getZhName(),4, 32)){
	            msgStr="公司名称最少4个汉字，最多不能超过32个汉字";
	            Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("msgStr", msgStr);
	            return new ModelAndView(toRegister, paraMap);
	        }
	        if(!checkStringLength(info.getLegalName(),2, 16)){
	            msgStr="法人代表姓名最少2个字符，最多不能超过16个字符";
	            Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("msgStr", msgStr);
	            return new ModelAndView(toRegister, paraMap);
	        }
	        if(!checkStringLength(info.getCompayLinkerName(),2, 32)){
	            msgStr="联系人名称最少2个字符，最多不能超过32个字符";
	            Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("msgStr", msgStr);
	            return new ModelAndView(toRegister, paraMap);
	        }
	        
	        //验证企业营业执照号码
	        if(!checkStringLength(info.getBizLicenceCode(),13,15) || !isNumber(info.getBizLicenceCode())){
	        	msgStr="营业执照号码必须是13至15位数字";
	        	Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("msgStr", msgStr);
	            return new ModelAndView(toRegister, paraMap);
	        }
	        
	      //验证企业营业执照号码
	        if(!checkStringLength(info.getBizLicenceCode(),13,18) || !isNumOrABC(info.getBizLicenceCode())){
	        	msgStr="税务登记号必须是13至18位数字或字母";
	        	Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("msgStr", msgStr);
	            return new ModelAndView(toRegister, paraMap);
	        }
	        
	        int result = enterpriseRegisterService.checkUnique(info);
	        if(result > 0){
	        	if(result == 1){
	        		msgStr="营业执照号已存在，请重新填写";
	        	}else if(result == 2){
	        		msgStr="组织机构号已存在，请重新填写";
	        	}else{
	        		msgStr="税务登记证号已存在，请重新填写";
	        	}
	        	Map<String, Object> paraMap = new HashMap<String, Object>();
	        	paraMap.put("info", info);
	        	paraMap.put("msgStr", msgStr);
	        	return new ModelAndView(toRegister, paraMap);
	        }
	        if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { //校验验证码
	            Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("inputName", "randCode");
	            paraMap.put("msgStr", MessageConvertFactory.getMessage("randCode"));
	            return new ModelAndView(toRegister, paraMap);
	        }else if(!baseMemberService.checkLoginNameByRegister(loginName, Integer.valueOf(2))){//校验用户名是否唯一
	            Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("info", info);
	            paraMap.put("inputName", "loginName");
	            paraMap.put("msgStr", MessageConvertFactory.getMessage("loginNameRepeat"));
	            return new ModelAndView(toRegister, paraMap);
	        }
	        
            //基本信息填写成功之后把enterpriseRegisterInfo放入SESSION以方便以后调用 
            request.getSession().setAttribute("enterpriseRegisterInfo", info);
            // 跳转银行信息页面
            return new ModelAndView("redirect:/liquidateInfo.htm?method=inputLiquidateInfo");
	        
	    }else{
	        info =new EnterpriseRegisterInfo();
	    }
	    
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("info", info);
		paraMap.put("msgStr", msgStr);
		return new ModelAndView(toRegister, paraMap);
		//return  index(request,response).addObject("msgStr",msgStr);
	}
	
	public static boolean checkStringLength(final String str,int len){
//        String fstr=str;
//        if(StringUtils.isBlank(fstr)){
//            return false;
//        }
//        try {
//            fstr=new String(str.getBytes("gbk"),"iso-8859-1");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return false;
//        }
        return (str.length()<=len);
    }
	
	private  boolean checkStringLength(String str,int min,int max){
        String fstr=str;
        if(StringUtils.isBlank(fstr)){
            return false;
        }
        return (fstr.length()<=max && fstr.length()>=min);
    }
	
	private boolean isNumOrABC(String str){
		return NUM_OR_ABC.matcher(str).matches();
	}
	
	private boolean isNumber(String str){
		return NUMBER.matcher(str).matches();
	}
	
	public void checkLoginName(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			response.setContentType("text/plain;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-cache");
	        // 电子邮箱
	    	int regType=2;
	    	String loginName=request.getParameter("email")==null?"":request.getParameter("email");
	    		
	        PrintWriter out = null;
	        out = response.getWriter();
	        boolean flag=false;
	        flag=baseMemberService.checkLoginNameByRegister(loginName.toLowerCase(), regType);
	        out.print(flag);
	        out.flush();
	        out.close();
	}

	public void setToRegister(String toRegister) {
		this.toRegister = toRegister;
	}

	public void setEnterpriseRegisterService(
			EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}

	public void setBaseMemberService(MemberService baseMemberService) {
		this.baseMemberService = baseMemberService;
	}
	
}
