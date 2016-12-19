package com.pay.app.common.template;

import java.util.List;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.app.service.messagebox.MessageReceiveService;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class MsgCountTemplate implements TemplateMethodModel{
	 private static   MessageReceiveService messageReceiveService=ServiceLocator.getService(MessageReceiveService.class,"app-messageReceiveService");
	 private static int countUnRead=0;
	 
	  public Object exec(List arguments) throws TemplateModelException {
		  LoginSession loginSession=SessionHelper.getLoginSession();
		  if(loginSession!=null && loginSession.getMemberCode()!=null)
			  countUnRead= (Integer) messageReceiveService.countUnRead(loginSession.getMemberCode());
		  return countUnRead;
	  }
}
