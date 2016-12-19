package com.pay.app.controller.base.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.common.helper.AppConf;



public class LoginController  extends MultiActionController{
    
    private String enterpriceLogin;
    
    private String individualLogin;
    
    public ModelAndView individualLogin(HttpServletRequest request,
            HttpServletResponse response) {
        String returnUrl=request.getParameter("returnUrl");
        if(StringUtils.isBlank(returnUrl)){
            returnUrl=AppConf.defaultCallBack;
        }
        return new ModelAndView(individualLogin).addObject("returnUrl",returnUrl);
    }
    
  

    public ModelAndView enterpriceLogin(HttpServletRequest request,
            HttpServletResponse response) {
        String returnUrl=request.getParameter("returnUrl");
        if(StringUtils.isBlank(returnUrl)){
            returnUrl=AppConf.defaultCorpCallBack;
        }
        return new ModelAndView(enterpriceLogin).addObject("returnUrl",returnUrl);
    }
    
    public void setEnterpriceLogin(String enterpriceLogin) {
        this.enterpriceLogin = enterpriceLogin;
    }

    public void setIndividualLogin(String individualLogin) {
        this.individualLogin = individualLogin;
    }
}
