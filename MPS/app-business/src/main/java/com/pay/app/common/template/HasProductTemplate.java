package com.pay.app.common.template;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.dto.MemberProductResult;
import com.pay.app.common.ServiceLocator;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class HasProductTemplate implements TemplateMethodModel{
	private static Log logger = LogFactory.getLog(HasProductTemplate.class);
	 private static   MemberProductService memberProductService=ServiceLocator.getService(MemberProductService.class,"acc-memberProductService");
	 
	 public Object exec(List arguments) throws TemplateModelException {
		  LoginSession loginSession=SessionHelper.getLoginSession();
		  if(loginSession!=null && loginSession.getMemberCode()!=null){
			  Long memberCode=Long.valueOf(loginSession.getMemberCode());
			  Long operatorId=loginSession.getOperatorId();
			  String productCode=(String)arguments.get(0);
			  MemberProductResult result= memberProductService.isHaveProduct(memberCode, productCode, operatorId);
			  if(result.isReturnBool()){
				  return true;
			  }else{
				  if(logger.isInfoEnabled()){
					  logger.info("memberCode:["+memberCode+"],operatorId:["+operatorId+"],productCode:["+productCode+"] errorMsg:["+result.getErrMsg()+"]" );
				  }
				  return false;
			  }
		  }
			 
		  return false;
	  }
	
}
