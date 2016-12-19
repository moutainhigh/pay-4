package com.pay.app.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.app.common.helper.AppConf;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.cache.MemberFeatureCacheService;
import com.pay.util.CheckUtil;
import com.pay.util.SignatureHelper;
import com.pay.util.WebUtil;


/**
 * @author zengjin
 * @date 2010-8-4
 * @param 
 */
public class AppFilterCommon{
    private static final Log            logger               = LogFactory
                                                                 .getLog(AppFilterCommon.class);
    private static final String staff="|";
    public static final int success=0;
    public static final int nofeature=1;
    public static final int nocert=2;
    
    private static final String certcheck="1";
    
	private static  MemberFeatureCacheService memberFeatureCacheService=ServiceLocator.getService(MemberFeatureCacheService.class,"base-memberFeatureCacheServiceImpl");
	private static  MemberQueryService memberQueryService=ServiceLocator.getService(MemberQueryService.class,"acc-memberQueryService");  
    public static void removePaySession(HttpServletRequest request) {
        request.getSession().removeAttribute("memberCode");
        request.getSession().removeAttribute("loginName");
        request.getSession().removeAttribute("verifyName");
        request.getSession().removeAttribute("userSession");
        request.getSession().removeAttribute("userid");
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("memberSignatureData");
    }
    
    public static void makeMenuSelect(HttpServletRequest request){
        request.getSession().removeAttribute("enabledMenuId");
        String enabledMenuId=request.getParameter("menuId");
        if(StringUtils.isNotEmpty(enabledMenuId)){
            request.getSession().setAttribute("enabledMenuId", enabledMenuId);
        }
    }

    /**
     * 判断个人用户身份是否正常登录
     * @param request
     * @return
     */
    public static boolean isNormalUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.debug("appfiter sessionId is :#####################" + request.getSession().getId());
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        if (loginSession != null && loginSession.getMemberCode() != null
            && loginSession.getActiveStatus().equals("1")
            && (loginSession.getScaleType() == ScaleEnum.INDIVIDUAL.getValue())) {
            return true;
        }
        //  && (loginSession.getScaleType()==ScaleEnum.INDIVIDUAL.getValue())
        return false;
    }

    
    public static boolean isHaveFeature(){
    	LoginSession loginSession=SessionHelper.getLoginSession();
    	//if(loginSession.getBspIdentity()==2){
    		String actionUrl = RequestLocal.getRequest().getRequestURI().replace(RequestLocal.getRequest().getContextPath(), "");
        	logger.info("actionUrl#########["+actionUrl+"]################");
    		Long memberCode=Long.valueOf(loginSession.getMemberCode());
    	//	Long operatorId=loginSession.getOperatorId();
//    		if(operatorId>0L){
//    			return memberFeatureService.isOperatorHaveMenu(operatorId, memberCode, actionUrl);
//    		}else{
    			/*return memberFeatureCacheService.isHaveMenu(loginSession.getSecurityLvl(), loginSession.getScaleType()
            			, memberCode, actionUrl);*/
    		return memberCode != 0 ? true : false ;
    		//}
        	
    	//}
    	//return true;
    }
    
    
    public static int isHaveCertFeature(){
    	int result=AppFilterCommon.success;
    	LoginSession loginSession=SessionHelper.getLoginSession();
    	String actionUrl = RequestLocal.getRequest().getRequestURI().replace(RequestLocal.getRequest().getContextPath(), "");
    	logger.info("actionUrl#########["+actionUrl+"]################");
		Long memberCode=Long.valueOf(loginSession.getMemberCode());
		if(memberCode != 0){
			result=AppFilterCommon.success;
		}else{
			//
		}
    	/*PowersModel pm=memberFeatureCacheService.isHaveCertMenu(loginSession.getSecurityLvl(), loginSession.getScaleType()
            			, memberCode, actionUrl);
    	if(pm==null)result=AppFilterCommon.nofeature;
    	else{
    		if(SessionHelper.isCertUser()){
        		if(SessionHelper.isLocalCertUser()) result=AppFilterCommon.success;//证书用户且安装了证书的用户
        		else{
        			String serlvl=pm.getSecurityLvl();
        			if(serlvl!=null && serlvl.contains(AppFilterCommon.certcheck)){ //证书用户未安装了证书的用户 验证需要证书权限的菜单
        				result=AppFilterCommon.nocert;
        			}
        		} 
        	}else{
        		result=AppFilterCommon.success;//非证书用户
        	}
    	}*/
    	
    		
    	return result;	
    }
    
 
    /**
     * 判断企业用户身份是否正常登录
     * @param request
     * @return
     */
    public static boolean isCorpNormalUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        if (loginSession != null && loginSession.getMemberCode() != null
            && loginSession.getActiveStatus().equals("1")
            && SessionHelper.isCorpLogin()) {
            return true;
        }

        return false;
    }
    
    /**
     * 判断用户身份是否正常
     * @param request
     * @return
     */
    public static boolean isNormalStatusUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        if (loginSession != null && loginSession.getMemberCode() != null
        		 && loginSession.getActiveStatus().equals("1")) {
        	MemberBaseInfoBO memberInfo=null;
            try {
				 memberInfo=memberQueryService.queryMemberBaseInfoByMemberCode(Long.valueOf(loginSession.getMemberCode()));
				 if(memberInfo.getStatus().equals(MemberStatusEnum.NORMAL.getCode())){
					 return true;
				 }
			} catch (Exception e) {
				logger.error("memberQueryService.doQueryMemberInfoNsTx throws error",e);
			}
        }

        return false;
    }
    
    

    public static String getSignData(){
        String args=RequestLocal.getSession().getId()+AppFilterCommon.staff+WebUtil.getAgent(RequestLocal.getRequest());
        logger.debug("appfiter getSignData is :#####################" + args);
        return args;
    }
    
    public static boolean isSignature(){
    	
        String signatureData=RequestLocal.getSession().getAttribute("memberSignatureData")==null
            ?"":RequestLocal.getSession().getAttribute("memberSignatureData").toString();
        logger.debug("session signatureData is :#####################" + signatureData);
        String newSignatureData=SignatureHelper.generateAppSignature(getSignData());
        logger.debug("session newSignatureData is :#####################" + newSignatureData);
        return (signatureData.equals(newSignatureData));
    }

    /**
     * 判断用户是否登录
     * @param request
     * @return
     */
    public static boolean isSecurityLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("memberCode") == null) {
            return false;
        }
        return true;
    }

    /**
     * 只验证登录不验证身份的url
     * @param request
     * @return
     */
    public static boolean demandSecurityUrl(HttpServletRequest request, String requestPath) {
        //String requestPath=request.getRequestURI();
        //String   contextPath   =   request.getContextPath()+"/";  
        //String   basePath   =   request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
        //logger.info("###############################  contextPath is "+contextPath);
        //if(contextPath.equals(requestPath))return true;
        //requestPath=requestPath.replace(request.getContextPath(), "");
        final String specialUrl = "/app/memberActive.htm";
        logger.info("###############################  requestPath is " + requestPath);
        String urls = AppConf.get(AppConf.uncheckUrl);
        String[] urlArray = urls.split(",");
        if (urlArray.length > 0) {
            for (String u : urlArray) {
                if (requestPath.equals(u) || requestPath.contains(specialUrl)) {
                    return true;
                }
            }
        }

        return false;

    }

    public static String requestURI(HttpServletRequest request) throws UnsupportedEncodingException {
        String url = request.getRequestURI().replace(request.getContextPath(), "");

        StringBuffer queryString = new StringBuffer("");
        //StringBuffer url_buffer = request.getRequestURL(); 
        Map map = request.getParameterMap();
        Iterator it = map.keySet().iterator();
        String key = "";
        String keyValue = "";
        while (it.hasNext()) {
            key = (String) it.next();
            keyValue = map.get(key) == null ? "" : ((String[]) map.get(key))[0];
            if ("".equals(keyValue)) {
                continue;
            }

            if (CheckUtil.isContainsChinese(keyValue)) {
                try {
                    keyValue = URLEncoder.encode(keyValue, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("URLEncoder.encode " + keyValue + " throws error :" + e);
                }
            }

            queryString.append(key);
            queryString.append("=");
            queryString.append(keyValue);
            queryString.append("&");
        }
        String fullPath = "";
        if (queryString.toString().endsWith("&")) {
            fullPath = url + "?" + queryString.substring(0, queryString.length() - 1);
        } else
            fullPath = url + "?" + queryString;

        if (StringUtils.isEmpty(queryString.toString())) {
            fullPath = url;
        }
        // fullPath =  new String(fullPath.getBytes("iso-8859-1"),"utf-8");

        return fullPath;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> requestUriMap(HttpServletRequest request, String callBackUrl,int orgin,int memberType) {
        String actionUrl = request.getRequestURI().replace(request.getContextPath(), "");
        Map<String, String> paramMaps = null;
        Map<String, Object> pMaps = new HashMap<String, Object>(2);
        String actionKey=AppConf.actionUrl;
        if(memberType==2){
        	actionKey=AppConf.actionCorpUrl;
        }
        pMaps.put(actionKey, actionUrl);
        pMaps.put(AppConf.orginUrl, orgin);
        
        Map map = request.getParameterMap();
        if (map != null && map.size() > 0) {
            paramMaps = new HashMap<String, String>(map.size());
        }

        Iterator it = map.keySet().iterator();
        String key = "";
        String keyValue = "";
        while (it.hasNext()) {
            key = (String) it.next();
            keyValue = map.get(key) == null ? "" : ((String[]) map.get(key))[0];
//            if ("".equals(keyValue)) {
//                continue;
//            }
            if (AppConf.payiframe.equals(key) && "true".equals(keyValue)) {
                pMaps.clear();
                pMaps.put(actionKey, callBackUrl);
                logger.info("this is iframe window url is ===========" + actionUrl);
                return pMaps;
            }
            paramMaps.put(key, keyValue);
        }
        if (paramMaps != null) {
            pMaps.put(AppConf.jumpChildParam, paramMaps);
        }

        return pMaps;
    }

    public static void setCallBackJumpUri(HttpServletRequest request,String callBackUrl,int memberType){
        Map<String, Object> pMaps = new HashMap<String, Object>(2);
        if(memberType==2){
        	 pMaps.put(AppConf.actionCorpUrl, callBackUrl);
             request.getSession().setAttribute(AppConf.sessionCorpJumpMap, pMaps);
        }else{
        	 pMaps.put(AppConf.actionUrl, callBackUrl);
             request.getSession().setAttribute(AppConf.sessionJumpMap, pMaps);
        }
       
    }
    
    public static void setCallBackUri(HttpServletRequest request, String callBackUrl,int orgin,int memberType) {
        Map<String, Object> jMaps = AppFilterCommon.requestUriMap(request,callBackUrl,orgin,memberType);
        if(memberType==2){
        	 request.getSession().setAttribute(AppConf.sessionCorpJumpMap, jMaps);
        }else
        	request.getSession().setAttribute(AppConf.sessionJumpMap, jMaps);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getCallBackUri(HttpServletRequest request,int memberType) {
    	String jumpKey=AppConf.sessionJumpMap;
    	if(memberType==2){
    		jumpKey=AppConf.sessionCorpJumpMap;
    	}
        Map<String, Object> jMaps = null;
        if (request.getSession().getAttribute(jumpKey) != null) {
            Map<String, Object> attribute = (Map<String, Object>) request.getSession()
                .getAttribute(jumpKey);
            jMaps = attribute;
        }
        return jMaps;
    }
}
