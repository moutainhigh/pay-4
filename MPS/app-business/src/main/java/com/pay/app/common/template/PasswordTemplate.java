package com.pay.app.common.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.cert.model.CertManage;
import com.pay.acc.cert.service.CertManageService;
import com.pay.acc.service.cert.CertQueryService;
import com.pay.acc.service.cert.CertService;
import com.pay.acc.service.member.dto.ApplyCertResponse;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.model.OsBrowser;
import com.pay.util.RSAHelper;
import com.pay.util.StringUtil;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 密码控件
 * @author jerry_jin
 *
 */
public class PasswordTemplate implements TemplateMethodModel {
	
	/**
	 * 控件生效的来源
	 */
	public static String[] ORIGIN = MessageConvertFactory.getMessage("payedit.controller.origin").split(",");
	
	public static String[] OS_BROWSERS = MessageConvertFactory.getMessage("payedit.controller.browsers").split(",");
	
	public static OsBrowser[] osBrowsers = null;
	
	private CertService  certService = null;
	
	private CertQueryService certQueryService = null;
	
	CertManageService certManageService = null;
	
	static{
		osBrowsers = new OsBrowser[OS_BROWSERS.length];
		String temp[] = null;
		int i = 0;
		for(String t:OS_BROWSERS){
			osBrowsers[i] = new OsBrowser();
			temp = StringUtil.split(t,".");
			osBrowsers[i].setOs(temp[0]);
			osBrowsers[i].setBrowser(temp[1]);
			i++;
		}
	}
	
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String from  = String.valueOf(arguments.get(0));
		String useCert = String.valueOf(arguments.get(1));
		String memberCode = String.valueOf(arguments.get(2));
		String operatorId = String.valueOf(arguments.get(3));
		
		HttpServletRequest request=(HttpServletRequest)RequestLocal.getRequest();
		String agent = request.getHeader("User-Agent");
//		StringTokenizer st = new StringTokenizer(agent,";");
////		st.nextToken();
//		String userbrowser = st.nextToken();
	    String isSecurity="0";
        Map<String,Object> result = new HashMap<String,Object>();
        boolean useController = false;
        for(String origin:ORIGIN){
        	if(from.equals(origin)){
        		useController = true;
        		break;
        	}
        }
        
        String browser = null;
        
        try{
	        for(OsBrowser ob :osBrowsers){
	        	if(agent.contains(ob.getBrowser()) && agent.contains(ob.getOs())){
	        		browser = ob.getBrowser();
	        		break;
	        	}
	        }
        }catch(Exception e){}
        
		if(browser!=null && useController){
			isSecurity="1";
			String pkey=RSAHelper.public_key_String;
			result.put("browser", browser);
			result.put("pkey", pkey);
			result.put("control", "0");
		}
		
		//检测是否为证书控件
		LoginSession loginSession=SessionHelper.getLoginSession();
		if("1".equals(useCert)){
			//根据输入的memberCode,operatorId 判断
			Long lgMemberCode = null;
			Long lgOperatorId = null;
			boolean isCertUser = false;
			if(StringUtils.isNotBlank(memberCode) && StringUtils.isNotBlank(operatorId)){
				lgMemberCode = Long.valueOf(memberCode);
				lgOperatorId = Long.valueOf(operatorId);
				certQueryService = ServiceLocator.getService(CertQueryService.class,"pki-certQueryService");
				if(certQueryService.isCertUser(lgMemberCode, lgOperatorId)){
					isCertUser = true;
				}
			}else if (loginSession!=null && loginSession.isCertUser()){
				//根据 session中当前用户判断
				isCertUser = true;
				lgMemberCode = Long.valueOf(loginSession.getMemberCode());
				lgOperatorId = SessionHelper.getOperatorIdBySession();
			}
			
			if(isCertUser){
				isSecurity="1";//所有浏览器使用证书控件
				String userAgentLow = agent.toLowerCase();
				int isIE =  userAgentLow.indexOf("msie") >= 0 ? 1:0;
				result.put("ie",isIE);
				result.put("control", "1");
				result.put("memberCode", String.valueOf(lgMemberCode));
				result.put("operatorId", String.valueOf(lgOperatorId));
				
				certService=ServiceLocator.getService(CertService.class,"pki-certService");
				ApplyCertResponse acResponse=certService.getValidMmeberCert(lgMemberCode, lgOperatorId);
				if(acResponse!=null){
					 result.put("dn",acResponse.getDn());
					 result.put("cn",acResponse.getCn());
				}
				certManageService = ServiceLocator.getService(CertManageService.class,"acc-certManageService");
				List<CertManage> certManages = certManageService.queryCertManage(lgMemberCode, lgOperatorId);
				result.put("certManages", certManages);
			}
		}
		result.put("isSecurity",isSecurity);
		SimpleHash sh = new SimpleHash(result);
		return sh;
	}

}
