package com.pay.app.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.filter.AppFilterCommon;

public class RedirectController implements Controller {
    private static final Log logger = LogFactory.getLog(RedirectController.class);
    
    private String autoJump;
    
  
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	int memberType=1;
    	if(SessionHelper.getLoginSession()!=null && SessionHelper.getLoginSession().getScaleType()==2){
    		memberType=2;
    	}
    	
        Map<String,Object> jMaps=AppFilterCommon.getCallBackUri(request,memberType);
        Map<String,String> pMaps=null;
        String jumpUrl=AppConf.defaultCallBack;
        if(jMaps!=null){
        	if(memberType==2){
        		 jumpUrl=jMaps.get(AppConf.actionCorpUrl)==null?AppConf.defaultCorpCallBack:jMaps.get(AppConf.actionCorpUrl).toString();
                 pMaps=(Map)jMaps.get(AppConf.jumpChildParam);
                 request.getSession().removeAttribute(AppConf.sessionCorpJumpMap);
        	}else{
        		 jumpUrl=jMaps.get(AppConf.actionUrl)==null?AppConf.defaultCallBack:jMaps.get(AppConf.actionUrl).toString();
                 pMaps=(Map)jMaps.get(AppConf.jumpChildParam);
                 request.getSession().removeAttribute(AppConf.sessionJumpMap);
        	}
           
        }
        
      
        if(pMaps!=null){
            pMaps.put("isCheked", "1");
            return new ModelAndView(autoJump).addObject(AppConf.actionUrl,jumpUrl).addObject("pMaps",pMaps).addObject("loading",true);
        }
        return new ModelAndView("redirect:"+jumpUrl);  
    }
    
    public String getAutoJump() {
        return autoJump;
    }

    public void setAutoJump(String autoJump) {
        this.autoJump = autoJump;
    }
}
