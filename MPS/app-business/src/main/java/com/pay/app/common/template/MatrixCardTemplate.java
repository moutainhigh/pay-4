package com.pay.app.common.template;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.matrixcard.login.MatrixCardLoginService;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class MatrixCardTemplate implements TemplateMethodModel{
    
    private MatrixCardLoginService matrixCardLoginService=ServiceLocator.getService(MatrixCardLoginService.class,"matrixCardLoginService");
    private static final int len=3;
    
    public Object exec(List arguments) throws TemplateModelException {
        HashMap<String,Object> matrixMap=new HashMap<String,Object>(2);
        HttpServletRequest request=(HttpServletRequest)RequestLocal.getRequest();
        LoginSession loginSession=SessionHelper.getLoginSession();
        if(loginSession!=null && loginSession.getMemberCode()!=null){
            String serialNo=loginSession.getSerialNo()==null?"":loginSession.getSerialNo();
            matrixMap.put("serialNo", securityNo(serialNo));
            MatrixCardVfyRequest mcVfyRequest =new MatrixCardVfyRequest();
            OperatorInfo operatorInfo=new OperatorInfo();
            operatorInfo.setMemberCode(Long.valueOf(loginSession.getMemberCode()));
            operatorInfo.setOperator(loginSession.getVerifyName());
            operatorInfo.setSessionId(request.getSession().getId());
            operatorInfo.setIp(this.getIp(request));
            ResultDto rdto=matrixCardLoginService.processRequest(mcVfyRequest, operatorInfo);
            matrixMap.put("rdto", rdto);
        }
        
        
         return matrixMap;
      }
    
    private static String securityNo(String serialNo){
        String serialNoDes="";
        
        int alen=serialNo.length();
      if(len>0){
          serialNoDes=serialNo.substring(0,len)+serialNo.substring(len, alen-len).replaceAll("\\w", "*")+serialNo.substring(alen-len, alen);
      }  
       return serialNoDes;
    }
    
    
    public static String getIp(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级反向代理
        if (null != ip && !"".equals(ip.trim())) {
            StringTokenizer st = new StringTokenizer(ip, ",");
            if (st.countTokens() > 1) {
                return st.nextToken();
            }
        }
        return ip;
    }
  
}
