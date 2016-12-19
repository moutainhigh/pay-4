package com.pay.app.controller.base.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.service.matrixcard.login.MatrixCardLoginService;
import com.pay.util.WebUtil;

public class MatrixCardLoginController  extends MultiActionController {

    private MatrixCardLoginService matrixCardLoginService;
    
    private String matrixLogin;
    
    private static final int len=3;
    
    
    public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
            LoginSession loginSession=SessionHelper.getLoginSession();
            MatrixCardDto mc=matrixCardLoginService.findByBindMemberCode(Long.valueOf(loginSession.getMemberCode()));
            String serialNo=mc.getSerialNo();
            MatrixCardVfyRequest mcVfyRequest =new MatrixCardVfyRequest();
            OperatorInfo operatorInfo=new OperatorInfo();
            operatorInfo.setMemberCode(Long.valueOf(loginSession.getMemberCode()));
            operatorInfo.setOperator(loginSession.getVerifyName());
            operatorInfo.setSessionId(request.getSession().getId());
            operatorInfo.setIp(WebUtil.getClientIP(request));
            ResultDto rdto=matrixCardLoginService.processRequest(mcVfyRequest, operatorInfo);
         
            return new ModelAndView(matrixLogin)
                    .addObject("serialNo",securityNo(serialNo))
                    .addObject("token",(MatrixCardToken)rdto.getObject());
    }
    
    public ModelAndView redirect(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        LoginSession loginSession=SessionHelper.getLoginSession();
        loginSession.setSecurityCount(1);
        String jumpUrl=request.getParameter("jumpUrl")==null?AppConf.defaultCallBack:request.getParameter("jumpUrl");
        request.getSession().setAttribute("userSession", loginSession);
        return new ModelAndView("redirect:"+jumpUrl);   
    }
    
    
    public ModelAndView login(HttpServletRequest request,
            HttpServletResponse response,MatrixCardToken matrixCardToken) throws Exception {
     
        LoginSession loginSession=SessionHelper.getLoginSession();
        MatrixCardDto mc=matrixCardLoginService.findByBindMemberCode(Long.valueOf(loginSession.getMemberCode()));
        String serialNo=mc.getSerialNo();
        OperatorInfo operatorInfo=new OperatorInfo();
        operatorInfo.setMemberCode(Long.valueOf(loginSession.getMemberCode()));
        operatorInfo.setOperator(loginSession.getVerifyName());
        operatorInfo.setSessionId(request.getSession().getId());
        operatorInfo.setIp(WebUtil.getClientIP(request));
        ResultDto rdto=matrixCardLoginService.verifyLogin(serialNo, matrixCardToken, operatorInfo);
        ModelAndView mv=index(request,response);
        if(rdto.getErrorCode()==null){
            loginSession.setSecurityLvl(SecurityLvlEnum.MAXTRIX.getValue());
            loginSession.setSecurityCount(1);
            request.getSession().setAttribute("userSession", loginSession);
            mv.addObject("succesCode",AppConf.successCode);
        }else{
            mv.addObject("errMsg",rdto.getErrorMsg());
        }
        return mv; 
    }
    
    private static String securityNo(String serialNo){
        String serialNoDes="";
        
        int alen=serialNo.length();
      if(len>0){
          serialNoDes=serialNo.substring(0,len)+serialNo.substring(len, alen-len).replaceAll("\\w", "*")+serialNo.substring(alen-len, alen);
      }  
       return serialNoDes;
    }
    
    
    public void setMatrixCardLoginService(MatrixCardLoginService matrixCardLoginService) {
        this.matrixCardLoginService = matrixCardLoginService;
    }



    public void setMatrixLogin(String matrixLogin) {
        this.matrixLogin = matrixLogin;
    }

}
