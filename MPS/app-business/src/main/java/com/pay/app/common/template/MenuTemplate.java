package com.pay.app.common.template;

import java.util.ArrayList;
import java.util.List;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.MemberFeatureService;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @author zengjin
 * @date 2010-8-6
 * @param 获取权限菜单
 */
public class MenuTemplate implements TemplateMethodModel{
	
	  private static  MemberFeatureService memberFeatureService=ServiceLocator.getService(MemberFeatureService.class,"base-memberFeatureService");
	  //private static  MemberInfoServiceFacade memberAppInfoService=ServiceLocator.getService(MemberInfoServiceFacade.class,"app-memberInfoService");
	  public Object exec(List arguments) throws TemplateModelException {
		  List<PowersModel> menuList=new ArrayList<PowersModel>();
		  LoginSession loginSession=SessionHelper.getLoginSession();
		  if(loginSession!=null && loginSession.getMemberCode()!=null){
		      int securLevel=loginSession.getSecurityLvl();
		      int scaleType=loginSession.getScaleType();
		      Long memberCode=Long.valueOf(loginSession.getMemberCode());
		      menuList=memberFeatureService.getMemberMenu(securLevel, scaleType,memberCode);
		   if(SessionHelper.isCorpLogin()){
		          Long operatorId=loginSession.getOperatorId();
		          if(operatorId>0L)
		             menuList=memberFeatureService.getOperatorMenu(operatorId,memberCode);
		   	}
			 
		  }
		  if(menuList==null)
		      menuList=new ArrayList<PowersModel>(1);
		  
	       return menuList;
	    }
}
